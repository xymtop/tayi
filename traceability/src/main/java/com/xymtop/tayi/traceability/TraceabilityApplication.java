package com.xymtop.tayi.traceability;


import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;

import java.io.Serializable;

public class TraceabilityApplication extends TaYiJavaContract implements Serializable {
	@Override
	public ContractInfo info() {
		ContractInfo info = new ContractInfo();
		info.setName("TraceabilityApplication");
		info.setVersion("1.0");
		return info;
	}

}
