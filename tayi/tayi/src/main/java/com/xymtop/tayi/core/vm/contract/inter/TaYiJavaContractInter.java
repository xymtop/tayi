package com.xymtop.tayi.core.vm.contract.inter;

import com.xymtop.tayi.core.vm.code.That;
import com.xymtop.tayi.core.vm.contract.ContractInfo;

public interface TaYiJavaContractInter {
    //部署
   public void deploy();

    //启动
    public void start();

    //停止运行
    public void stop( );

    //合约信息
    public ContractInfo info();

}
