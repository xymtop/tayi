package com.xymtop.tayi.core.block;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.block.entity.BlockHead;
import com.xymtop.tayi.store.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 3:15
 */
@Component
public class BlockUtils {

    @Autowired
    private BlockChainHeightUtils   blockChainHeightUtils;

    @Autowired
    private DBUtils dbUtils;


    //获取构建区块哈希的属性
    public  String getBlockHashAttr(Block block) {
        //获取区块头
        BlockHead blockHead = block.getBlockHead();
        //时间戳
        String timestamp = blockHead.getTimeStamp();
        //上一个区块的哈希
        String preBlockHash = blockHead.getPreviousHash();
        //随机数
        String nonce = blockHead.getNonce();
//        交易列表的根
        String merkleRoot = blockHead.getMerkleRootHash();


        Map<String, String> map = MapUtil.newHashMap();
        map.put("timestamp", timestamp);
        map.put("preBlockHash", preBlockHash);
        map.put("nonce", nonce);
        map.put("merkleRoot", merkleRoot);

        return sortMap(map);
    }

    //通过map的参数排序
    public  String sortMap(Map<String, String> map) {
        //排序
        Map<String, String> sortMap = MapUtil.sort(map);
        //拼接
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : sortMap.entrySet()) {
            sb.append(entry.getValue());
        }
        return sb.toString();
    }





    //将区块数据写入到kv数据库
    public  void writeBlockToDB(Block block) throws Exception {
        long blockHeight = blockChainHeightUtils.getBlockHeight();
        //序列化区块信息
        String blockStr = JSONUtil.toJsonStr(block);
        //写入数据库

        long blockNumber = blockHeight + 1;

        dbUtils.put(String.valueOf(blockNumber), blockStr);

    }

    //通过id获取区块
    public  Block getBlock(long id) throws Exception {
        //获取区块信息
        String blockStr = dbUtils.get(String.valueOf(id));
        //反序列化
        return JSONUtil.toBean(blockStr, Block.class);
    }

    //获取最新的区块
    public Block getLatestBlock() throws Exception {
        long blockHeight = blockChainHeightUtils.getBlockHeight();
        return getBlock(blockHeight);
    }

}
