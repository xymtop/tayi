package com.xymtop.tayi.core.vm.code;

import com.xymtop.tayi.core.block.BlockChainUtils;
import com.xymtop.tayi.core.block.BlockUtils;
import com.xymtop.tayi.core.cmd.CmdSystem;
import com.xymtop.tayi.core.oprate.execute.OperateEntityUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 14:36
 */

@Component
@Data
public class That {
    @Autowired
    private CmdSystem cmdSystem;
}
