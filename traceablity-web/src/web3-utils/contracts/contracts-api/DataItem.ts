// const contract = new web3.eth.Contract(abi,"0xfd09e83c0d0593B3D25877E2F7B2DA691938570e");

// 用来测试
import {abi} from "./json/DataItem.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";


const traceabilityContract = await getContract(abi,"0xA51c1fc2f0D1a1b8494Ed1FE312d7C3a78Ed91C0");


const setUser = async function (data: any) {
    return  traceabilityContract.methods.setUser(data).send({from:await getWalletAccount()});
}


const getUser = async function () {
    console.log(await  traceabilityContract.methods.user().call())
    return await traceabilityContract.methods.getUser().call();
}


export {getUser,setUser}