package com.xymtop.tayi.core.cmd.apis;

import com.xymtop.tayi.core.cmd.apis.ann.CmdApi;
import com.xymtop.tayi.core.cmd.apis.ann.CmdApiFun;
import com.xymtop.tayi.core.cmd.cmdbuilder.ArgsBuilder;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.user.SystemUser;
import com.xymtop.tayi.core.user.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

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


    //新增用户
    @CmdApiFun(cmd = "addUser")
    public SystemUser addUser() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        return userPool.generateUser();
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
