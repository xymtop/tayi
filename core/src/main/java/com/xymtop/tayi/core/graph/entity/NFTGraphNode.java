package com.xymtop.tayi.core.graph.entity;

import com.xymtop.tayi.core.nft.NFTData;
import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 5:49
 */

@Data
public class NFTGraphNode {
    //节点数据
    private NFTData data;

    //边
    private NFTGraphEdge[] edges;
}
