package com.xymtop.tayi.cmd;

import com.xymtop.tayi.cmd.apis.ann.CmdApi;
import com.xymtop.tayi.cmd.apis.ann.CmdApiFun;
import com.xymtop.tayi.cmd.cmdbuilder.ArgsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 21:08
 */

@Component
public class CmdSystem {

    //指令map
    private   HashMap<String,CmdMap> cmdMaps = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;



    private HashMap<String, Method> argsBuilderMap = new HashMap<>();


    private HashMap<String, Method> apisMap = new HashMap<>();


    //对象
    private HashMap<String, Object> objMap = new HashMap<>();

    //初始化指令
    public  void init(){
        Map<String, Object> apis = applicationContext.getBeansWithAnnotation(CmdApi.class);
        for (Object api : apis.values()) {
            Method[] methods = api.getClass().getDeclaredMethods();
            for (Method method : methods) {
                CmdApiFun cmdApiFun = method.getAnnotation(CmdApiFun.class);
                if (cmdApiFun != null) {

                    apisMap.put(cmdApiFun.cmd(),method);


                    CmdMap cmdMap = new CmdMap();
                    cmdMap.setName(cmdApiFun.cmd());
                    cmdMap.setMethod(method);
                    cmdMap.setNeedArgs(cmdApiFun.needParam());
                    cmdMaps.put(cmdApiFun.cmd(),cmdMap);

                    objMap.put(cmdApiFun.cmd(),api);
                }

                //参数构建器
                ArgsBuilder argsBuilder = method.getAnnotation(ArgsBuilder.class);
                if (argsBuilder != null) {
                    argsBuilderMap.put(argsBuilder.value(),method);
                    objMap.put(argsBuilder.value(),api);
                }
            }
        }


        for (Method api : apisMap.values()){
            CmdMap cmdMap = new CmdMap();
            cmdMap.setName(api.getName());
            cmdMap.setMethod(api);
            cmdMap.setObj(objMap.get(api.getName()));

            //参数构建器
            Method argsBuilder = argsBuilderMap.get(api.getName());
            if (argsBuilder != null) {

                cmdMap.setArgsBuilder(argsBuilder);
                cmdMap.setNeedArgs(true);
            }else {
                cmdMap.setNeedArgs(false);
            }

            cmdMaps.put(api.getName(),cmdMap);
        }



    }


    //执行指令
    public Object exec(String cmd,Object... args) throws Exception {
        CmdMap cmdMap = cmdMaps.get(cmd);
        if (cmdMap == null) {
            throw new Exception("指令不存在");
        }
        return cmdMap.getMethod().invoke(applicationContext.getBean(cmdMap.getMethod().getDeclaringClass()),args);
    }

    //获取指令内容
    public CmdMap getCmdContent(String cmd){
        return cmdMaps.get(cmd);
    }
}
