// const contract = new web3.eth.Contract(abi,"0xfd09e83c0d0593B3D25877E2F7B2DA691938570e");


import {abi} from "./json/ContractRegistry.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";
import {getNFTMetadataURL} from "../../NFTUtils";
import {ContractInfo} from "../../infoTypes";
import {getTaYi} from "../../tayi/TaYiUtils";



const contractRegistry = "1dc3dab1cbd5a0b73cc4a53bb45cd46518b44c8f9d3c79d4a60bcaa93e75c33c"

let tayi = getTaYi()
// console.log(await tayi.deploy("QmVQEMffQPNZVM15s8kWzdogtyEiMyoect1oYUcXTEEvsX"))

//注册合约
export  async function registerContract(contractAddress:any,name:any,description:any,whitepaperIPFSHashes:string[]) {
   return tayi.call(contractRegistry,"registerContract",{
       contractAddress,
       name,
       description,
       whitepaperIPFSHashes
   })
}

// function updateStatus(
//     address _contractAddress, // 合约地址
//     Status _status // 新状态
// 一个公共函数，用于更新合约的状态
export  async function updateStatus(contractAddress:any,status:any) {
    return tayi.call(contractRegistry,"updateStatus",{
        contractAddress,
        status
    })
}

// getAllContracts返回所有合约
export  async function getAllContracts() {
    return tayi.call(contractRegistry,"getAllContracts")
}

// getApprovedContracts
// 返回已批准的合约
export  async function getApprovedContracts() {
    return tayi.call(contractRegistry,"getApprovedContracts")
}
//function isContractApprovedAndNotOfflineStruct(address _contractAddress)
//返回合约是否批准且不脱机
export  async function isContractApprovedAndNotOfflineStruct(contractAddress:any) {
    return tayi.call(contractRegistry,"isContractApprovedAndNotOfflineStruct")
}


//function isContractApprovedAndNotOffline(address _contractAddress)
//判断是否批准的合约
export  async function isContractApprovedAndNotOffline(contractAddress:any) {
    return tayi.call(contractRegistry,"isContractApprovedAndNotOffline",{
        contractAddress
    })
}


// isContractRegistered (address _contractAddress)
// 返回合约是否注册
export  async function isContractRegistered(contractAddress:any) {
    return tayi.call(contractRegistry,"isContractRegistered",{
        contractAddress
    })
}



//getContractInfo(address _contractAddress)
export  async function getContractInfo(contractAddress:any) {
    const info:ContractInfo =   await  tayi.call(contractRegistry,"getContractInfo",{
        contractAddress
    })
       for (let i = 0; i < info.whitepaperIPFSHashes.length; i++) {
           info.whitepaperIPFSHashes[i] = getNFTMetadataURL(info.whitepaperIPFSHashes[i]);
       }
    return info;
}

//获取所有的详细合约信息
export  async function getAllContractDetails() {
    let contractDetails:ContractInfo[] = [];
     const allContracts = await getAllContracts();
     for (let i = 0; i < allContracts.length; i++) {
         contractDetails[i] = allContracts[i]
         // //构建所有的IPFS链接
            let whitepaperIPFSHashes:string = contractDetails[i]["whitepaperIPFSHashes"].toString();
            let hashs  = whitepaperIPFSHashes.slice(1,whitepaperIPFSHashes.length-1).split(",")
            for (let j = 0; j < hashs.length; j++) {
                hashs[j] = getNFTMetadataURL(hashs[j]);
            }
         contractDetails[i].whitepaperIPFSHashes = hashs;
     }
        return contractDetails;
}

////新增新的数据
//     function addTrac(address _user,address _contract)
export async function addTrac(user:any,contract:any) {
    return contractRegistry.methods.addTrac(user,contract).send({from:await getWalletAccount()})
}

// getAllContractByUser(address _user)
export async function getAllContractByUser(user:any) {
    return contractRegistry.methods.getAllContractByUser(user).call();
}