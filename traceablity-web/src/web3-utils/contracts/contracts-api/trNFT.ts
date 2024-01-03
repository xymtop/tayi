import {abi} from "./json/TraceabilityNFT.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";
import {getNFTMetadataFromJson, getNFTMetadataFromURL} from "../../NFTUtils";



const trNFT = await getContract(abi,"0x4ed7c70F96B99c776995fB64377f0d4aB3B0e1C1");

// mint(address to, string memory tokenURI)
async function mintNFT(to:any,tokenURI:any){
    return trNFT.methods.mint(to,tokenURI).send({from:await getWalletAccount()})
}

// getAddress(uint id)
async function getAddress(id:any){
    return trNFT.methods.getAddress(id).call();
}

// tokensOfOwner(address account)
async function tokensOfOwner(account:any){
    return trNFT.methods.tokensOfOwner(account).call();
}

//tokenURI(uint256 tokenId)
async function tokenURI(tokenId:any){
    return trNFT.methods.tokenURI(tokenId).call();
}

//根据id获取NFT的元数据
export async function getNFTMetadata(id:any){
    let token = await tokenURI(id);
    let metaData =  await getNFTMetadataFromURL(token);
    metaData.address =await getContractAddress();
    return metaData;
}

//获取所有token
async function getAllTokens(){
    let tokens = await tokensOfOwner(await getWalletAccount());
    let tokenList = [];
    for(let i=0;i<tokens.length;i++){
        let token = await tokenURI(tokens[i]);
        let  nft =await getNFTMetadataFromURL(token);
        //设置id
        nft.id = tokens[i];

        //设置地址
        nft.address = await  getAddress(tokens[i])
        tokenList.push(nft);
    }
    return tokenList;
}

//获取合约的地址
async function getContractAddress(){
    return trNFT.options.address;
}

export { mintNFT,getAddress,tokensOfOwner,tokenURI,getAllTokens}