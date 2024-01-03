package com.xymtop.tayi.core.cmd.apis;

import cn.hutool.core.lang.UUID;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApi;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApiFun;
import com.xymtop.tayi.core.cmd.cmdbuilder.ArgsBuilder;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.nft.NFTMeta;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.OprateMeta;
import com.xymtop.tayi.core.utils.encrypt.HashUtils;
import com.xymtop.tayi.core.utils.jsonutils.XJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    @Autowired
    HashUtils hashUtils;

    @CmdApiFun(cmd = "createNFT")
    public NFTData createNFT(String user, NFTMeta nftMeta){

        System.out.println("创建NFT");
        NFTData nftData = new NFTData();
        nftData.setMeta(nftMeta);

        //设置所有权人
        nftData.setOwner(user);
        nftData.setTime(new Date());
        nftData.setResource(null);
        //创建NFT唯一地址
        String id = hashUtils.hashHex(UUID.randomUUID().toString());
        nftData.setAddress(id);
        //获取到用户


        //设置关系为拥有

        return nftData;
    }

    @ArgsBuilder("createNFT")
    public Object[] createNFTArgsBuilder(OperateEntity operate) {

        String sender = operate.getSender();
        OprateMeta oprateMeta = operate.getOprateMeta();

        //获取负载数据
        String payload = oprateMeta.getPayload();

        NFTMeta nftMeta = xJsonUtils.jsonToObj(payload, NFTMeta.class);

        return new Object[]{sender,nftMeta};
    }

}
