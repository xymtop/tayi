package com.xymtop.tayi.core.vm;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.store.DBUtils;
import com.xymtop.tayi.core.system.Runner;
import com.xymtop.tayi.core.utils.encrypt.HashUtils;
import com.xymtop.tayi.core.utils.fileutils.FileUtils;
import com.xymtop.tayi.core.utils.jsonutils.XJsonUtils;
import com.xymtop.tayi.core.vm.code.that.That;
import com.xymtop.tayi.core.vm.contract.Contract;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.Event;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import com.xymtop.tayi.core.vm.ipfs.IPFSUtils;
import com.xymtop.tayi.core.vm.virtual.ContractStore;
import com.xymtop.tayi.core.vm.virtual.JarEnv;
import com.xymtop.tayi.core.vm.virtual.object.ObjectStreamUtils;
import com.xymtop.tayi.core.vm.virtual.object.TaYiStreamUtils;
import com.xymtop.tayi.core.vm.zip.ZIPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 2:17
 */

//类似于一种虚拟机制
@Component
public class TaYiVM  {

    //当前环境的That
    @Autowired
    private That that;


    @Autowired
    private IPFSUtils ipfsUtils;

    @Value("${tayi.vm.contract.path}")
    private String contractPath;

    @Autowired
    HashUtils hashUtils;

    @Autowired
    ContractStore contractStore;

    @Autowired
    XJsonUtils xJsonUtils;

    @Autowired
    DBUtils dbUtils;






    //调试智能合约
    public Object debug(Class clazz,String funName,Object... args) throws Exception {
          //获取所有方法
        Method[] methods = clazz.getMethods();
        if (methods == null){
            throw new RuntimeException("方法不存在");
        }
        //获取方法
        for (Method method : methods) {
            if (method.getName().equals(funName)){
                //初始化对象
                Object instance = clazz.newInstance();

                //注入that
                setThat(instance);

                //调用方法
                Object result = method.invoke(clazz.newInstance(),args);

                return result;
            }
        }
        throw new RuntimeException("调试错误");
    }

    public Object debug(Class clazz,String funName) throws Exception {
        //获取所有方法
        Method[] methods = clazz.getMethods();
        if (methods == null){
            throw new RuntimeException("方法不存在");
        }
        //获取方法
        for (Method method : methods) {
            if (method.getName().equals(funName)){
                //初始化对象
                Object instance = clazz.newInstance();

                //注入that
                setThat(instance);

                //调用方法
                Object result = method.invoke(clazz.newInstance());

                return result;
            }
        }
        throw new RuntimeException("调试错误");
    }

    //部署合约
    public String deploy(String hash) throws Exception {
        String id = hashUtils.hashHex(hash);

        //获取文件
        String filePath = contractPath+"/"+id+"/";
        String contractJar = filePath +"contract.jar";
        String statusFilePath = filePath +"data.ser";




        boolean flag =  ipfsUtils.downloadFile(hash,contractJar);
        if (!flag){
              throw new RuntimeException("获取合约代码失败");
        }

       //构建合约信息
        Contract contract = new Contract();
        ContractInfo contractInfo = new ContractInfo();

        JarEnv jarEnv = new JarEnv(contractJar, TaYiJavaContract.class);

        //获取方法
        Method[] allMethods = jarEnv.getAllMethods();

        //获取参数
        Field[] allFields = jarEnv.getAllFields();


        Object object =  jarEnv.getLoadedClass().newInstance();

        if (!(object instanceof TaYiJavaContract)){
            throw new RuntimeException("不是标准合约");
        }

        TaYiJavaContract contractObj = (TaYiJavaContract) object;

        if (contractObj == null){
            throw new RuntimeException("合约构建失败");
        }

//
        setThat(contractObj);

        contractInfo = contractObj.info();

        for (Method method : allMethods) {
            contract.getMethods().put(method.getName(),method);
        }

        for (Field field : allFields) {
            contract.getFields().put(field.getName(),field);
        }


        contract.setContractInfo(contractInfo);


        //调用部署方法
        contractObj.deploy();


        //获取状态数据

        String stateHash =  writeState(contractObj,statusFilePath);

       //设置状态id
        contract.setStatusAddress(stateHash);




       contract.setAddress(hash);
       //设置合约id
        contract.setId(id);

       //维护代码源地址

       writeContractData(contract);

        //删除文件夹
        deleteDir(filePath);

       return id;
    }

    public Object call(String id,String funName) throws Exception {
        //获取合约信息
        Contract contractInfo = getContractInfo(id);

        if (contractInfo == null){
            throw new RuntimeException("合约不存在");
        }

        //组装合约方法
        contractInfo =    getMethedsAndFields(contractInfo);


        //调用方法
        Method method = contractInfo.getMethods().get(funName);

        if (method == null){
            throw new RuntimeException("方法不存在");
        }

        Object obj = contractInfo.getContract();
        if (obj == null){
            throw new RuntimeException("合约初始化失败");
        }


        if (!(obj instanceof TaYiJavaContract)){

        }



        //注入当前环境
        setThat(obj);


//        再次获取方法
        method = obj.getClass().getDeclaredMethod(funName);
        if (method == null){
            throw new RuntimeException("类方法获取失败");
        }
        //判断是否是实践类
        Class<?> methodDeclaringClass = method.getDeclaringClass();
        if (!methodDeclaringClass.isInstance(obj)){

            throw new Exception("不是实现类");

        }

        Object result = method.invoke(obj);


        System.out.println("调用合约中...");


        //写入合约数据
        contractInfo.setContract(obj);



        String filePath = contractPath+"/"+id+"/";
        String statusFilePath = filePath +"data.ser";

        String stateHash =  writeState(obj,statusFilePath);

        //设置状态id
        contractInfo.setStatusAddress(stateHash);


        //运行完毕，写入状态数据和合约数据
        writeContractData(contractInfo);

        //删除文件夹
        deleteDir(filePath);

        return result;
    }


    public  void deleteDir(String path) throws IOException {
//        FileUtil.del(FileUtils.getResourcesFilePath(path));
    }

    //写入合约运行后的数据
    private void writeRunData(Contract contract, Object obj) throws Exception {
        ContractInfo contractInfo = contract.getContractInfo();
        if (contractInfo == null){
            throw new Exception("合约信息不存在");
        }
        String id = contract.getId();

    }

    //注入环境
    private void setThat(Object obj) throws Exception {
        Field field = obj.getClass().getSuperclass().getDeclaredField("that");
        field.setAccessible(true);
        if (that == null){
            throw new RuntimeException("当前环境错误");
        }
        field.set(obj,that);
    }

    //调用合约
    public Object call(String id,String funName,Object args) throws Exception {
        //获取合约信息
        Contract contractInfo = getContractInfo(id);

        if (contractInfo == null){
            throw new RuntimeException("合约不存在");
        }

        //组装合约方法
        contractInfo =    getMethedsAndFields(contractInfo);


        //调用方法
        Method method = contractInfo.getMethods().get(funName);

        if (method == null){
            throw new RuntimeException("方法不存在");
        }

        Object obj = contractInfo.getContract();
        if (obj == null){
            throw new RuntimeException("合约初始化失败");
        }


        if (!(obj instanceof TaYiJavaContract)){

        }



        //注入当前环境
        setThat(obj);


//        再次获取方法
        method = obj.getClass().getDeclaredMethod(method.getName(),method.getParameterTypes());
        if (method == null){
            throw new RuntimeException("类方法获取失败");
        }
        //判断是否是实践类
        Class<?> methodDeclaringClass = method.getDeclaringClass();
        if (!methodDeclaringClass.isInstance(obj)){

            throw new Exception("不是实现类");

        }


        //判断方法的参数列表
//        Class<?>[] parameterTypes = method.getParameterTypes();
//        for (int i = 0; i < parameterTypes.length; i++){
//            if (parameterTypes[i].isAssignableFrom(JSONObject.class)){
//                args[i] = JSONUtil.toBean(JSONUtil.toJsonStr(args[i]),parameterTypes[i]);
//            }
//        }

        Object result = method.invoke(obj,args);


        System.out.println("调用合约中...");

        contractInfo.setContract(obj);
        writeContractData(contractInfo);
        //删除文件夹
        String filePath = contractPath+"/"+id+"/";
        deleteDir(filePath);

        return result;
    }

    //获取合约信息
    public Contract getContractInfo(String hash) throws Exception {
        System.out.println("获取合约信息中...");
        return readContractData(hash);
    }

    //写入状态
    public String writeState(Object contract,String contracDatatPath) throws IOException, IllegalAccessException {
        TaYiStreamUtils.serialize(contracDatatPath,contract);

        //上传数据文件
        String hash = ipfsUtils.uploadFile(contracDatatPath);
        System.out.println("写入状态中...");
        return hash;

    }


    //导出合约所有数据
    public String export(String id) throws Exception {
        //获取合约信息
        Contract contractInfo = getContractInfo(id);

        //合约代码地址
        String filePath = contractPath+"/"+id+"/"+"contract.jar";
        String dataPath = contractPath+"/"+id+"/"+"data.ser";
        //除了路径
        filePath = FileUtils.getResourcesFilePath(filePath);
        dataPath = FileUtils.getResourcesFilePath(dataPath);

        //存在合约代码
        boolean haveCode =false;
        //存在合约数据
        boolean haveData = false;

        //判断文件是否存在
        if (!FileUtil.exist(filePath)){
            //下载文件
            boolean flag =  ipfsUtils.downloadFile(id,filePath);
            if (flag){
//                throw new RuntimeException("获取合约代码失败");
                haveCode = true;
            }
        }

        //判断数据文件是否存在
        if (!FileUtil.exist(dataPath)){
            //下载文件
            boolean flag =  ipfsUtils.downloadFile(contractInfo.getStatusAddress(),dataPath);
            if (flag){
              haveData = true;
            }
        }

        String zipPath = contractPath+id+".tayi";

//        压缩为合约导出文件
        ZIPUtils contractZip =  new ZIPUtils(zipPath);

        if (!FileUtil.exist(zipPath)){
            contractZip.createZipFile();
        }
        if (haveCode){
            contractZip.addFileToZip(filePath);
        }
        if (haveData){
            contractZip.addFileToZip(dataPath);
        }


        //上传合约导出文件
        String resHash = ipfsUtils.uploadFile(contractPath + id + ".tayi");

        System.out.println("导出合约中...");

        return resHash;
    }
    public void writeContractData(Contract contract) throws Exception {

        //新建一个合约数据
        Contract temp = contract;

        temp.setMethods(null);
        temp.setFields(null);

        //写入智能合约的数据部分

        //将合约数据写入数据库
        dbUtils.put(contract.getId(), xJsonUtils.objToJson(temp));
    }

    //读取合约数据
    public Contract readContractData(String contractId) throws Exception {
        //从数据库中读取合约数据
        String contractData = dbUtils.get(contractId);

        //将合约数据转换为合约对象
        Contract contract = xJsonUtils.jsonToObj(contractData, Contract.class);

        //获取到合约的数据
        Object contractObj = new Object();

        //通过哈希值加载合约数据


        contract.setContract(contractObj);

        return contract;
    }

    //组装合约方法
    public Contract getMethedsAndFields(Contract contract ) throws Exception {
        //获取合约地址
        String contractAddress = contract.getAddress();

        //获取合约数据
        //合约代码地址
        String hash = contract.getId();

        String filePath = contractPath+"/"+hash+"/"+"contract.jar";
        String dataPath = contractPath+"/"+hash+"/"+"data.ser";
        //构建合约信息

        ContractInfo contractInfo = new ContractInfo();

        //判断数据文件是否存在
        Object instance = ObjectStreamUtils.jsonObjectToBean(filePath, new JSONObject());

        JarEnv jarEnv = new JarEnv(filePath, instance.getClass().getName());

        //获取方法
        Method[] allMethods = jarEnv.getAllMethods();

        //获取参数
        Field[] allFields = jarEnv.getAllFields();



        if (!(instance instanceof TaYiJavaContract)){
            throw new RuntimeException("不是标准合约");
        }

        Object contractObj =  instance;

        if (contractObj == null){
            throw new RuntimeException("合约构建失败");
        }

        contract.setClassName(contractObj.getClass().getName());
        contract.setContract(contractObj);

        setThat(contractObj);
        contractInfo = ((TaYiJavaContract) contractObj).info();

        for (Method method : allMethods) {
            contract.getMethods().put(method.getName(),method);
        }

        for (Field field : allFields) {
            contract.getFields().put(field.getName(),field);
        }


        contract.setContractInfo(contractInfo);

        dataPath = FileUtils.getResourcesFilePath(dataPath);
        //判断数据文件是否存在
        if (!FileUtil.exist(dataPath)){
            //下载文件
            boolean flag =  ipfsUtils.downloadFile(contract.getStatusAddress(),dataPath);
            if (!flag){
                throw new RuntimeException("获取合约状态数据失败");
            }
        }
        Object contractData = TaYiStreamUtils.deserialize(filePath,dataPath);

        if (contractData instanceof TaYiJavaContract){
            contract.setContract(contractData);
        }


        return contract;

    }




}
