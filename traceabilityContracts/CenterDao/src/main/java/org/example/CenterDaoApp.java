package org.example;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xymtop.tayi.core.graph.entity.NftLabel;
import com.xymtop.tayi.core.nft.NFTData;
import com.xymtop.tayi.core.vm.code.that.NFTUtils;
import com.xymtop.tayi.core.vm.contract.ContractInfo;
import com.xymtop.tayi.core.vm.contract.inter.TaYiJavaContract;
import org.example.entity.Proposal;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class CenterDaoApp extends TaYiJavaContract implements Serializable
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    @Override
    public ContractInfo info() {
        ContractInfo info = new ContractInfo();
        info.setName("官方DAO");
        return info;
    }


    public List<Object> getAllProposals(){
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftData = new NFTData();
        nftData.setResource("CenterDaoApp");
        List<NFTData> dataList = nftUtils.search(nftData);
        List<Object> proposals = new ArrayList<>();
        for (NFTData data : dataList){
            proposals.add(nftUtils.getFromNft(data));
        }
        return  proposals;
    }

    public  int getProposalCount(){
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftData = new NFTData();
        nftData.setResource("CenterDaoApp");
        List<NFTData> dataList = nftUtils.search(nftData);
        return dataList.size();
    }

    public Object getProposal(JSONObject jsonObject){
        Proposal proposal = JSONUtil.toBean(jsonObject, Proposal.class);
        NFTUtils nftUtils = that.getNftUtils();
        NFTData nftData = nftUtils.getNftDataById(proposal.getId());
        if (nftData != null){
            Object fromNft =  nftUtils.getFromNft(nftData);
            return  fromNft;
        }
        return null;
    }


    public Object createProposal(JSONObject jsonObject){
        NFTUtils nftUtils = that.getNftUtils();
        Proposal proposal = JSONUtil.toBean(jsonObject, Proposal.class);
        NFTData nftData = nftUtils.writeToNft(proposal);
        nftData.setNftLabel(NftLabel.DATA);
        nftData.setAddress(proposal.getId());
        nftData.setResource("CenterDaoApp");
        Object mining = nftUtils.mining(nftData);
        Object fromNft = nftUtils.getFromNft(nftData);
        return  fromNft;
    }

    public boolean vote(JSONObject jsonObject){
        NFTUtils nftUtils = that.getNftUtils();
        Proposal proposal = JSONUtil.toBean(jsonObject, Proposal.class);

        NFTData nftDataById = nftUtils.getNftDataById(proposal.getId());
        if (nftDataById != null){
            Object fromNft = nftUtils.getFromNft(nftDataById);

            proposal = BeanUtil.mapToBean((HashMap)fromNft, Proposal.class, true);

            if (proposal != null){
                boolean vote =(boolean) jsonObject.get("vote");

                if (vote){
                    proposal.setPositiveVotes(proposal.getPositiveVotes() + 1);
                }else {
                    proposal.setNegativeVotes(proposal.getNegativeVotes() + 1);
                }
            }
            NFTData nftData = nftUtils.writeToNft(proposal);
            nftData.setNftLabel(NftLabel.DATA);

            return nftUtils.updateNFT(proposal.getId(),nftData);
        }
        return false;
    }

    public  boolean executeProposal(JSONObject jsonObject){
        NFTUtils nftUtils = that.getNftUtils();
        Proposal proposal = JSONUtil.toBean(jsonObject, Proposal.class);

        if (!proposal.isExecuted()){
            proposal.setExecuted(true);
        }
        NFTData nftData = nftUtils.writeToNft(proposal);
        nftData.setNftLabel(NftLabel.DATA);

        return nftUtils.updateNFT(proposal.getId(),nftData);
    }

    public List<Object> getProposalVotes(JSONObject jsonObject){

        return null;
    }

}
