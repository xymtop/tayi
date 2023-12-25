package com.xymtop.tayi.core.block;

import com.xymtop.tayi.core.oprate.OperateEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 2:12
 */

//区块的结构
@Data
public class Block {
//    区块头
    private BlockHead blockHead;

//    操作列表
    private List<OperateEntity> operateList;

    //区块大小
    private String blockSize;

//    额外信息
    private String extraData;
}
