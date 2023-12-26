package com.xymtop.tayi.core.nft.builder;

import com.xymtop.tayi.core.inter.Builder;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.nft.NFTMeta;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 5:14
 */
@Component
public class NFTBuilder implements Builder<NFTMeta, NFTData> {

    @Override
    public NFTData build(NFTMeta source) {

        return null;
    }
}
