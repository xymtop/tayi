package com.xymtop.tayi.core.ai.proof;

import com.xymtop.tayi.core.ai.aioperate.runner.entity.RunParam;
import com.xymtop.tayi.core.ai.config.runconfig.RunConfig;
import com.xymtop.tayi.core.utils.encrypt.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/9 0:33
 */

@Component
public class Prover {

    @Autowired
    HashUtils hashUtils;

    @Autowired
    HeavyComputation heavyComputation;
    public ProverResult executeAndProve(RunConfig runConfig, RunParam runParam) throws IOException {
        Object result = heavyComputation.exec(runConfig, runParam);

        String proof = hashUtils.hashHex(result.toString());

        return new ProverResult(result, proof);
    }

    public static class ProverResult {
        public final Object result;
        public final Object proof;

        public ProverResult(Object result, Object proof) {
            this.result = result;
            this.proof = proof;
        }
    }
}
