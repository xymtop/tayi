import {abi} from "./json/TraceabilityNFT.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";
import {getNFTMetadataFromJson, getNFTMetadataFromURL} from "../../NFTUtils";
import {getTaYi} from "../../tayi/TaYiUtils";



let  trNFT = "a6e79b6648968a496e75f9949d4f0f0c33e5d7fd999f804ccd4f06976fd9ea4d";

let tayi = getTaYi()
// console.log(await tayi.deploy("Qmcgx8bz2yujVQksvafgxiCmFfg9FJXrFd2NAtyJhhmbLN"))



// mint(address to, string memory tokenURI)
async function mintNFT(to:any,tokenURI:any){
    return tayi.call(trNFT,"mintNFT",{
        id:Date.now(),
        to,
        tokenURI
    })
}

// getAddress(uint id)
async function getAddress(id:any){
    return tayi.call(trNFT,"getAddress",{
       id
    })
}

// tokensOfOwner(address account)
async function tokensOfOwner(account:any){
    return tayi.call(trNFT,"tokensOfOwner",{
        account
    })
}

//tokenURI(uint256 tokenId)
async function tokenURI(tokenId:any){
    return tayi.call(trNFT,"tokenURI",{
        tokenId
    })
}

//根据id获取NFT的元数据
export async function getNFTMetadata(id:any){
    let token = await tokenURI(id);
    let metaData =  await getNFTMetadataFromURL(token);
     metaData.id = id;
    return metaData;
}

//获取所有token
async function getAllTokens(){
    let data = await tayi.call(trNFT,"getAllTokens")
    for (let index = 0; index < data.length; index++){
        let metaData =  await getNFTMetadataFromURL(data[index].tokenURI);
        data[index] = metaData;
    }
    return data;
}

//获取合约的地址
async function getContractAddress(){
    return tayi.call(trNFT,"getContractAddress")
}

export { mintNFT,getAddress,tokensOfOwner,tokenURI,getAllTokens}