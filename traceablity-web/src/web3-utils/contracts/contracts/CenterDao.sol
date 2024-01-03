// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

//投票治理中心
contract CenterDao {
    struct Proposal {
        uint id;
        string title;
        string descriptionCID; // IPFS上的提案内容标识符
        address creator;
        uint256 deadline;
        bool executed;
        uint256 positiveVotes;
        uint256 negativeVotes;
    }

    struct VoteRecord {
        address voter;
        uint256 voteTime ;
        bool vote;
    }

    Proposal[] private  proposals;
    mapping(uint256 => VoteRecord[]) public proposalVotes;

    // 创建提案
    function createProposal(string memory _title, string memory _descriptionCID, uint256 _deadline) public {
        uint len =  proposals.length;
        Proposal memory newProposal = Proposal({
            id:len,
            title: _title,
            descriptionCID: _descriptionCID,
            creator: msg.sender,
            deadline: _deadline,
            executed: false,
            positiveVotes: 0,
            negativeVotes: 0
        });
        proposals.push(newProposal);
    }

    // 投票
    function vote(uint256 _proposalIndex, bool _vote) public {
        require(_proposalIndex < proposals.length, "Proposal does not exist");
        Proposal storage proposal = proposals[_proposalIndex];
        require(block.timestamp <= proposal.deadline, "Voting has ended");
        require(!proposal.executed, "Proposal already executed");

        // 检查是否已投票
        for(uint i = 0; i < proposalVotes[_proposalIndex].length; i++) {
            require(proposalVotes[_proposalIndex][i].voter != msg.sender, "Already voted");
        }

        proposalVotes[_proposalIndex].push(VoteRecord({
            voter: msg.sender,
            vote: _vote,
            voteTime: block.timestamp
        }));

        if (_vote) {
            proposal.positiveVotes++;
        } else {
            proposal.negativeVotes++;
        }
    }

    // 执行提案
    function executeProposal(uint256 _proposalIndex) public {
       
        Proposal storage proposal = proposals[_proposalIndex];
        require(block.timestamp > proposal.deadline, "Voting period has not ended");
        require(!proposal.executed, "Proposal already executed");
        require(proposal.positiveVotes > proposal.negativeVotes, "Proposal did not pass");

        // 执行提案的逻辑
        // ...

        proposal.executed = true;
    }

    // 获取提案信息
    function getProposal(uint256 _proposalIndex) public view returns (Proposal memory) {
        require(_proposalIndex < proposals.length, "Proposal does not exist");
        Proposal storage proposal = proposals[_proposalIndex];
        return proposal;
    }

    // 获取提案总数
    function getProposalCount() public view returns (uint256) {
        return proposals.length;
    }

    // 获取特定提案的投票记录
    function getProposalVotes(uint256 _proposalIndex) public view returns (VoteRecord[] memory) {
        require(_proposalIndex < proposals.length, "Proposal does not exist");
        return proposalVotes[_proposalIndex];
    }

    //获取所有的提案
    function getAllProposals() public  view  returns (Proposal[] memory){
        return proposals;
    }
}
