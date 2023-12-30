package com.xymtop.tayi.core.vm.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 14:47
 */

//合约结构描述
@Data
public class Contract {

     //合约id
     String id;

     //合约地址
     String address;

     //状态数据地址
     String statusAddress;

     //在本地的包路径
     String path;

     //创建人
     String creator;

     //合约信息体
     ContractInfo contractInfo;

     //合约类名
     String className;

     //合约本体
     @JsonIgnore
     Object contract;

     //所有方法
     @JsonIgnore
     HashMap<String, Method> methods = new HashMap<>();

     //所有参数
     @JsonIgnore
    HashMap<String, Field> fields =   new HashMap<>();
}
