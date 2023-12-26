package com.xymtop.tayi.graph.entity;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 5:49
 */

@Data
public class NFTGraphEdge {
//    源节点
    private NFTGraphNode source;

//    目标节点
    private NFTGraphNode target;

//    id
    private String edgeId;

}
