// const contract = new web3.eth.Contract(abi,"0xfd09e83c0d0593B3D25877E2F7B2DA691938570e");


import {abi} from "./json/CenterDao.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";
import {getTaYi} from "../../tayi/TaYiUtils";


let tayi = getTaYi()


let  centerDao = "ab78291adf108cd58829e9eee78b3e8655565b9fa107403ce511a7958bf3cc4a"

// centerDao  = await tayi.deploy("QmUu5ycnHiy4RBG71pkfPo6a4HX9vEXUTbfZ4W6xeD1kuD")
// console.log(centerDao)

//getAllProposals
async function getAllProposals() {
   return tayi.call(centerDao,"getAllProposals")
}

// getProposalCount
async function getProposalCount() {
    return tayi.call(centerDao,"getProposalCount")
}

// getProposal(uint256 _proposalIndex)
async function getProposal(proposalIndex:any) {
    return tayi.call(centerDao,"getProposal",{
        id:proposalIndex
    })
}

// createProposal(string memory _title, string memory _descriptionCID, uint256 _deadline)
async function createProposal(title:any,descriptionCID:any,deadline:any) {
    return tayi.call(centerDao,"createProposal",{
        id:Date.now(),
        title,
        descriptionCID,
        deadline
    })
}

// vote(uint256 _proposalIndex, bool _vote)
async function vote(proposalIndex:any,vote:any) {
    return tayi.call(centerDao,"vote",{
        id:proposalIndex,
        vote
    })
}

// executeProposal(uint256 _proposalIndex)
async function executeProposal(proposalIndex:any) {
    return tayi.call(centerDao,"executeProposal",{
        id:proposalIndex
    })
}


// getProposalVotes(uint256 _proposalIndex)
async function getProposalVotes(proposalIndex:any) {
    return tayi.call(centerDao,"getProposalVotes",{
        proposalIndex
    })
}


export {getAllProposals,getProposalCount,getProposal,createProposal,vote,executeProposal,getProposalVotes}