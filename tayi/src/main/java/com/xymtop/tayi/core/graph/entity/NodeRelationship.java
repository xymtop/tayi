package com.xymtop.tayi.core.graph.entity;

import com.xymtop.tayi.core.nft.NFTData;
import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/10 3:51
 */

@Data
public class NodeRelationship {

    String type;

    NFTData startNode;

    NFTData endNode;
}
