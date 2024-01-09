package com.xymtop.tayi.core.vm.code.that.impl;

import com.xymtop.tayi.core.vm.TaYiVM;
import com.xymtop.tayi.core.vm.code.that.ContractUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/10 0:15
 */

@Component
@Lazy
public class ContractUtilsImpl implements ContractUtils {

    @Autowired
    private TaYiVM taYiVM;

    @Override
    public Object call(String id, String method, String args) throws Exception {
        return taYiVM.call(id,method,args);
    }

    @Override
    public Object call(String id, String method) throws Exception {
        return taYiVM.call(id,method);
    }
}
