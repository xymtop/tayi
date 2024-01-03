// const contract = new web3.eth.Contract(abi,"0xfd09e83c0d0593B3D25877E2F7B2DA691938570e");


import {abi} from "./json/Traceability.json";
import {getContract} from "../../oprate-utils";
import {getWalletAccount} from "../../wallet-utils";


const traceabilityContract = await getContract(abi,"0xb87c067ec9ff0C58f2EE843D805dB066296FA5c5");


const setData = async function (data: any) {
    return  traceabilityContract.methods.setData(data).send({from:await getWalletAccount()});
}

traceabilityContract.events.setDataEvent({
    filter:{},
    fromBlock: 'latest'
})
    .on('data', function(event){
        console.log(event);
    })

const getData = async function () {
    return await traceabilityContract.methods.getData().call();
}


export {setData,getData}