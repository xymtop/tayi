package com.xymtop.tayi.core.vm.code.that.impl;

import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.vm.code.that.NFTUtils;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

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

        return false;
    }

    @Override
    public boolean transfer(String nftId, String to) {
        return false;
    }

    @Override
    public boolean auth(String nftId, String address) {
        return false;
    }

    @Override
    public boolean burning(String nftId) {
        return false;
    }

    @Override
    public List<Object> getAllEdges(String nftId) {
        return null;
    }

    @Override
    public List<NFTData> getAllNodes(String nftId) {
        return null;
    }

    @Override
    public List<NFTData> search(String nftKey) {
        return null;
    }
}
