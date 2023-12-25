package com.xymtop.tayi.core.utils.encrypt;

import java.security.*;

public interface ECDSAUtil {
    // 生成基于 secp256k1 的 ECDSA 密钥对
    KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException;

    // 使用私钥对数据进行签名
    byte[] sign(PrivateKey privateKey, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;

    // 使用公钥验证签名
    boolean verify(PublicKey publicKey, byte[] data, byte[] sigBytes) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;
}
