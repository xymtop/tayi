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



	private List<SystemUser> userList = new ArrayList<>();

	@Data
	class Cat {
		String name;
	}

	@Override
	public ContractInfo info() {
		ContractInfo info = new ContractInfo();
		info.setName("TraceabilityApplication");
		info.setVersion("1.0");
		return info;
	}


	//获取用户
	public List<SystemUser> getUserList(){
		for (int i = 0; i < 100; i++){
			SystemUser user = new SystemUser();
			user.setAddress(String.valueOf(i));
			userList.add(user);
		}
		return userList;
	}


	public  int getAdd(int x){
		return userList.size()+x;
	}

	public SystemUser addUser(JSONObject user){
		SystemUser systemUser = JSONUtil.toBean(user, SystemUser.class);
		userList.add(systemUser);
		return systemUser;
	}



}
