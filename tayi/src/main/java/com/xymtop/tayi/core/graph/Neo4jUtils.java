package com.xymtop.tayi.core.graph;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.xymtop.tayi.core.graph.entity.NodeRelationship;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.nft.NFTMeta;
import lombok.Data;
import org.neo4j.cypher.internal.expressions.functions.E;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.*;
import org.neo4j.internal.batchimport.cache.NodeRelationshipCache;

import java.nio.file.Path;
import java.util.*;

@Data
public class Neo4jUtils {

    private final GraphDatabaseService graphDb;
    private final DatabaseManagementService managementService;

    public Neo4jUtils(String path) {
        this.managementService = new DatabaseManagementServiceBuilder(Path.of(path)).build();
        this.graphDb = managementService.database("neo4j"); // 使用默认数据库
        registerShutdownHook(managementService);
    }

    public void shutdownDatabase() {
        managementService.shutdown();
    }

    public String createNode(Label label, Map<String, Object> properties) {
        try (Transaction tx = graphDb.beginTx()) {
            Node node = tx.createNode(label);
            for (Map.Entry proper : properties.entrySet()){
                if (proper.getKey()!=null && proper.getValue() !=null){
                    node.setProperty(String.valueOf(proper.getKey()),proper.getValue());
                }
            }

            String nodeId = node.getElementId();
            tx.commit();
            return nodeId;
        }
    }


    //创建两个nft之间的关系
    public Relationship createNftRelationship(String startNodeId, String endNodeId, String relation) {
        try (Transaction tx = graphDb.beginTx()) {
            Node startNode = tx.findNode(Label.label(NFTUtils.LABEL), "address", startNodeId);
            Node endNode = tx.findNode(Label.label(NFTUtils.LABEL), "address", endNodeId);


            if (startNode == null){
                return null;
            }

            RelationshipType type = RelationshipType.withName(relation);
            Relationship relationship = startNode.createRelationshipTo(endNode, type);
            tx.commit();
            return relationship;
        }
    }

    //删除nft
    public boolean deleteNftNode(String nftAddress){
        try (Transaction tx = graphDb.beginTx()) {
            ResourceIterable<Relationship> allRelationships = tx.getAllRelationships();
            for (Relationship relationship : allRelationships) {
                //删除关系
                relationship.delete();
            }
            Node node = tx.findNode(Label.label(NFTUtils.LABEL), "address", nftAddress);
            if (node!=null){
                node.delete();
            }

            tx.commit();
        }
        return true;
    }



    public NFTData beanToNftData(Map<String, Object> map){
        Map<String,Object> newMap = new HashMap<>();
        for (String key : map.keySet()){
            if (key.startsWith("nft-")){
                String newKey = key.split("nft-")[1];
                newMap.put(newKey,map.get(key));
            }
        }

        NFTMeta meta = new NFTMeta();
       meta.setAttributes(newMap);

        NFTData nftData = new NFTData();
        nftData =  BeanUtil.mapToBean(map,NFTData.class,true);
        nftData.setMeta(meta);

        return nftData;
    }

    //获取到NFT数据
    public NFTData getById(String address){
        try (Transaction tx = graphDb.beginTx()) {
            Node node = tx.findNode(Label.label(NFTUtils.LABEL), "address", address);
            if (node == null){
                return null;
            }

            Map<String, Object> allProperties = node.getAllProperties();
            NFTData nftData = new NFTData();

            //转对象
            nftData = beanToNftData(allProperties);

            tx.commit();
            return nftData;
        }
    }



    public Relationship createRelationship(Node startNode, Node endNode, RelationshipType type, Map<String, Object> properties) {
        try (Transaction tx = graphDb.beginTx()) {
            Relationship relationship = startNode.createRelationshipTo(endNode, type);
            properties.forEach(relationship::setProperty);
            tx.commit();
            return relationship;
        }
    }

    public void deleteNode(Node node) {
        try (Transaction tx = graphDb.beginTx()) {
            node.delete();
            tx.commit();
        }
    }

    public boolean updateNftNodeProperties(String nftId,NFTData nftData) {
        Map<String, Object> properties = BeanUtil.beanToMap(nftData);
        try (Transaction tx = graphDb.beginTx()) {
            Node node = tx.findNode(Label.label(NFTUtils.LABEL), "nftId", nftId);

            if (node == null){
                return false;
            }
            properties.forEach(node::setProperty);
            tx.commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public void updateNodeProperties(Node node, Map<String, Object> properties) {
        try (Transaction tx = graphDb.beginTx()) {
            properties.forEach(node::setProperty);
            tx.commit();
        }
    }

    public void deleteRelationship(Relationship relationship) {
        try (Transaction tx = graphDb.beginTx()) {
            relationship.delete();
            tx.commit();
        }
    }

    public boolean deleteNftRelationship(String nftId1, String nftId2,String relationshipType){
        try (Transaction tx = graphDb.beginTx()) {

            Node node1 = tx.findNode(Label.label(NFTUtils.LABEL), "nftId", nftId1);
            Node node2 = tx.findNode(Label.label(NFTUtils.LABEL), "nftId", nftId2);

            if (node1 == null){
                return false;
            }

            if (node2 == null){
                return false;
            }
            RelationshipType relationshipTypeTemp = RelationshipType.withName(relationshipType);

            ResourceIterable<Relationship> relationships = node1.getRelationships();
            for (Relationship relationship : relationships){
                if (relationship.getType().equals(relationshipTypeTemp) && relationship.getOtherNode(node1).equals(node2)){
                    relationship.delete();
                    break;
                }
            }

            tx.commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 执行Cypher查询并返回结果列表。
     *
     * @param query 要执行的Cypher查询
     * @return 查询结果的列表，每个元素是一行数据的键值映射
     */
    public List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try (Transaction tx = graphDb.beginTx()) {
            Result result = tx.execute(query);
            while (result.hasNext()) {
                resultList.add(result.next());
            }
            tx.commit();
            return resultList;
        }
    }

    /**
     * 获取指定节点的所有关系。
     *
     * @param node 要查询的节点
     * @return 与该节点相关的所有关系
     */
    public List<Relationship> getAllRelationshipsOfNode(Node node) {
        List<Relationship> relationships = new ArrayList<>();
        try (Transaction tx = graphDb.beginTx()) {
            node.getRelationships().forEach(relationships::add);
            tx.commit();
            return relationships;
        }
    }

    //获取nft的所有节点
    public  List<NFTData> getAllNodes(String nftId) {

        List<NFTData> nodes = new ArrayList<>();

        try (Transaction tx = graphDb.beginTx()) {

            Node node = tx.findNode(Label.label(NFTUtils.LABEL), "address", nftId);

            if (node == null){
                return null;
            }
            ResourceIterable<Relationship> allRelationships = node.getRelationships();
            for (Relationship relationship : allRelationships){
                Node endNode = relationship.getEndNode();
                Node startNode = relationship.getStartNode();
                if (node == startNode){
                    NFTData nftData = nodeToNFT(endNode);
                    nodes.add(nftData);
                }else {
                    NFTData nftData = nodeToNFT(startNode);
                    nodes.add(nftData);
                }
            }
            tx.commit();
            return nodes;
        }
    }

    public  List<NodeRelationship> queryAllRelation(String nftId){
        try (Transaction tx = graphDb.beginTx()) {
            List<NodeRelationship> resultList = new ArrayList<>();

            Node node = tx.findNode(Label.label(NFTUtils.LABEL), "address", nftId);

            if (node == null){
                return null;
            }
            ResourceIterable<Relationship> allRelationships = node.getRelationships();

            for (Relationship relationship : allRelationships){
                RelationshipType type = relationship.getType();
                Node startNode = relationship.getStartNode();
                Node endNode = relationship.getEndNode();
                NFTData nftDataStart = nodeToNFT(startNode);
                NFTData nftDataEnd = nodeToNFT(endNode);

                NodeRelationship nodeRelationship = new NodeRelationship();
                nodeRelationship.setStartNode(nftDataStart);
                nodeRelationship.setEndNode(nftDataEnd);
                nodeRelationship.setType(type.name());

                resultList.add(nodeRelationship);
            }
            tx.commit();

            return resultList;
        }
    }

    //node转NFT
    public NFTData nodeToNFT(Node node) {
        Map<String, Object> allProperties = node.getAllProperties();
        NFTData nftData = new NFTData();

        //转对象
        nftData = beanToNftData(allProperties);

        return nftData;
    }

    private void registerShutdownHook(final DatabaseManagementService managementService) {
        Runtime.getRuntime().addShutdownHook(new Thread(managementService::shutdown));
    }

    /**
     * 根据 tokenId 获取单个 NFT 节点的信息。
     *
     * @param id NFT 的唯一标识符
     * @param nftLabel NFT 节点的标签
     * @return 包含 NFT 信息的 Map，如果找不到则返回 Optional.empty()
     */
    public Optional<Map<String, Object>> getNFTByTokenId(String id, Label nftLabel) {
        try (Transaction tx = graphDb.beginTx()) {
            // 构建查询
            String query = String.format("MATCH (n:%s {id: $tokenId}) RETURN n", nftLabel.name());
            Map<String, Object> parameters = Collections.singletonMap("tokenId", id);

            // 执行查询
            Result result = tx.execute(query, parameters);
            if (result.hasNext()) {
                Node nftNode = (Node) result.next().get("n");
                Map<String, Object> nftProperties = nftNode.getAllProperties();
                tx.commit();
                return Optional.of(nftProperties);
            } else {
                tx.commit();
                return Optional.empty();
            }
        }
    }


    /**
     * 根据多个属性和值获取节点。
     *
     * @param label 节点的标签
     * @param properties 要查询的属性及其值的映射
     * @return 包含匹配节点信息的列表
     */
    public List<Map<String, Object>> findNodesByProperties(Label label, Map<String, Object> properties) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try (Transaction tx = graphDb.beginTx()) {
            // 构建匹配条件
            StringBuilder matchCondition = new StringBuilder();
            for (String key : properties.keySet()) {
                matchCondition.append(String.format("n.%s = $%s AND ", key, key));
            }
            // 移除最后的 "AND "
            String conditions = matchCondition.substring(0, matchCondition.length() - 5);

            // 构建查询
            String query = String.format("MATCH (n:%s) WHERE %s RETURN n", label.name(), conditions);

            // 执行查询
            Result result = tx.execute(query, properties);
            while (result.hasNext()) {
                Node node = (Node) result.next().get("n");
                Map<String, Object> nodeProperties = node.getAllProperties();
                resultList.add(nodeProperties);
            }
            tx.commit();
            return resultList;
        }
    }

    //清空数据库
    public  void clearDatabase() {
        try (Transaction tx = graphDb.beginTx()) {
            tx.execute("MATCH (n) DETACH DELETE n");
            tx.commit();
        }
    }


    //根据对象来查询NFT
    public List<NFTData> queryNFTByObject(NFTData nftData) {
        List<NFTData> nftDataList = new ArrayList<>();
        try (Transaction tx = graphDb.beginTx()) {
            Map<String, Object> properties = NFTUtils.createNFTMap(nftData);

            // 构建匹配条件
            StringBuilder matchCondition = new StringBuilder();
            for (String key : properties.keySet()) {
                matchCondition.append(String.format("n.%s = $%s AND ", key, key));
            }

            // 移除最后的 "AND "
            String conditions = matchCondition.substring(0, matchCondition.length() - 5);

            // 构建查询
            String query = String.format("MATCH (n:%s) WHERE %s RETURN n",NFTUtils.LABEL, conditions);

            // 执行查询
            Result result = tx.execute(query, properties);
            while (result.hasNext()) {
                Node node = (Node) result.next().get("n");

                NFTData data = nodeToNFT(node);

                nftDataList.add(data);
            }
            tx.commit();
            return nftDataList;
        }catch (Exception e){
            return null;
        }
    }

}
