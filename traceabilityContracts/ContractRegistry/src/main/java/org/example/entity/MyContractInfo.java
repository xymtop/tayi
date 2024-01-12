package org.example.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/10 22:59
 */

@Data
public class MyContractInfo {
    private String contractAddress; // 合约的地址
    private String name;            // 合约的名字
    private String description;     // 合约的描述
    private String owner;           // 提交合约的用户地址
    private String applicationTime; // 提交合约的时间
    private List<String> whitepaperIPFSHashes; // 白皮书的IPFS哈希数组
    private String status;          // 合约的状态
}
