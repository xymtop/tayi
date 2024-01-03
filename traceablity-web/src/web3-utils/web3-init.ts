import {checkWallet} from "./wallet-utils";
import {Web3} from "web3";
import { RegisteredSubscription } from "web3/lib/commonjs/eth.exports";

let web3: Web3<RegisteredSubscription> | null = null

const web3Connect = async function (){
    // web3 = new Web3('http://localhost:8545');
    web3 = new Web3(window.ethereum);
    return web3
}

const getWeb3 = async function () {
    if (web3 == null){
          web3 = await web3Connect();
    }
   return web3;
}
const web3InitFun =async function (){
    await checkWallet()
}

export   {web3InitFun,getWeb3}