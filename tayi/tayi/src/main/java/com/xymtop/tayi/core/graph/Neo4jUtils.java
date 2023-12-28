package com.xymtop.tayi.core.graph;

import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public Node createNode(Label label, Map<String, Object> properties) {
        try (Transaction tx = graphDb.beginTx()) {
            Node node = tx.createNode(label);
            properties.forEach(node::setProperty);
            tx.commit();
            return node;
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
}
