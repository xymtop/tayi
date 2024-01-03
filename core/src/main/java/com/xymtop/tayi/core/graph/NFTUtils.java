package com.xymtop.tayi.core.graph;

import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.nft.NFTMeta;
import org.neo4j.graphdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NFTUtils {

    @Autowired
    private  Neo4jUtils neo4jUtils;

    public NFTUtils(Neo4jUtils neo4jUtils) {
        this.neo4jUtils = neo4jUtils;
    }

    public String createNFT(NFTData nftData) {
        Map<String, Object> properties = convertNFTDataToProperties(nftData);
        return neo4jUtils.createNode(Label.label("NFT"), properties);
    }

    public void deleteNFT(Node nftNode) {
        neo4jUtils.deleteNode(nftNode);
    }

    public void updateNFTOwner(Node nftNode, String newOwner) {
        neo4jUtils.updateNodeProperties(nftNode, Map.of("owner", newOwner));
    }

    public void updateNFTMetaData(Node nftNode, NFTMeta newMetaData) {
        Map<String, Object> properties = convertNFTMetaDataToProperties(newMetaData);
        neo4jUtils.updateNodeProperties(nftNode, properties);
    }

    public void updateNFTImage(Node nftNode, String newImageUrl) {
        neo4jUtils.updateNodeProperties(nftNode, Map.of("image", newImageUrl));
    }

    public Relationship createOrUpdateRelationshipBetweenNFTs(Node startNode, Node endNode, String relationshipType, Map<String, Object> properties) {
        return neo4jUtils.createRelationship(startNode, endNode, RelationshipType.withName(relationshipType), properties);
    }

    private Map<String, Object> convertNFTDataToProperties(NFTData nftData) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("address", nftData.getAddress());
        properties.put("owner", nftData.getOwner());
        properties.put("resource", nftData.getResource());
        properties.put("time", nftData.getTime().getTime()); // Storing as timestamp

        NFTMeta meta = nftData.getMeta();
        properties.putAll(convertNFTMetaDataToProperties(meta));

        return properties;
    }

    private Map<String, Object> convertNFTMetaDataToProperties(NFTMeta meta) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("meta_title", meta.getTitle());
        properties.put("meta_description", meta.getDescription());
        properties.put("meta_image", meta.getImage());
        // Additional logic to handle attributes if needed
        return properties;
    }
    //获取所有的NFT
    public List<Node> getAllNFTs() {
        List<Node> nodes = new ArrayList<>();
        List<Map<String, Object>> mapList = neo4jUtils.executeQuery("MATCH (n:NFT) RETURN n");
        for (Map<String, Object> map : mapList) {
            nodes.add((Node) map.get("n"));
        }
        return nodes;
    }
}
