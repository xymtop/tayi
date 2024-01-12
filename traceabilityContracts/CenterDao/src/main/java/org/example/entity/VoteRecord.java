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
public class VoteRecord {
    private String voter;
    private BigInteger voteTime;
    private boolean vote;

}