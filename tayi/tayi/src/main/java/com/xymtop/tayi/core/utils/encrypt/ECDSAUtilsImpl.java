package com.xymtop.tayi.core.utils.encrypt;

import com.xymtop.tayi.core.utils.encrypt.base.SECP256K1Util;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 2:05
 */
@Component
public class ECDSAUtilsImpl implements ECDSAUtil{
    @Override
    public KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        return SECP256K1Util.generateKeyPair();
    }

    @Override
    public byte[] sign(PrivateKey privateKey, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return SECP256K1Util.sign(privateKey, data);
    }

    @Override
    public boolean verify(PublicKey publicKey, byte[] data, byte[] sigBytes) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return SECP256K1Util.verify(publicKey, data, sigBytes);
    }

    public  PublicKey convertStringToPublicKey(String publicKeyStr) {
        try {
            byte[] publicBytes = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Error converting string to public key", e);
        }
    }
}
