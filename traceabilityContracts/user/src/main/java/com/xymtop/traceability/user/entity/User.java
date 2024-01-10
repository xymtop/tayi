package com.xymtop.traceability.user.entity;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/10 13:38
 */
@Data
public class User {
   String account;
   String nickname;
   boolean gender;
   String email;
   String bio;
   String phone;
   String role;
}
