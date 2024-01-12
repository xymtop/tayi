package com.xymtop.traceability.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.HashUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.graph.entity.NftLabel;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.vm.code.that.NFTUtils;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import com.xymtop.traceability.user.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//为了实现自己的合约内容并且调用，你只需要7步
/*
 * 1. 继承TaYiJavaContract
 * 2.实现Serializable接口,方便序列化
 * 3.实现info方法，注册合约信息
 * 4.编写自己的方法
 * 5.打包为jar包并且上传到IPFS
 * 6.在TaYi中部署合约
 * 7.调用智能合约方法
 * */
public class UserContract extends TaYiJavaContract implements Serializable {


    public static void main( String[] args )
    {

    }

    @Override
    public ContractInfo info() {
        ContractInfo info = new ContractInfo();
        info.setName("用户合约");
        return info;
    }


    public  Object getUserInfo(JSONObject jsonObject){
        User user = JSONUtil.toBean(jsonObject, User.class);
        NFTUtils nftUtils = that.getNftUtils();

        NFTData nftDataUser = nftUtils.getNftDataById(user.getAccount());

        if (nftDataUser != null){
            Object fromNft =  nftUtils.getFromNft(nftDataUser);

            return  fromNft;
        }

        return  null;
    }

    public Object registerUser(JSONObject jsonObject){
        NFTUtils nftUtils = that.getNftUtils();
        User user = JSONUtil.toBean(jsonObject, User.class);
        NFTData nftData = nftUtils.writeToNft(user);
        nftData.setNftLabel(NftLabel.USER);
        nftData.setAddress(user.getAccount());
        nftData.setResource("userManager");
        Object mining = nftUtils.mining(nftData);
        Object fromNft = nftUtils.getFromNft(nftData);
        return  fromNft;
    }

    public boolean updateUser(JSONObject jsonObject){
        User user = JSONUtil.toBean(jsonObject, User.class);
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftData = nftUtils.writeToNft(user);
        return nftUtils.updateNFT(user.getAccount(),nftData);
    }


    public boolean banUser(JSONObject jsonObject){
        User user = JSONUtil.toBean(jsonObject, User.class);
        NFTUtils nftUtils = that.getNftUtils();
        user.setBanned(true);
        NFTData nftData = nftUtils.writeToNft(user);
        return  nftUtils.updateNFT(user.getAccount(),nftData);
    }

    public List<Object> getAllUsers(){
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftData = new NFTData();
        nftData.setResource("userManager");
        List<NFTData> dataList = nftUtils.search(nftData);
        List<Object> users = new ArrayList<>();
        for (NFTData data : dataList){
            users.add(nftUtils.getFromNft(data));
        }
        return users;
    }

}
