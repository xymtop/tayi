package com.xymtop.tayi.core.cmd.apis;

import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.ai.aioperate.TaYiAiVM;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApi;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApiFun;
import com.xymtop.tayi.core.cmd.cmdbuilder.ArgsBuilder;
import com.xymtop.tayi.core.oprate.OperateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/8 22:35
 */


@CmdApi
@Component
public class AiApi {
    @Autowired
    TaYiAiVM taYiAiVM;




    @CmdApiFun(cmd = "deployAI")
    public Object deployAI(String aiHash) throws IOException {
        return taYiAiVM.deployAI(aiHash);
    }


    @ArgsBuilder("deployAI")
    public Object[] deployAIArgsBuilder(OperateEntity operate){
        String payload = operate.getOprateMeta().getPayload();
        Object aiHash = JSONUtil.parseObj(payload).get("aiHash");
        return new Object[]{aiHash};
    }



    @CmdApiFun(cmd = "trainAI")
    public Object trainAI(String aiHash,String trainDataAddress){

        return taYiAiVM.trainAI(aiHash,trainDataAddress);
    }

    @ArgsBuilder("trainAI")
    public Object[] trainAIArgsBuilder(OperateEntity operate){
        String payload = operate.getOprateMeta().getPayload();
        Object aiHash = JSONUtil.parseObj(payload).get("aiHash");
        Object trainDataAddress = JSONUtil.parseObj(payload).get("trainDataAddress");

        return  new Object[]{aiHash,trainDataAddress};
    }


    @CmdApiFun(cmd = "callAI")
    public Object callAI(String aiHash,String aiInput) throws IOException {
        return  taYiAiVM.callAI(aiHash,aiInput);
    }

    @ArgsBuilder("callAI")
    public Object[] callAIArgsBuilder(OperateEntity operate){
        String payload = operate.getOprateMeta().getPayload();
        Object aiHash = JSONUtil.parseObj(payload).get("aiHash");
        Object aiInput = JSONUtil.parseObj(payload).get("aiInput");
        return  new Object[]{aiHash,aiInput};
    }
}
