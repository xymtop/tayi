package com.xymtop.tayi.core.vm.contract.inter;

import com.xymtop.tayi.core.vm.code.that.That;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 21:19
 */

@Data
public class TaYiJavaContract implements Serializable, TaYiJavaContractInter{

    public That that;

    @Override
    public void deploy() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public ContractInfo info() {
        return new ContractInfo();
    }

}
