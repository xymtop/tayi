package com.xymtop.tayi.core.vm.code.that;

public interface ContractUtils {
    //调用合约
    Object call(String id,String method, String args) throws Exception;

    Object call(String id,String method) throws Exception;
}
