package com.xymtop.tayi;


import com.xymtop.tayi.core.cmd.CmdSystem;
import com.xymtop.tayi.core.vm.code.That;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import lombok.Data;


import java.io.Serializable;

//为了实现自己的合约内容并且调用，你只需要7步
/*
* 1. 继承TaYiJavaContract
* 2.实现Serializable接口,方便序列化
* 3.实现info方法，注册合约信息
* 4.编写自己的方法
* 5.打包为jar包并且上传到IPFS
* 6.在TaYi中部署合约
* 7.调用智能合约方法
* */

@Data
public class Application extends TaYiJavaContract implements Serializable {

    @Override
    public ContractInfo info() {
        ContractInfo info = new ContractInfo();
        info.setName("hello,TaYi!");
        return info;
    }


    public String getHeight() throws Exception {
        That that = getThat();
        CmdSystem cmdSystem = that.getCmdSystem();
        long h = (long) cmdSystem.exec("getBlockHeight");
        return "当前区块高度:" + h;
    }
}
