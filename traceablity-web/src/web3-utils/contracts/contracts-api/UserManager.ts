// const contract = new web3.eth.Contract(abi,"0xfd09e83c0d0593B3D25877E2F7B2DA691938570e");


import {abi} from "./json/UserManager.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";


const userManager = await getContract(abi,"0x5FC8d32690cc91D4c39d9d3abcBD16989F875707");

async function getUserInfo(user: any) {
    return userManager.methods.getUserInfo(user).call();
}

async  function registerUser(nickname:any,gender:any,email:any,bio:any,phone:any,role:any){
    return userManager.methods.registerUser(nickname,gender,email,bio,phone,role).send({from:await getWalletAccount()})
}

async  function updateUser(acconut:any,nickname:any,gender:any,email:any,bio:any,phone:any,role:any,isBanned:any){
    return userManager.methods.updateUser(acconut,nickname,gender,email,bio,phone,role,isBanned).send({from:await getWalletAccount()})
}

async  function banUser(account:any){
    return userManager.methods.banUser(account).send({from:await getWalletAccount()})
}
async function getAllUsers(){
    return userManager.methods.getAllUsers().call();
}
export {getUserInfo,getAllUsers,registerUser,updateUser,banUser}