package com.xymtop.tayi.traceability;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.cmd.apis.AiApi;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.user.SystemUser;
import com.xymtop.tayi.core.vm.code.that.AiUtils;
import com.xymtop.tayi.core.vm.code.that.NFTUtils;
import com.xymtop.tayi.core.vm.code.that.That;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import lombok.Data;
import org.neo4j.graphdb.Node;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TraceabilityApplication extends TaYiJavaContract implements Serializable {


	@Override
	public ContractInfo info() {
		ContractInfo info = new ContractInfo();
		info.setName("TraceabilityApplication");
		info.setVersion("1.0");
		return info;
	}

	public  Object getAllNft(JSONObject jsonObject) throws IOException {
		String name = (String) jsonObject.get("name");
		AiUtils aiUtils = that.getAiUtils();
		Object object = aiUtils.callAI("QmT3xwZos5QzLTutv4k1b61hcVidD9KcrWzdf36DHW56Tp", name);
		return  object;
	}

	public  Object creatNft(JSONObject jsonObject){
		NFTData nftData = JSONUtil.toBean(jsonObject, NFTData.class);
		NFTUtils nftUtils = that.getNftUtils();
		Object mining = nftUtils.mining(nftData);
		return mining;
	}


}
