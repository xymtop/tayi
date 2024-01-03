// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract TBToken is ERC20 {
    uint256 public constant INITIAL_ALLOTMENT = 100000;
    uint256 public totalNFTs;

    mapping(address => bool) public hasReceivedInitialAllotment;


    address private  admin;

    constructor() ERC20("TraceCoin", "TC") {
        admin = msg.sender;
    }

    function firstTimeClaim(address recipient) public {
        require(!hasReceivedInitialAllotment[recipient], "Already received initial allotment");
        _mint(recipient, INITIAL_ALLOTMENT);
        hasReceivedInitialAllotment[recipient] = true;
    }

    function updateTotalNFTs(uint256 _totalNFTs) public  {
        totalNFTs = _totalNFTs;
    }


//有待计算
    function mintBasedOnCirculation() public  {
        uint256 circulationIndex = totalNFTs / totalSupply();
        uint256 newMintAmount = totalSupply() * circulationIndex / (10 ** decimals());
        _mint(admin, newMintAmount);
    }
}
