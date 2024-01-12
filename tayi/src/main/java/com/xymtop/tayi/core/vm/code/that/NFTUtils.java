package com.xymtop.tayi.core.vm.code.that;

import com.xymtop.tayi.core.graph.entity.NftLabel;
import com.xymtop.tayi.core.graph.entity.NodeRelationship;
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

    public boolean verifyAuth(String nftId,String address);

    //销毁 burning
    public  boolean burning(String nftId);

    //获取所有边
    public List<NodeRelationship> getAllEdges(String nftId);

    List<NodeRelationship> queryAllRelationInNfts(String nft1,String nft2);

    //有关系
    public boolean hasRelation(String nftId,String nft2,String type);

    //获取所有相邻节点
    public  List<NFTData> getAllNodes(String nftId);

    //search
    public  List<NFTData> search(NFTData nftData);

    //获取一个基础的新的nft
    public NFTData getNewBaseNFT();


    public boolean updateNFT(String id,NFTData nftData);

    //写入一个对象到NFT
    public  NFTData writeToNft(Object obj);

    //从NFT中获取写入的对象
    public Object getFromNft(NFTData nftData);

    //判断NFTid的类型
    public NftLabel checkType(String nftId);


    //根据id查询NFTdata
    public NFTData getNftDataById(String nftId);
}
