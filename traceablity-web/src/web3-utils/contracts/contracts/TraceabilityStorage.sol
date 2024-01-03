// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./CommentNFT.sol";
import "./ContractRegistry.sol";



contract TraceabilityStorage {
    // 定义一个结构体来存储NFT的详细信息
    struct NFTDetail {
        uint256 tokenId;  // NFT的Token ID
        address issuer;   // 颁发NFT的合约地址
    }

    // 溯源产品地址 => NFT详细信息的数组
    mapping(address => NFTDetail[]) private productToNFTDetails;

    // 记录溯源过程NFT
    function recordTraceabilityNFT(address productAddress, address issuer, uint256 tokenId) public {
        require(productAddress != address(0), "Invalid product address");
        require(issuer != address(0), "Invalid issuer address");
        // 添加新的NFT详细信息到数组中
        productToNFTDetails[productAddress].push(NFTDetail(tokenId, issuer));
    }

    // 查询某个溯源产品的所有NFT详细信息
    function getProductNFTDetails(address productAddress) public view returns (NFTDetail[] memory) {
        return productToNFTDetails[productAddress];
    }
}
