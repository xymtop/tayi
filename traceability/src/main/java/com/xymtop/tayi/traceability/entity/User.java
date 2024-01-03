package com.xymtop.tayi.traceability.entity;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/4 2:12
 */

@Data
public class User {
    String nickname;
    boolean gender;
    String  email;
    String bio;
    String phone;
    boolean isBanned;
    String role;
}
