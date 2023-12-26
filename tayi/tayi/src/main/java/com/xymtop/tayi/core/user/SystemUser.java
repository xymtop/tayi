package com.xymtop.tayi.core.user;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 1:40
 */

//系统用户

@Data
public class SystemUser {

    //用户公钥
    private String address;

    //用户私钥
    private String privateKey;

    //用户交易次数
    private long nonce;

    //用户余额
    private long balance;

}
