package com.xymtop.tayi.core.consensus.dpos;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 17:29
 */
import java.util.*;

public class DPoSExample {
    static class Node {
        String name;
        int votes;

        Node(String name) {
            this.name = name;
            this.votes = 0;
        }

        void receiveVote() {
            votes++;
        }

        @Override
        public String toString() {
            return name + ": " + votes + " votes";
        }
    }

    public static void main(String[] args) {
        // 创建节点
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("Node1"));
        nodes.add(new Node("Node2"));
        nodes.add(new Node("Node3"));

        // 模拟投票
        vote(nodes, "Node1");
        vote(nodes, "Node1");
        vote(nodes, "Node2");

        // 显示投票结果
        for (Node node : nodes) {
            System.out.println(node);
        }

        // 选择区块生产者
        Node blockProducer = selectBlockProducer(nodes);
        System.out.println("Selected Block Producer: " + blockProducer.name);
    }

    static void vote(List<Node> nodes, String nodeName) {
        for (Node node : nodes) {
            if (node.name.equals(nodeName)) {
                node.receiveVote();
                break;
            }
        }
    }

    static Node selectBlockProducer(List<Node> nodes) {
        return Collections.max(nodes, Comparator.comparingInt(n -> n.votes));
    }
}
