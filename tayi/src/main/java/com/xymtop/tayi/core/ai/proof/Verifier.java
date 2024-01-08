package com.xymtop.tayi.core.ai.proof;

import com.xymtop.tayi.core.utils.encrypt.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/9 0:34
 */

@Component
public class Verifier {

    @Autowired
    HashUtils hashUtils;
    public boolean verify(Prover.ProverResult proverResult) {

        return hashUtils.hashHex(proverResult.result.toString()).equals(proverResult.proof);
    }
}
