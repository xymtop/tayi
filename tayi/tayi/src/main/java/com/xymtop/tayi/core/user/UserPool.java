package com.xymtop.tayi.core.user;

import com.xymtop.tayi.core.utils.encrypt.ECDSAUtil;
import com.xymtop.tayi.core.utils.jsonutils.XJsonUtils;
import com.xymtop.tayi.store.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 6:06
 */

//用户池，维护一些基本的用户情况

@Component
public class UserPool {

    @Autowired
     private ECDSAUtil ecdsaUtil;

    @Autowired
    private DBUtils dbUtils;


    @Autowired
    private XJsonUtils xJsonUtils;


    //生成一个用户
    public SystemUser generateUser() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        KeyPair keyPair = ecdsaUtil.generateKeyPair();
        String publicKeyString = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        SystemUser user = new SystemUser();
        user.setAddress(publicKeyString);
        user.setPrivateKey(privateKeyString);
        user.setBalance(0);
        user.setNonce(0);
        return user;
    }

    //验证用户秘钥
    public boolean verifyUser(SystemUser user) {
        return true;
    }

    //返回用户余额
    public long getBalance(String userAddress) {
        return 0;
    }

    //返回用户交易次数
    public long getNonce(String userAddress) {
        return 0;
    }


    //验证用户签名
    public boolean verifySign(String userAddress, String sign) {
        return true;
    }

    //修改用户余额
    public void setBalance(String userAddress, long balance) {

    }

    //修改用户交易次数
    public void setNonce(String userAddress, long nonce) {

    }


    //从数据库获取用户
    public SystemUser getUserFromDB(String address) throws Exception {
        return xJsonUtils.jsonToObj(dbUtils.get(address), SystemUser.class);
    }

//    更新用户数据到数据库
    public void updateUserToDB(SystemUser user) throws Exception {
        dbUtils.put(user.getAddress(), xJsonUtils.objToJson(user));
    }


}
