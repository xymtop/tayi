package com.xymtop.tayi.core.vm.code.that;

import com.xymtop.tayi.core.block.BlockChainUtils;
import com.xymtop.tayi.core.cmd.CmdSystem;
import com.xymtop.tayi.core.cmd.apis.*;
import com.xymtop.tayi.core.oprate.execute.OperateEntityUtils;
import com.xymtop.tayi.core.user.SystemUser;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 14:36
 */

@Component
@Data
@Lazy
public class That {

    String sender;

   @Autowired
    AiUtils aiUtils;
   @Autowired
    BlockUtils blockUtils;

   @Autowired
    NFTUtils nftUtils;

   @Autowired
    UserUtils userUtils;

}
