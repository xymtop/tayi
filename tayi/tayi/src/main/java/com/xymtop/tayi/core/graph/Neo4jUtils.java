package com.xymtop.tayi.core.graph;

import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.*;
import java.nio.file.Path;
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

    public Result executeQuery(String query) {
        try (Transaction tx = graphDb.beginTx()) {
            Result result = tx.execute(query);
            tx.commit();
            return result;
        }
    }

    private void registerShutdownHook(final DatabaseManagementService managementService) {
        Runtime.getRuntime().addShutdownHook(new Thread(managementService::shutdown));
    }
}
