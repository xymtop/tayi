package com.xymtop.tayi.core.block.builder;

import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.browser.entity.Result;
import com.xymtop.tayi.core.block.BlockHashUtils;
import com.xymtop.tayi.core.block.BlockUtils;
import com.xymtop.tayi.core.block.entity.Block;
import com.xymtop.tayi.core.block.entity.BlockConfig;
import com.xymtop.tayi.core.block.entity.BlockHead;
import com.xymtop.tayi.core.cmd.ExecResult;
import com.xymtop.tayi.core.inter.Builder;
import com.xymtop.tayi.core.merkle.MerkleBuilder;
import com.xymtop.tayi.core.oprate.OperateEntity;
import com.xymtop.tayi.core.oprate.execute.OperateExecuter;
import com.xymtop.tayi.core.oprate.poll.OpratePool;
import com.xymtop.tayi.core.pool.PoolItem;
import com.xymtop.tayi.core.vm.code.that.That;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 5:10
 */

//构建区块链
@Component
public class BlockBuilder implements Builder<List<PoolItem>, Block> {

    @Autowired
    private BlockUtils blockUtils;

    @Autowired
    private BlockConfig blockConfig;


    @Autowired
    private MerkleBuilder merkleBuilder;

    @Autowired
    private BlockHashUtils blockHashUtils;
    @Autowired
    private OperateExecuter operateExecuter;


    @Autowired
    private OpratePool opratePool;

    @Autowired
    That that;

    @Override
    public Block build(List<PoolItem> source) throws Exception {

        //获取上一区块信息
        Block latestBlock = blockUtils.getLatestBlock();



        //获取上一个区块的哈希
        String latestBlockHash = latestBlock.getHash();


        if (latestBlockHash==null){
            return null;
        }

        //获取当前区块编号
        long lastNum = Long.parseLong(latestBlock.getBlockNumber());
        String blockNumber = String.valueOf(lastNum+1);

        //操作列表
        List<OperateEntity> operateEntityList = new ArrayList<>();



        for (PoolItem poolItem : source) {
            operateEntityList.add(poolItem.getOperateEntity()) ;
        }


        //执行操作
        for (OperateEntity operateEntity : operateEntityList){
            try {

                //设置当前的环境的发送者

                that.setSender(operateEntity.getSender());

                operateExecuter.execute(operateEntity);
            }catch (Exception e){
                //操作执行错误,发送错误信息
                WebSocketSession session = opratePool.get(operateEntity.getOperateHash());
                if (session!=null){
                    ExecResult execResult = new ExecResult();
                    execResult.setResult(e);
                    execResult.setResultFlag(false);
                    execResult.setCmd(operateEntity.getOperateCmd());
                    operateEntity.setExecResult(execResult);
                    session.sendMessage(new TextMessage(JSONUtil.toJsonStr(Result.okData(operateEntity))));
                }

                throw  e;

            }

        }


        //区块大小
        String blockSize = String.valueOf(operateEntityList.size());

//        额外信息
        String extraData = "tayi";

        //区块头
        BlockHead blockHead = new BlockHead();
        blockHead.setVersion(blockConfig.getVersion());
        blockHead.setPreviousHash(latestBlockHash);
//获取默克尔树根节点
        String merkleRoot = merkleBuilder.getMerkleRoot(operateEntityList);

        //时间戳
        String timeStamp = String.valueOf(System.currentTimeMillis());

        //难度目标
        String target = blockConfig.getTarget();

        //随机数
        String nonce = "0";

        blockHead.setMerkleRootHash(merkleRoot);
        blockHead.setTimeStamp(timeStamp);
        blockHead.setTarget(target);
        blockHead.setNonce(nonce);


        //构建完整区块
        Block newBlock = new Block();
        newBlock.setBlockNumber(blockNumber);
        newBlock.setBlockHead(blockHead);
        newBlock.setOperateList(operateEntityList);
        newBlock.setBlockSize(blockSize);
        newBlock.setExtraData(extraData);

        //计算当前区块的哈希
        String hash = blockHashUtils.getBlockHash(newBlock);
        newBlock.setHash(hash);

        return newBlock;
    }

}
