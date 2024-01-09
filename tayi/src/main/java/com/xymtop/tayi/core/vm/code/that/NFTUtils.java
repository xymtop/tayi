package com.xymtop.tayi.core.vm.code.that;

import com.xymtop.tayi.core.nft.NFTData;

import java.util.List;

public interface NFTUtils {

    //minting
    public Object mining(NFTData nftData);

    //verify
    public  boolean verify(String nftId,String address);

    //transfer
    public  boolean transfer(String nftId,String to);

    //auth
    public  boolean auth(String nftId,String address);

    //销毁 burning
    public  boolean burning(String nftId);

    //获取所有边
    public List<Object> getAllEdges(String nftId);

    //获取所有相邻节点
    public  List<NFTData> getAllNodes(String nftId);

    //search
    public  List<NFTData> search(String nftKey);


}
