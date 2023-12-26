package com.xymtop.tayi.browser.controller;

import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.browser.entity.Result;
import com.xymtop.tayi.browser.service.BlockService;
import com.xymtop.tayi.core.oprate.execute.OperateEntityUtils;
import com.xymtop.tayi.core.oprate.execute.OperateEntrance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 4:22
 */

@RestController
@RequestMapping("/block")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @Autowired
    private OperateEntrance operateEntrance;

    @Autowired
    private OperateEntityUtils operateEntityUtils;

    //获取创世区块
    @RequestMapping("/getGenesisBlock")
    public Result getGenesisBlock() {
        return Result.ok(blockService.getGenesisBlock());
    }

    //获取某个id的区块
    @RequestMapping("/getBlock/{id}")
    public Result getBlock(@PathVariable("id") long id) throws Exception {
        return Result.ok(blockService.getBlock(id));
    }

    //执行交易
    @RequestMapping("/executeTransaction")
    public  Result executeTransaction(@RequestBody String msg) throws Exception {
        return Result.ok(operateEntrance.execute(JSONUtil.parseObj(msg).getStr("msg")));
    }

    //获取交易结果
    @RequestMapping("/getTransactionResult/{id}")
    public Result getTransactionResult(@PathVariable("id") String id) throws Exception {
        return Result.ok(operateEntityUtils.getResult(id));
    }

    //获取区块高度
    @RequestMapping("/getBlockHeight")
    public Result getBlockHeight() throws Exception {
        return Result.ok(blockService.getBlockHeight());
    }
}
