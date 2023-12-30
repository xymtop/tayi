package com.xymtop.tayi.core.nft;

import lombok.Data;

import java.util.List;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 1:27
 */

//NFT元数据
//    {
//            "title":"My NFT Art",
//            "description":"This piece of art represents...",
//            "image":"https://example.com/path/to/nft/image.png",
//            "attributes":[
//            {
//            "trait_type":"Artist",
//            "value":"Jane Doe"
//            },
//            {
//            "trait_type":"Year",
//            "value":"2021"
//            },
//            {
//            "trait_type":"Material",
//            "value":"Digital"
//            }
//            ]
//            }

@Data
public class NFTMeta {

        //标题
        private String title;
//        描述
        private String description;
//        图片
        private String image;
//        属性
        private List<String> attributes;

}
