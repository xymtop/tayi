package com.xymtop.tayi.core.oprate.listener;

public interface PoolListener {
    void onTransaction(OperateEvent event) throws Exception;
}
