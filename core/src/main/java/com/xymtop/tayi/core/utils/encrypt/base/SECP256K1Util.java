package com.xymtop.tayi.core.utils.encrypt.base;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 1:49
 */
//椭圆曲线数字签名算法（ECDSA）：用于生成公钥和私钥，以太坊特别使用 secp256k1 椭圆曲线。


public class SECP256K1Util {

    static {
        // 添加 Bouncy Castle 作为安全提供者
        Security.addProvider(new BouncyCastleProvider());
    }

    // 生成基于 secp256k1 的 ECDSA 密钥对
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        keyGen.initialize(ecSpec, new SecureRandom());
        return keyGen.generateKeyPair();
    }

    // 使用私钥对数据进行签名
    public static byte[] sign(PrivateKey privateKey, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    // 使用公钥验证签名
    public static boolean verify(PublicKey publicKey, byte[] data, byte[] sigBytes) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sigBytes);
    }

    public static void main(String[] args) {
        try {
            // 生成密钥对
            KeyPair keyPair = SECP256K1Util.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // 创建测试消息
            String message = "This is a test message";

            // 对消息进行签名
            byte[] signature = SECP256K1Util.sign(privateKey, message.getBytes());

            // 验证签名
            boolean isVerified = SECP256K1Util.verify(publicKey, message.getBytes(), signature);

            System.out.println("签名验证结果: " + isVerified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




