import {mockTraceAblilityInfos} from "./mock";
import {traceAblilityInfoToGNode} from "./graphUtils";
import {getAddress, getNFTMetadata, tokenURI} from "./contracts/contracts-api/trNFT";
import {getAllNFTsInCenter, NFTMetadataToTraceAblilityInfo} from "./NFTUtils";
import {TraceAblilityInfo, TraceNodeNft} from "./infoTypes";
import {checkWallet} from "./wallet-utils";

//获取当前项目数据
export const getProjectData = async (id:string) => {
    //传递过来的id为溯源项目NFT的id
    //可以先获取溯源项目中的数据
    let proNftData = await getNFTMetadata(id);
    let proNft = await NFTMetadataToTraceAblilityInfo(id,proNftData);

    //通过项目的id获取到项目的地址
     let address = await getAddress(id);

     let datas =await getAllNFTsInCenter(address)

    // let mockData = await mockTraceAblilityInfos();
    const data = await traceAblilityInfoToGNode({
        productNft: proNft,
        traceNodesNft: datas,
    })
    // console.log(data)
    return data;
};

