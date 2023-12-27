package com.xymtop.tayi.core.vm.virtual;

import cn.hutool.core.io.FileUtil;
import com.xymtop.tayi.core.store.DBUtils;
import com.xymtop.tayi.core.utils.jsonutils.XJsonUtils;
import com.xymtop.tayi.core.vm.contract.Contract;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import com.xymtop.tayi.core.vm.ipfs.IPFSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 20:13
 */

@Component
public class ContractStore {

    @Autowired
    private DBUtils dbUtils;

    @Autowired
    XJsonUtils xJsonUtils;


    @Autowired
    private IPFSUtils ipfsUtils;
    //写入合约数据



}
