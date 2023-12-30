package com.xymtop.tayi.core.merkle;

import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.execute.OperateExecuter;
import com.xymtop.tayi.core.utils.encrypt.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 14:39
 */



import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class MerkleBuilder {

    @Autowired
    private HashUtils hashUtils;

    // 构建Merkle树并返回Merkle根
    public String getMerkleRoot(List<OperateEntity> operateEntityList) {
        if (operateEntityList == null || operateEntityList.isEmpty()) {
            return hashUtils.hashHex("tayi");
        }


        List<String> layer = new ArrayList<>();
        for (OperateEntity entity : operateEntityList) {
            layer.add(entity.getOperateHash());
        }

        // 构建Merkle树
        while (layer.size() > 1) {
            List<String> newLayer = new ArrayList<>();
            for (int i = 0; i < layer.size(); i += 2) {
                // 处理列表中最后一个元素的情况
                if (i + 1 == layer.size()) {
                    newLayer.add(layer.get(i)); // 直接添加到新层
                } else {
                    String combinedHash = combineHashes(layer.get(i), layer.get(i + 1));
                    newLayer.add(combinedHash);
                }
            }
            layer = newLayer;
        }

        return layer.get(0);
    }

    // 哈希两个字符串并合并它们
    private String combineHashes(String hash1, String hash2) {
        return hashUtils.hashHex(hash1 + "-" + hash2);
    }
}
