package com.xymtop.tayi.core.utils.encrypt.base;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 1:54
 */
//以太坊使用的是 Keccak-256，这是 SHA-3 算法家族的一部分，用于生成交易、区块和地址的哈希值。

public class Keccak256Utils {

    // 计算输入字节数组的 Keccak-256 哈希
    public static byte[] keccak256(byte[] input) {
        Keccak.Digest256 digest256 = new Keccak.Digest256();
        return digest256.digest(input);
    }

    // 计算输入字符串的 Keccak-256 哈希并返回十六进制字符串
    public static String keccak256Hex(String input) {
        byte[] hashbytes = keccak256(input.getBytes());
        return Hex.toHexString(hashbytes);
    }


    public static void main(String[] args) {
        String input = "Hello, world!";
        String hashHex = Keccak256Utils.keccak256Hex(input);
        System.out.println("Keccak-256 Hash in Hex: " + hashHex);
    }


}


