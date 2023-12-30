package com.xymtop.tayi.core.utils.encrypt;

import com.xymtop.tayi.core.utils.encrypt.base.Keccak256Utils;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 2:03
 */
//实现一个加密算法类，如果需要替换其他算法可以直接替换
@Component
public class HashUtilsImpl implements HashUtils{


    @Override
    public byte[] hash(byte[] input) {
        return Keccak256Utils.keccak256(input);
    }

    @Override
    public String hashHex(String input) {
            return Keccak256Utils.keccak256Hex(input);
    }
}
