// const contract = new web3.eth.Contract(abi,"0xfd09e83c0d0593B3D25877E2F7B2DA691938570e");


import {abi} from "./json/CenterDao.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";



const centerDao = await getContract(abi,"0x9A676e781A523b5d0C0e43731313A708CB607508");


//getAllProposals
async function getAllProposals() {
    return centerDao.methods.getAllProposals().call();
}

// getProposalCount
async function getProposalCount() {
    return centerDao.methods.getProposalCount().call();
}

// getProposal(uint256 _proposalIndex)
async function getProposal(proposalIndex:any) {
    return centerDao.methods.getProposal(proposalIndex).call();
}

// createProposal(string memory _title, string memory _descriptionCID, uint256 _deadline)
async function createProposal(title:any,descriptionCID:any,deadline:any) {
    return centerDao.methods.createProposal(title,descriptionCID,deadline).send({from:await getWalletAccount()})
}

// vote(uint256 _proposalIndex, bool _vote)
async function vote(proposalIndex:any,vote:any) {
    return centerDao.methods.vote(proposalIndex,vote).send({from:await getWalletAccount()})
}

// executeProposal(uint256 _proposalIndex)
async function executeProposal(proposalIndex:any) {
    return centerDao.methods.executeProposal(proposalIndex).send({from:await getWalletAccount()})
}


// getProposalVotes(uint256 _proposalIndex)
async function getProposalVotes(proposalIndex:any) {
    return centerDao.methods.getProposalVotes(proposalIndex).call();
}


export {getAllProposals,getProposalCount,getProposal,createProposal,vote,executeProposal,getProposalVotes}