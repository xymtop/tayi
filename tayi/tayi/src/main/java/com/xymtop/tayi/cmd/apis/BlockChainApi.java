package com.xymtop.tayi.cmd.apis;

import com.xymtop.tayi.cmd.apis.ann.CmdApi;
import com.xymtop.tayi.cmd.apis.ann.CmdApiFun;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 21:05
 */

@Component
@CmdApi
public class BlockChainApi {
    //获取区块高度

    @CmdApiFun(cmd = "getBlockHeight")
    public int getBlockHeight(){
        return 0;
    }

    //获取系统时间戳
    @CmdApiFun(cmd = "getTimestamp")
    public long getTimestamp(){
        return 0;
    }

    //获取当前区块id

    @CmdApiFun(cmd = "getBlockId")
    public String getBlockId(){

        return "";
    }

}
