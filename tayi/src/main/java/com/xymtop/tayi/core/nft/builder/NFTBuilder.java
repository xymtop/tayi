package com.xymtop.tayi.core.nft.builder;

import cn.hutool.core.lang.UUID;
import com.xymtop.tayi.core.inter.Builder;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.nft.NFTMeta;
import com.xymtop.tayi.core.utils.encrypt.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 5:14
 */
@Component
public class NFTBuilder implements Builder<NFTMeta, NFTData> {


    @Autowired
    HashUtils hashUtils;

    @Override
    public NFTData build(NFTMeta source) {

        return null;
    }

    //获取一个基础的NFT信息
    public    NFTData  getBaseNFT(){

        NFTData nftData = new NFTData();

        nftData.setTime(new Date());
        nftData.setResource(null);
        nftData.setAddress(hashUtils.hashHex(UUID.fastUUID().toString()));

        return nftData;
    }
}
