package org.example.entity;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/10 22:40
 */

@Data
public class Proposal {
    private String id;
    private String title;
    private String descriptionCID; // IPFS上的提案内容标识符
    private String creator; // Solidity的address在Java中通常表示为String
    private String deadline;
    private boolean executed;
    private int positiveVotes;
    private int negativeVotes;
}