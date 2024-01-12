package org.example;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.graph.entity.NftLabel;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.vm.code.that.NFTUtils;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import org.example.entity.TokenEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class TrNftApp extends TaYiJavaContract implements Serializable
{
    private final String APP_NAME =  "TrNftApp";
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    @Override
    public ContractInfo info() {
        ContractInfo info = new ContractInfo();
        info.setName(APP_NAME);
        return info;
    }


     public Object mintNFT(JSONObject jsonObject){
         TokenEntity token = JSONUtil.toBean(jsonObject, TokenEntity.class);
         NFTUtils nftUtils = that.getNftUtils();
         NFTData nftData = nftUtils.writeToNft(token);
         nftData.setResource(APP_NAME);
         nftData.setNftLabel(NftLabel.DATA);
         Object mining = nftUtils.mining(nftData);
         if (mining == null){
             return  null;
         }
         return  token;
     }


    public Object getAddress(JSONObject jsonObject){
        TokenEntity token = JSONUtil.toBean(jsonObject, TokenEntity.class);
        return  token.getId();
    }
    public Object tokensOfOwner(JSONObject jsonObject){
        TokenEntity token = JSONUtil.toBean(jsonObject, TokenEntity.class);
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftData = nftUtils.getNftDataById(token.getId());
        Object fromNft = nftUtils.getFromNft(nftData);
        token =  BeanUtil.mapToBean((HashMap) fromNft,TokenEntity.class,true);
        return  token.getOwner();
    }
    public Object tokenURI(JSONObject jsonObject){
        TokenEntity token = JSONUtil.toBean(jsonObject, TokenEntity.class);
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftData = nftUtils.getNftDataById(token.getId());
        Object fromNft = nftUtils.getFromNft(nftData);
        token =  BeanUtil.mapToBean((HashMap) fromNft,TokenEntity.class,true);
        return  token.getTokenURI();
    }
    public List<Object> getAllTokens(){
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftData = new NFTData();
        nftData.setResource(APP_NAME);
        List<NFTData> dataList = nftUtils.search(nftData);
        List<Object> trs = new ArrayList<>();
        for (NFTData data : dataList){
            trs.add(nftUtils.getFromNft(data));
        }
       return  trs;
    }
}
