package com.xymtop.tayi.core.graph;

import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.*;
import java.nio.file.Path;
import java.util.*;

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
}
