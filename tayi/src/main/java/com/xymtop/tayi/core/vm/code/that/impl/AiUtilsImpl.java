package com.xymtop.tayi.core.vm.code.that.impl;

import com.xymtop.tayi.core.ai.aioperate.TaYiAiVM;
import com.xymtop.tayi.core.vm.code.that.AiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/8 23:31
 */

@Component
public class AiUtilsImpl implements AiUtils {

    @Autowired
    TaYiAiVM taYiAiVM;

    @Override
    public Object deployAI(String aiHash) throws IOException {
        return taYiAiVM.deployAI(aiHash);
    }

    @Override
    public Object trainAI(String aiHash, String trainDataAddress) {
        return taYiAiVM.trainAI(aiHash, trainDataAddress);
    }

    @Override
    public Object callAI(String aiHash, String aiInput) throws IOException {
        return taYiAiVM.callAI(aiHash, aiInput);
    }
}
