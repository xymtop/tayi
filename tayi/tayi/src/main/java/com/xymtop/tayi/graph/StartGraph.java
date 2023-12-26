package com.xymtop.tayi.graph;

import com.xymtop.tayi.core.nft.NFTAttribute;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.nft.NFTMeta;
import com.xymtop.tayi.system.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 5:46
 */
@Component
public class StartGraph implements Runner {

    @Autowired
    private NFTUtils nftUtils;

    @Override
    public void run()   {
//        模拟NFTData数据
        // 创建NFT属性
        NFTAttribute colorAttribute = new NFTAttribute();
        colorAttribute.setTraitType("Color");
        colorAttribute.setTraitValue("Blue");

        NFTAttribute sizeAttribute = new NFTAttribute();
        sizeAttribute.setTraitType("Size");
        sizeAttribute.setTraitValue("Large");

        // 创建NFT元数据
        NFTMeta meta = new NFTMeta();
        meta.setTitle("My Awesome NFT");
        meta.setDescription("This is a description of my awesome NFT");
        meta.setImage("https://example.com/nft-image.jpg");
        meta.setAttributes(Arrays.asList(colorAttribute, sizeAttribute));

        // 创建NFT数据
        NFTData nftData = new NFTData();
        nftData.setAddress("0x1234567890abcdef");
        nftData.setOwner("0xabcdef1234567890");
        nftData.setMeta(meta);
        nftData.setResource("https://example.com/nft-resource");
        nftData.setTime(new Date());

        nftUtils.createNFT(nftData);

        System.out.println("StartGraph");
    }
}
