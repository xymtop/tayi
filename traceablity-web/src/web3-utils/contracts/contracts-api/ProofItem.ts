//证明流程数据


//nft合约的所有方法


import {abi} from "./json/ProofItem.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";


const proofItem = await getContract(abi,"0xd9145CCE52D386f254917e481eB44e9943F39138");

// balanceOf(address owner): 返回指定地址拥有的NFT数量。
async function balanceOf(owner:any) {
    return proofItem.methods.balanceOf(owner).call();
}


// ownerOf(uint256 tokenId): 返回拥有指定tokenId的NFT的地址。
async function ownerOf(tokenId:any) {
    return proofItem.methods.ownerOf(tokenId).call();
}
// safeTransferFrom(address from, address to, uint256 tokenId, bytes data): 安全地将NFT从一个地址转移到另一个地址。
async function safeTransferFrom(from:any,to:any,tokenId:any,data:any) {
    return proofItem.methods.safeTransferFrom(from,to,tokenId,data).send({from:await getWalletAccount()})
}
// transferFrom(address from, address to, uint256 tokenId): 将NFT从一个地址转移到另一个地址，不包括安全检查。
async function transferFrom(from:any,to:any,tokenId:any) {
    return proofItem.methods.transferFrom(from,to,tokenId).send({from:await getWalletAccount()})
}
// approve(address to, uint256 tokenId): 授权另一个地址操作特定的NFT。
async function approve(to:any,tokenId:any) {
    return proofItem.methods.approve(to,tokenId).send({from:await getWalletAccount()})
}

// getApproved(uint256 tokenId): 查询特定NFT的授权地址。
async function getApproved(tokenId:any) {
    return proofItem.methods.getApproved(tokenId).call();
}

// setApprovalForAll(address operator, bool approved): 授权或撤销另一个地址操作所有者的所有NFT。
async function setApprovalForAll(operator:any,approved:any) {
    return proofItem.methods.setApprovalForAll(operator,approved).send({from:await getWalletAccount()})
}

// isApprovedForAll(address owner, address operator): 查询一个地址是否被授权操作另一个地址的所有NFT。
async function isApprovedForAll(owner:any,operator:any) {
    return proofItem.methods.isApprovedForAll(owner,operator).call();
}

// safeTransferFrom(address from, address to, uint256 tokenId): 安全地将NFT从一个地址转移到另一个地址，只需要tokenId。
async function safeTransferFrom2(from:any,to:any,tokenId:any) {
    return proofItem.methods.safeTransferFrom(from,to,tokenId).send({from:await getWalletAccount()})
}

// metadata URI(uint256 tokenId): 返回特定NFT的元数据URI，通常包含关于NFT的详细信息。
async function tokenURI(tokenId:any) {
    return proofItem.methods.tokenURI(tokenId).call();
}
