package com.xymtop.tayi.core.inter;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface Builder <T,M>{

    //构建一些实体的内容
    M build(T source) throws Exception;

}
