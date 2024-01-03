package com.xymtop.tayi.core.cmd.apis;

import com.xymtop.tayi.core.cmd.apis.ann.CmdApi;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApiFun;
import com.xymtop.tayi.core.cmd.cmdbuilder.ArgsBuilder;
import com.xymtop.tayi.core.graph.NFTUtils;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.nft.NFTMeta;
import com.xymtop.tayi.core.nft.builder.NFTBuilder;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.user.SystemUser;
import com.xymtop.tayi.core.user.UserPool;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 21:04
 */

@Component
@CmdApi
public class UserApi {

    @Autowired
    private UserPool userPool;


    @Autowired
    private NFTBuilder nftBuilder;

    @Autowired
    private NFTUtils nftUtils;


    //新增用户
    @CmdApiFun(cmd = "addUser")
    public SystemUser addUser() throws Exception {
        SystemUser systemUser = userPool.generateUser();

        //构建NFT
        NFTData nftData = nftBuilder.getBaseNFT();
        NFTMeta nftMeta = new NFTMeta();
        nftData.setOwner(systemUser.getAddress());
        nftData.setAddress(systemUser.getAddress());
        nftMeta.setTitle(systemUser.getAddress());
        nftMeta.setDescription("美好的生活从现在开始!");
        nftMeta.setImage("https://upload.wikimedia.org/wikipedia/zh/4/4a/Xinjiang_University_logo.png");
        nftMeta.setAttributes(Arrays.asList("user"));
        nftData.setMeta(nftMeta);

        //写入数据库
        String nodeId = nftUtils.createNFT(nftData);

        if (nodeId == null){
            throw new Exception("创建失败");
        }

        return  systemUser;
    }


    //获取用户余额
    @CmdApiFun(cmd = "getBalance",needParam = true)
    public long getBalance(String address) {
        return userPool.getBalance(address);
    }


    @ArgsBuilder("getBalance")
    public Object[] getBalanceArgsBuilder(OperateEntity operate) {
        return new String[]{operate.getSender()};
    }

    //获取用户交易次数
    @CmdApiFun(cmd = "getNonce")
    public  long getNonce(String address) {
        return userPool.getNonce(address);
    }

    @ArgsBuilder("getNonce")
    public Object[] getNonceArgsBuilder(OperateEntity operate) {
        return new String[]{operate.getSender()};
    }
}
