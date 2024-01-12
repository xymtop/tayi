package com.xymtop.tayi.core.vm.code.that.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.xymtop.tayi.core.graph.entity.NftLabel;
import com.xymtop.tayi.core.graph.entity.NodeRelationship;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.nft.NFTMeta;
import com.xymtop.tayi.core.vm.code.that.NFTUtils;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 22:39
 */

@Component
@Lazy
public class NFTUtilsImpl implements NFTUtils {

    @Autowired
    com.xymtop.tayi.core.graph.NFTUtils nftUtils;

    @Override
    public Object mining(NFTData nftData) {
        String id =  nftUtils.createNFT(nftData);
        nftData.setAddress(id);
        return nftData;
    }

    @Override
    public boolean verify(String nftId, String address) {
        NFTData nftData = nftUtils.queryNFT(nftId);
        return address.equals(nftData.getAddress());
    }

    @Override
    public boolean transfer(String nftId, String to) {
        try {
            NFTData nftData = nftUtils.queryNFT(nftId);

            nftData.setAddress(to);

           return   nftUtils.updateNFT(nftId,nftData);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean auth(String nftId, String address) {
        Relationship auth = nftUtils.createNFTRelation(nftId, address, "auth");
        return auth != null;
    }

    @Override
    public boolean verifyAuth(String nftId, String address) {
        List<NodeRelationship> nodeRelationships = queryAllRelationInNfts(nftId, address);
        for (NodeRelationship nodeRelationship : nodeRelationships){
            if ("auth".equals(nodeRelationship.getType())){
                return  true;
            }
        }
        return false;
    }

    @Override
    public boolean burning(String nftId) {
        return nftUtils.deleteNFT(nftId);
    }

    @Override
    public List<NodeRelationship> getAllEdges(String nftId) {
        return nftUtils.queryAllRelation(nftId);
    }

    @Override
    public List<NodeRelationship> queryAllRelationInNfts(String nft1, String nft2) {
        return nftUtils.queryAllRelationInNfts(nft1,nft2);
    }

    @Override
    public boolean hasRelation(String nftId, String nft2, String type) {
        List<NodeRelationship> nodeRelationships = queryAllRelationInNfts(nftId, nft2);
        for (NodeRelationship nodeRelationship : nodeRelationships){
            if (type.equals(nodeRelationship.getType())){
                return  true;
            }
        }
        return false;
    }

    @Override
    public List<NFTData> getAllNodes(String nftId) {
        return nftUtils.getAllNftNodes(nftId);
    }

    @Override
    public List<NFTData> search(NFTData nftData) {
        return nftUtils.queryNft(nftData);
    }

    @Override
    public NFTData getNewBaseNFT() {

        return nftUtils.getBaseNft();
    }

    @Override
    public boolean updateNFT(String id, NFTData nftData) {
        return nftUtils.updateNFT(id, nftData);
    }

    @Override
    public NFTData writeToNft(Object obj) {
        Map<String, Object> objectMap = BeanUtil.beanToMap(obj);
        NFTData newBaseNFT = getNewBaseNFT();
        NFTMeta nftMeta = new NFTMeta();
        nftMeta.setAttributes(objectMap);
        newBaseNFT.setMeta(nftMeta);
        return newBaseNFT;
    }

    @Override
    public Object getFromNft(NFTData nftData) {
        NFTMeta meta = nftData.getMeta();
        if (meta!=null){
            if (meta.getAttributes()!=null){
              return     BeanUtil.mapToBean(nftData.getMeta().getAttributes(), Object.class, true);
            }
        }
        return null;
    }

    @Override
    public NftLabel checkType(String nftId) {
        NFTData nftData = getNftDataById(nftId);
        if (nftData != null){
            return nftData.getNftLabel();
        }
        return null;
    }

    @Override
    public NFTData getNftDataById(String nftId) {
        return nftUtils.queryNFT(nftId);
    }
}
