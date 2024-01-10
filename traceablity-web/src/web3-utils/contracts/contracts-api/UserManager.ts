// const contract = new web3.eth.Contract(abi,"0xfd09e83c0d0593B3D25877E2F7B2DA691938570e");


import {abi} from "./json/UserManager.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";
import {getTaYi} from "../../tayi/TaYiUtils";
import {TaYi} from "tayijs/dist/web3/TaYi";


let userManager = "90dfedaa8973f564d8a34f85464dfe2f1c1643e7f7c9a936ad2b72f6fd12027f";

const tayi:TaYi = getTaYi();



async function getUserInfo(account: any) {
    // userManager = await tayi.deploy("QmYTAJA6iFpz2TZ9gKCcQujeowoMjWEdBaLH1dui1ih2yQ")
    // console.log(userManager)
    return tayi.call(userManager,"getUserInfo",{
        account
    })
}

async  function registerUser(nickname:any,gender:any,email:any,bio:any,phone:any,role:any){
  return   tayi.call(userManager,"registerUser",{
      account:tayi.user.address,
      nickname,
      gender,
      email,
      bio,
      phone,
      role
    })
}

async  function updateUser(account:any,nickname:any,gender:any,email:any,bio:any,phone:any,role:any,isBanned:any){
    return tayi.call(userManager,"updateUser",{
            account,
            nickname,
            gender,
            email,
            bio,
            phone,
            role
        })
}

async  function banUser(account:any){
    return tayi.call(userManager,"banUser",{
        account
    })
}
async function getAllUsers(){
    return tayi.call(userManager,"getAllUsers")
}
export {getUserInfo,getAllUsers,registerUser,updateUser,banUser}