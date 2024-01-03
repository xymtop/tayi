// const contract = new web3.eth.Contract(abi,"0xfd09e83c0d0593B3D25877E2F7B2DA691938570e");


import {abi} from "./json/ContractRegistry.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";
import {getNFTMetadataURL} from "../../NFTUtils";
import {ContractInfo} from "../../infoTypes";



const contractRegistry = await getContract(abi,"0xa6e99A4ED7498b3cdDCBB61a6A607a4925Faa1B7");

// registerContract(
//     address _contractAddress, // 合约地址
//     string memory _name, // 合约名字
//     string memory _description, // 合约描述
//     string[] memory _whitepaperIPFSHashes // 白皮书IPFS哈希数组
// )


//   struct ContractInfo {
//         address contractAddress; // 合约的地址
//         string name; // 合约的名字
//         string description; // 合约的描述
//         address owner; // 提交合约的用户地址
//         uint256 applicationTime; // 提交合约的时间
//         string[] whitepaperIPFSHashes; // 白皮书的IPFS哈希数组
//         Status status; // 合约的状态
//     }




//注册合约
export  async function registerContract(contractAddress:any,name:any,description:any,whitepaperIPFSHashes:string[]) {
    return contractRegistry.methods.registerContract(contractAddress,name,description,whitepaperIPFSHashes).send({from:await getWalletAccount()})
}

// function updateStatus(
//     address _contractAddress, // 合约地址
//     Status _status // 新状态
// 一个公共函数，用于更新合约的状态
export  async function updateStatus(contractAddress:any,status:any) {
    return contractRegistry.methods.updateStatus(contractAddress,status).send({from:await getWalletAccount()})
}

// getAllContracts返回所有合约
export  async function getAllContracts() {
    return contractRegistry.methods.getAllContracts().call();
}

// getApprovedContracts
// 返回已批准的合约
export  async function getApprovedContracts() {
    return contractRegistry.methods.getApprovedContracts().call();
}
//function isContractApprovedAndNotOfflineStruct(address _contractAddress)
//返回合约是否批准且不脱机
export  async function isContractApprovedAndNotOfflineStruct(contractAddress:any) {
    return contractRegistry.methods.isContractApprovedAndNotOfflineStruct(contractAddress).call();
}


//function isContractApprovedAndNotOffline(address _contractAddress)
//判断是否批准的合约
export  async function isContractApprovedAndNotOffline(contractAddress:any) {
    return contractRegistry.methods.isContractApprovedAndNotOffline(contractAddress).call();
}


// isContractRegistered (address _contractAddress)
// 返回合约是否注册
export  async function isContractRegistered(contractAddress:any) {
    return contractRegistry.methods.isContractRegistered(contractAddress).call();
}



//getContractInfo(address _contractAddress)
export  async function getContractInfo(contractAddress:any) {
    const info:ContractInfo = await contractRegistry.methods.getContractInfo(contractAddress).call();
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
         contractDetails[i] = await getContractInfo(allContracts[i]);
         // //构建所有的IPFS链接
         //    let whitepaperIPFSHashes = contractDetails[i].whitepaperIPFSHashes;
         //    for (let j = 0; j < whitepaperIPFSHashes.length; j++) {
         //        whitepaperIPFSHashes[j] = getNFTMetadataURL(whitepaperIPFSHashes[j]);
         //    }
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