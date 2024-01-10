package com.xymtop.tayi.core.graph;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.graph.entity.NodeRelationship;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.nft.NFTMeta;
import com.xymtop.tayi.core.utils.encrypt.HashUtils;
import org.neo4j.graphdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NFTUtils {

    @Autowired
    private  Neo4jUtils neo4jUtils;

    @Autowired
    private HashUtils hashUtils;

    public  static final  String LABEL="NFT";

    public NFTUtils(Neo4jUtils neo4jUtils) {
        this.neo4jUtils = neo4jUtils;
    }

    public NFTData getBaseNft(){
        NFTData nftData = new NFTData();

        nftData.setTime(new Date());
        nftData.setResource(null);
        nftData.setAddress(hashUtils.hashHex(UUID.fastUUID().toString()));

        return nftData;
    }

    public void test(){
        neo4jUtils.clearDatabase();
//        NFTData nftData = new NFTData();
//        nftData.setAddress("1");
//
//        NFTData nftData1 = new NFTData();
//        nftData1.setAddress("2");
//
//        String nft = createNFT(nftData);
//        String nft1 = createNFT(nftData1);
//
//        Relationship nftRelation = createNFTRelation(nft, nft1,"朋友");
//
//        List<NFTData> allNodes = neo4jUtils.getAllNodes(nft);
//
//        List<NodeRelationship> nodeRelationships = queryAllRelation(nft);
//
//
//        boolean deleteNFT = deleteNFT(nft);
//
//        NFTData nftData2 = queryNFT(nft1);
//
//        nftData2.setResource("666");
//
//        boolean b = updateNFT(nft1, nftData2);
//
//        boolean deleteNFTRelation = deleteNFTRelation(nft, nft1, "朋友");
//
//        List<NFTData> nftDataList = neo4jUtils.queryNFTByObject(nftData);

//        System.out.println(nftDataList);


    }

    //计算NFT地址
    public String calculateNFTAddress(NFTData nftData) {
        return hashUtils.hashHex(JSONUtil.toJsonStr(nftData));
    }

    public static Map<String,Object>  createNFTMap(NFTData nftData) {
        HashMap<String,Object>  map = new HashMap<>();
        JSONObject jsonObject = JSONUtil.parseObj(nftData);
        for (String key : jsonObject.keySet()){

            if ("meta".equals(key)){
                Object meta = jsonObject.get("meta");
                if (meta != null){
                        Object attributes =((JSONObject) meta).get("attributes");
                        Map<String, Object> objectMap = BeanUtil.beanToMap(attributes);
                        for (String subKey : objectMap.keySet()){
                            map.put("nft-"+subKey,objectMap.get(subKey));
                        }


                }

            }else if ("time".equals(key)){
                Object object = jsonObject.get(key);
                String format = DateUtil.format((Date) object, "yyyy-MM-dd HH:mm:ss");
                map.put(key,format);
            }
            else {
                map.put(key,jsonObject.get(key));
            }

        }
        return map;
    }

    //创建NFT
    public String createNFT(NFTData nftData) {
        if (nftData.getAddress() == null){
            String address = calculateNFTAddress(nftData);
            nftData.setAddress(address);
        }

        //创建便于储存的map格式
        Map<String, Object> nftMap =NFTUtils.createNFTMap(nftData);
        String node = neo4jUtils.createNode(Label.label(LABEL),nftMap);
        return nftData.getAddress();
    }

    //创建两个NFT的关系
    public Relationship createNFTRelation(String nftId1, String nftId2,String relation) {
        Relationship nftRelationship = neo4jUtils.createNftRelationship(nftId1, nftId2, relation);
        return nftRelationship;
    }


    //删除NFT
    public boolean deleteNFT(String nftId) {
        return neo4jUtils.deleteNftNode(nftId);
    }

    //删除一个关系
    public boolean deleteNFTRelation(String nftId1, String nftId2,String relation) {
        return neo4jUtils.deleteNftRelationship(nftId1, nftId2, relation);
    }

    //修改NFT
    public boolean updateNFT(String nftId, NFTData nftData) {
      return   neo4jUtils.updateNftNodeProperties(nftId,nftData);
    }

    //查询NFT
    public NFTData queryNFT(String nftId) {
        return neo4jUtils.getById(nftId);
    }

    //查询所有的关系
    public List<NodeRelationship> queryAllRelation(String nftId) {
        return neo4jUtils.queryAllRelation(nftId);
    }
    public List<NodeRelationship> queryAllRelationInNfts(String nftId1,String nftId2) {
        List<NodeRelationship> newShips = new ArrayList<>();

        List<NodeRelationship> nodeRelationships = neo4jUtils.queryAllRelation(nftId1);
        for (NodeRelationship nodeRelationship : nodeRelationships){
            NFTData startNode = nodeRelationship.getStartNode();
            NFTData endNode = nodeRelationship.getEndNode();
            if (nftId2.equals(startNode.getAddress())|| nftId2.equals(endNode.getAddress())){
                newShips.add(nodeRelationship);
            }
        }

        return newShips;
    }

    public List<NFTData> getAllNftNodes(String nftId){
        return neo4jUtils.getAllNodes(nftId);
    }

    public List<NFTData> queryNft(NFTData nftData){
        return neo4jUtils.queryNFTByObject(nftData);
    }

}
