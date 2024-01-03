// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/token/ERC721/IERC721.sol";

contract CommentStorage {
   
    // 合约地址 => 评论NFT Token IDs
    mapping(address => uint256[]) private contractToComments;


    // 记录评论NFT
    function recordCommentNFT(address contractAddress, uint256 tokenId) public {
        // // 确认NFT已经转移到本合约
        // require(commentNFT.ownerOf(tokenId) == address(this), "NFT not owned by contract");

        // 将NFT Token ID记录到对应的合约地址下
        contractToComments[contractAddress].push(tokenId);
    }

    // 查询某个合约的所有评论NFT
    function getCommentsOfContract(address contractAddress) public view returns (uint256[] memory) {
        return contractToComments[contractAddress];
    }
}
