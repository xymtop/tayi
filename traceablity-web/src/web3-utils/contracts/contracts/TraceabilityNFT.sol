// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "./ERC6551Registry.sol";
import "./SocialAccount.sol";
import "./interfaces/IERC6551Account.sol";
import "./TraceabilityStorage.sol";

contract TraceabilityNFT is ERC721URIStorage {
    address private _owner;

    uint256 private _tokenId;

    //可以共用一个EIP6551注册中心
    ERC6551Registry private _registry;
    //EIP6551的逻辑合约地址
    SocialAccount private _accountContract;
    
    mapping(uint256 => address) public accountAddress;



    event minted(uint256,address account);



    constructor() ERC721("Traceability NFT", "TNFT") {
        _owner = msg.sender;

        //应该作为参数来进行传递
        _registry = new ERC6551Registry();
        _accountContract = new SocialAccount();

    }

    function mint(address to, string memory tokenURI) external {
        _tokenId += 1;
        uint256 salt = generateRandomSalt();
        bytes memory emptyBytes = "";
        accountAddress[_tokenId] = _registry.createAccount(address(_accountContract), block.chainid, address(this), _tokenId, salt, emptyBytes);
        address expectedAddress = _registry.account(address(_accountContract), block.chainid, address(this), _tokenId, salt);
        require( accountAddress[_tokenId] == expectedAddress, "wrong addresses");
        _safeMint(to, _tokenId);
        _setTokenURI(_tokenId, tokenURI);

        emit minted(_tokenId,expectedAddress);
    }

    function nextId() internal view returns(uint256) {
        return _tokenId;
    }

    function getNextId() external view returns(uint256) {
        return nextId();
    }


    function generateRandomSalt() internal view returns (uint256) {
        bytes32 hash = keccak256(abi.encodePacked(block.timestamp, msg.sender, nonce()));
        return uint256(hash);
    }

    function owner() external view returns(address) {
        return _owner;
    }

    //返回一个适用的随机数
    function nonce() internal pure returns (uint256) {
        return 520;
    }

    function transferETH(address payable to, uint amount) internal {
        (bool success, ) = to.call{value: amount}("");
        require(success, "Failed to send Ether");
    }

     function tokensOfOwner(address account) public  view  returns (uint256[] memory) {
        uint256 tokenIdsIdx;
        uint256 tokenIdsLength = balanceOf(account);
        uint256[] memory tokenIds = new uint256[](tokenIdsLength);
        uint256 supply = nextId();
        for (uint256 i = 1; i <= supply; i++) {
            if (ownerOf(i) == account) {
                tokenIds[tokenIdsIdx] = i;
                tokenIdsIdx++;
            }
        }

        return tokenIds;
    }


    function setOwner(address newOwner) external returns(bool) {
        require(msg.sender == _owner);
        _owner = newOwner;
        return true;
    }


    function getAddress(uint id) public view returns(address){
        return accountAddress[id];
    }

    
}