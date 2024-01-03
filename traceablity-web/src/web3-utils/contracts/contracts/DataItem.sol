// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract DataItem {

    address public user;


    constructor(){
        user = msg.sender;
    }

    function getUser() public view returns(address){
    
        return user;
    }


    function setUser(address my) public{
        user = my;
    }
}
