package com.xymtop.tayi.test;

import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.TayiApplication;
import com.xymtop.tayi.core.nft.NFTMeta;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.OperateReceipt;
import com.xymtop.tayi.core.oprate.OprateMeta;
import com.xymtop.tayi.core.oprate.execute.OperateExecuter;
import com.xymtop.tayi.core.vm.TaYiVM;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/30 0:47
 */
public class TestNft {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(TayiApplication.class, args);
        OperateExecuter executer = run.getBean(OperateExecuter.class);


        //构造测试数据
        OperateEntity operateEntity = new OperateEntity();

        operateEntity =  testBuildAddUser(operateEntity);

        //执行交易
        OperateReceipt executeRes = executer.execute(operateEntity);

        //打印结果
        System.out.println(executeRes);
    }


    //构建基础信息
    private static OperateEntity buildBaseInfo(OperateEntity operateEntity){

        operateEntity.setSender("0x1048f6dE58Fe9A82D6aa0245f3aDBEDCA08A5FA4");

        operateEntity.setRecipient("0x1048f6dE58Fe9A82D6aa0245f3aDBEDCA08A5FA6");

        return operateEntity;
    }


    //测试构建用户新增操作
    private static OperateEntity testBuildAddUser(OperateEntity operateEntity) {
       operateEntity =   buildBaseInfo(operateEntity);

       operateEntity.setOperateCmd("addUser");

        OprateMeta oprateMeta = new OprateMeta();

        operateEntity.setOprateMeta(oprateMeta);

       return operateEntity;
    }

    //测试添加NFT
    private static OperateEntity testBuildAddNFT(OperateEntity operateEntity){
        operateEntity =   buildBaseInfo(operateEntity);

        operateEntity.setOperateCmd("createNFT");

        OprateMeta oprateMeta = new OprateMeta();

        NFTMeta nftMeta = new NFTMeta();
        nftMeta.setTitle("TaYi");
        nftMeta.setDescription("美好的生活从现在开始!");
        nftMeta.setImage("https://upload.wikimedia.org/wikipedia/zh/4/4a/Xinjiang_University_logo.png");

//        nftMeta.setAttributes(Arrays.asList("hi","xju","xymtop"));

        oprateMeta.setPayload(JSONUtil.toJsonStr(nftMeta));


        operateEntity.setOprateMeta(oprateMeta);


        return operateEntity;
    }
}
