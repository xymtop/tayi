package com.xymtop.tayi.test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface TestApp {
    void test() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException;
}
