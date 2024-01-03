package com.xymtop.tayi.core.utils.encrypt;

public interface HashUtils {
    byte[] hash(byte[] input);

    String hashHex(String input);
}
