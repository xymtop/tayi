package com.xymtop.tayi;


import com.xymtop.tayi.core.cmd.CmdSystem;
import com.xymtop.tayi.core.nft.NFTMeta;
import com.xymtop.tayi.core.vm.code.that.BlockUtils;
import com.xymtop.tayi.core.vm.code.that.That;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import com.xymtop.tayi.entity.Cat;
import lombok.Data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private int count;

    private List<Cat> cats = new ArrayList<>();

    public boolean addCat(){
        Cat cat = new Cat();
        cat.setName("老王");
        cats.add(cat);
        return true;
    }


    public int getName(){
        return count;
    }

    public void  addName(){
         count++;
    }

    @Override
    public ContractInfo info() {
        ContractInfo info = new ContractInfo();
        info.setName("hello,TaYi!");
        return info;
    }


    //获取单属性
    public String getHeight() throws Exception {
        That that = getThat();
        BlockUtils blockUtils = that.getBlockUtils();
        long chainBlockHeight = blockUtils.getChainBlockHeight();
        return "当前区块高度:" + chainBlockHeight;
    }

    //获取对象
    public  Object getObj() throws Exception {
        //执行其他
        NFTMeta nftMeta = new NFTMeta();

        nftMeta.setTitle("TaYi");
        nftMeta.setDescription("美好的生活从现在开始!");
        nftMeta.setImage("https://upload.wikimedia.org/wikipedia/zh/4/4a/Xinjiang_University_logo.png");

        nftMeta.setAttributes(Arrays.asList("hi","xju","xymtop"));

        return nftMeta;
    }

}
