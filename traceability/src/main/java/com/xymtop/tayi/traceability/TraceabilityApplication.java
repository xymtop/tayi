package com.xymtop.tayi.traceability;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.user.SystemUser;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TraceabilityApplication extends TaYiJavaContract implements Serializable {

	@Override
	public ContractInfo info() {
		ContractInfo info = new ContractInfo();
		info.setName("TraceabilityApplication");
		info.setVersion("1.0");
		return info;
	}


}
