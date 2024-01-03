import {uploadBlogToIPFS} from "./ipfsFileUtils";
import {getJsonFromIPFSByLink} from "./jsonUtils";
import {NFTMetadata, TraceAblilityProduct, TraceNodeNft} from "./infoTypes";
import {getAddress} from "./contracts/contracts-api/trNFT";
import {getContract} from "./oprate-utils";
import {getWalletAccount} from "./wallet-utils";

import {abi} from "./contracts/contracts-api/json/ERC721NFT.json";
import {addTrac, getAllContractByUser} from "./contracts/contracts-api/ContractRegistry";
import {isContractAddress} from "./solidityUtils";


//通过一个字符串创建nft元数据格式的json字符串
export function createNFTMetadata(name: string, description: string, image: string, external_url: string, attributes: any[]) {
  const metadata: NFTMetadata = {
    name,
    description,
    image,
    external_url,
    attributes,
  };
  return JSON.stringify(metadata);
}



//通过nft元数据格式的json字符串创建一个blob
export function createBlobByNFTMetadata(metadata: string) {
  return new Blob([metadata], { type: 'application/json' });
}

//调用现存的函数生成一个blob并且上传到ipfs
export async function uploadNFTMetadataToIPFS(name: string, description: string, image: string, external_url: string, attributes: any[]) {
  const metadata = createNFTMetadata(name, description, image, external_url, attributes);
  const blob = createBlobByNFTMetadata(metadata);
  let  url = await uploadBlogToIPFS(blob);
  url = getNFTMetadataURL(url);
  return url;
}


//设置一个静态的url，拼接ipfs地址
export function getNFTMetadataURL(hash: string) {
  return `https://cloudflare-ipfs.com/ipfs/${hash}`;
}

//解析nftjson得到nft结构体数据
export function getNFTMetadataFromJson(json: string) {
  const metadata: NFTMetadata = JSON.parse(json);
  return metadata;
}

//从链接拿数据并且解析为结构图
export async function getNFTMetadataFromURL(url: string) {
 let data = await getJsonFromIPFSByLink(url)
  return  getNFTMetadataFromJson(data);
}


//将NFT的元数据生成溯源产品对象
export async function NFTMetadataToTraceAblilityInfo(id: any, metadata: NFTMetadata) {

  //获取该NFT的所有者地址
  let owner = await getAddress(id);


  //合约的地址
    let contractAddress = metadata.address;

     let product: TraceAblilityProduct =  {
       name: metadata.name,
       description: metadata.description,
       image: metadata.image,
       external_url: metadata.external_url,
       attributes: metadata.attributes,
       id: id,
       owner: owner,
       contractAddress: contractAddress,
       metadata: metadata,
     };
  return product;
}


//通过合约地址和传入的信息mint一个nft
export async function mintNFTbyContract(contractAddress: any,to:any, uri:string) {
    //调用mint方法
    const trNFT = await getContract(abi,contractAddress);
    console.log(to,uri)
    return  trNFT.methods.mint(to,uri).send({from:await getWalletAccount()})
}

//获取在注册中心的所有nft
export async function getAllNFTsInCenter(user:string){
    // console.log(user)
    let contracts = await getAllContractByUser(user);
     contracts = new Set(contracts)
     let nfts = [];
    //循环所有的合约，获取所有的tokenid
  for (let contract of contracts){

      //判断是否为合约地址
      if(!await isContractAddress(contract)){
          continue;
      }

      let trNFT = await getContract(abi,contract);
      let tokens = await trNFT.methods.tokensOfOwner(user).call()

      // console.log(tokens)
      //循环所有的tokenid，获取所有的nft
       for(let i = 0;i<tokens.length;i++){
           let token = tokens[i];
           let nft = await  getTraceNodeNftByIdAndContractAddress(token,contract);
           // console.log(nft)
           nfts.push(nft);
       }
  }
    return nfts;
}


//通过id和合约地址构建TraceNodeNft
export async function  getTraceNodeNftByIdAndContractAddress(id:any,contractAddress:any){
    let metaData = await  getNFTbyContract(contractAddress,id);
    let node = await NFTMetadataToTraceNodeNft(metaData)
    node.id = id;
    return node;
}

//通过合约地址和nft的id构建一个元数据
export async function getNFTbyContract(contractAddress: any,id:any) {
    const trNFT = await getContract(abi,contractAddress);
    let token = await trNFT.methods.tokenURI(id).call();
    console.log(token)
    let metaData =  await getNFTMetadataFromURL(token);
    metaData.address = contractAddress;
    return metaData;
}

//根据metaData构建一个TraceNodeNft
export async function NFTMetadataToTraceNodeNft(metadata: NFTMetadata) {
    console.log(metadata)
    let nft:TraceNodeNft = {
        id:0,
        owner:"",
        contractAddress:metadata.address,
        metadata:metadata,
        data:{
            proofCenterAddress:metadata.address,
            proofCenterName:metadata.name.name,
            proofCenterNameDescription:metadata.description,
            proofCenterNameImage:metadata.image,
            proofCenterNameExternalUrl:metadata.external_url,
            proofCenterNameAttributes:metadata.attributes,
            traceabilityInfo:JSON.parse(metadata.attributes[0])
        }
    }
    return nft;
}