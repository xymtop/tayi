package com.xymtop.tayi.core.oprate;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 6:27
 */

//操作发送的原始消息
@Data
public class OperateMessage {
    //发送人
    private String address;

    //签名
    private String signature;

   //数据
   private String data;

}
