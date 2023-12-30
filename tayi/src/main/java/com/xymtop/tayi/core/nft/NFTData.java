package com.xymtop.tayi.core.nft;

import lombok.Data;

import java.util.Date;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 1:36
 */
@Data
public class NFTData {

    //唯一标识符
    private String address;

    //    所有者
    private String owner;

    //    元数据
    private NFTMeta meta;

    //    资源链接
    private String resource;

    //    创建时间
    private Date time;

}
