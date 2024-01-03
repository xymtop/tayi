package com.xymtop.tayi.core.system;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/28 16:55
 */

@Data
@Component
public class TestObject {
    //测试对象
    private Class testObject;

    //测试方法
    private String funName;

    //测试参数
    private Object[] args;
}
