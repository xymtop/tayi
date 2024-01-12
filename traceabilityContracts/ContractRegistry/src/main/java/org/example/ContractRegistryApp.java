package org.example;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.graph.entity.NftLabel;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.vm.code.that.NFTUtils;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import org.example.entity.MyContractInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Hello world!
 *
 */
public class ContractRegistryApp  extends TaYiJavaContract implements Serializable
{

    private final String APP_NAME =  "ContractRegistryApp";

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    @Override
    public ContractInfo info() {
        ContractInfo info = new ContractInfo();
        info.setName("证明注册中心");
        return info;
    }

    public Object registerContract(JSONObject jsonObject){
        MyContractInfo myContractInfo = JSONUtil.toBean(jsonObject, MyContractInfo.class);
        myContractInfo.setOwner(that.getSender());
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftData = nftUtils.writeToNft(myContractInfo);

        nftData.setNftLabel(NftLabel.DATA);
        nftData.setAddress(myContractInfo.getContractAddress());
        nftData.setResource(APP_NAME);
        Object mining = nftUtils.mining(nftData);
        Object fromNft = nftUtils.getFromNft(nftData);
        return  fromNft;
    }

    public boolean updateStatus(JSONObject jsonObject){
        MyContractInfo myContractInfo = JSONUtil.toBean(jsonObject, MyContractInfo.class);
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftDataById = nftUtils.getNftDataById(myContractInfo.getContractAddress());
        Object fromNft = nftUtils.getFromNft(nftDataById);
        myContractInfo =  BeanUtil.mapToBean((HashMap) fromNft, MyContractInfo.class,true);

        if (myContractInfo != null){
            myContractInfo.setStatus(jsonObject.get("status").toString());

            Object mining = nftUtils.mining(nftUtils.writeToNft(myContractInfo));

            if (mining != null){
                return  true;
            }
        }

        return false;
    }

    public List<Object> getAllContracts(){
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftData = new NFTData();
        nftData.setResource(APP_NAME);
        List<NFTData> dataList = nftUtils.search(nftData);
        List<Object> contracts = new ArrayList<>();
        for (NFTData data : dataList){
            contracts.add(nftUtils.getFromNft(data));
        }
        return  contracts;
    }

    public List<Object> getApprovedContracts(){
        List<Object> allContracts = getAllContracts();
        List<Object> approvedContracts = new ArrayList<>();
        for(Object obj : allContracts){
            if ("1".equals(((MyContractInfo) obj).getStatus())){
               approvedContracts.add(obj);
            }
        }
        return  approvedContracts;
    }

    public boolean isContractApprovedAndNotOfflineStruct(JSONObject jsonObject){
        return true;
    }

    public boolean isContractApprovedAndNotOffline(JSONObject jsonObject){
        return true;
    }

    public boolean isContractRegistered(JSONObject jsonObject){
            return true;
    }

    public  Object getContractInfo(JSONObject jsonObject){
        MyContractInfo myContractInfo = JSONUtil.toBean(jsonObject, MyContractInfo.class);
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftDataById = nftUtils.getNftDataById(myContractInfo.getContractAddress());
        Object fromNft = nftUtils.getFromNft(nftDataById);
        if (fromNft != null){
            myContractInfo =  BeanUtil.mapToBean((HashMap) fromNft, MyContractInfo.class,true);
            return myContractInfo;
        }


        return null;

    }


    public boolean addTrac(JSONObject jsonObject){

        return false;
    }

    public List<Object> getAllContractByUser(JSONObject jsonObject){
        List<Object> contracts = new ArrayList<>();
        String user = jsonObject.getStr("owner");

        if (user == null){
            return null;
        }
        List<Object> allContracts = getAllContracts();
        for (Object obj : allContracts){
          MyContractInfo info =   (MyContractInfo) obj;
          if (user.equals(info.getOwner())){
              contracts.add(info);
          }
        }
        return contracts;
    }
}
