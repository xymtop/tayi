package com.xymtop.tayi.core.cmd.apis;

import com.xymtop.tayi.core.cmd.apis.ann.CmdApi;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApiFun;
import com.xymtop.tayi.core.cmd.cmdbuilder.ArgsBuilder;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.OprateMeta;
import com.xymtop.tayi.core.utils.jsonutils.XJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 1:04
 */

@Component
@CmdApi
public class NftApi {

    @Autowired
    private XJsonUtils xJsonUtils;

    @CmdApiFun(cmd = "createNFT")
    public NFTData createNFT(String user, NFTData nftData){
        System.out.println("创建NFT");
        //创建NFT唯一地址


        //设置所有权人


        //设置关系为拥有

        return nftData;
    }

    @ArgsBuilder("createNFT")
    public Object[] createNFTArgsBuilder(OperateEntity operate) {

        String sender = operate.getSender();
        OprateMeta oprateMeta = operate.getOprateMeta();

        //获取负载数据
        String payload = oprateMeta.getPayload();

        NFTData nftData = xJsonUtils.jsonToObj(payload, NFTData.class);

        return new Object[]{sender,nftData};
    }

}
