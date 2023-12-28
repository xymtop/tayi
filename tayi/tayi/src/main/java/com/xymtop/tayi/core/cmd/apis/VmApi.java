package com.xymtop.tayi.core.cmd.apis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApi;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApiFun;
import com.xymtop.tayi.core.cmd.cmdbuilder.ArgsBuilder;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.utils.jsonutils.XJsonUtils;
import com.xymtop.tayi.core.vm.TaYiVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 22:15
 */

//虚拟机执行代码
@Component
@CmdApi
public class VmApi {


    @Autowired
    TaYiVM taYiVM;



    //部署合约
    @CmdApiFun(cmd = "deployContract")
    public String deployContract(String hash) throws Exception {
       return  taYiVM.deploy(hash);
    }

    @ArgsBuilder("deployContract")
    public Object[] deployContractArgsBuilder(OperateEntity operate) {
          return new String[]{operate.getOprateMeta().getPayload()};
    }


    //执行合约方法
    @CmdApiFun(cmd = "executeContract")
    public Object executeContract(String id,String method, String... args) throws Exception {

        if (args == null||args.length==0){
            return taYiVM.call(id,method);
        }
        //判断是否为无参函数
        return  taYiVM.call(id,method,args);
    }



    @ArgsBuilder("executeContract")
    public Object[] executeContractArgsBuilder(OperateEntity operate) {
        String payload = operate.getOprateMeta().getPayload();
        if (payload ==  null){
            throw new RuntimeException("函数或参数错误");
        }
        JSONObject parseObj = JSONUtil.parseObj(payload);
        String id = parseObj.getStr("id");
        String method = parseObj.getStr("method");

        String argsStr = String.valueOf(parseObj.get("args"));

        if (StrUtil.isEmpty(id) || StrUtil.isEmpty(method)){
            throw new RuntimeException("函数或参数错误");
        }
        if (argsStr.equals("null")){
            argsStr = null;
        }
        return new Object[]{id,method,argsStr};
    }
//
//    //获取合约信息
//    @CmdApiFun(cmd = "infoContract")
//    public void infoContract() {
//
//    }
//
//    @ArgsBuilder("infoContract")
//    public Object[] infoContractArgsBuilder(OperateEntity operate) {
//
//    }
}
