import {Web3} from "web3/lib/types";
import {getWeb3} from "./web3-init";
import {layer} from "@layui/layui-vue";


let account: any = null

const  getWalletAccount = async function (){
    if (account == null){
        await initWallet();
        await  setAccount(account)
    }

    return account
}

const initWallet =async function (){
  const accounts = await ethereum.request({ method: 'eth_requestAccounts' });
  account = accounts[0];
}

const  checkWallet = async function () {
    if (typeof window.ethereum === "undefined") {
        //没安装MetaMask钱包进行弹框提示
       layer.msg("您还没有MetaMask钱包,请安装后重试")
    } else {
      // const  web3 = await getWeb3();
      return   await getWalletAccount()
    }
}


const  setAccount = async function (tempAccount: string | undefined){
    const  web3 = await  getWeb3();
    web3.eth.defaultAccount = tempAccount
}

//获取区块高度
async function getBlockHeight(web3: { eth: { getBlockNumber: () => any; }; }) {
    try {
        const blockHeight = await web3.eth.getBlockNumber();
        return blockHeight;
    } catch (error) {
        console.error('Error getting block height:', error);
    }
}

async function getLatestBlock(web3: { eth: { getBlock: (arg0: string) => any; }; }) {
    try {
        const latestBlock = await web3.eth.getBlock('latest');
        return latestBlock;
    } catch (error) {
        console.error('Error getting latest block:', error);
    }
}

async function getTotalTransactions(web3: { eth: { getBlockNumber: () => any; getBlock: (arg0: number) => any; }; }) {
    try {
        const blockHeight = await web3.eth.getBlockNumber();
        let totalTransactions = 0;

        for (let i = 0; i <= blockHeight; i++) {
            const block = await web3.eth.getBlock(i);
            totalTransactions += block.transactions.length;
        }

        return totalTransactions;
    } catch (error) {
        console.error('Error getting total transactions:', error);
    }
}


//监听最新交易
const  listenNewBlock = async function (callback: (error: Error, blockHeader: any) => void){
    const  web3 = await  getWeb3();
    web3.eth.subscribe('newBlockHeaders', callback)
}

async function getContractCalls(web3: { eth: { getBlock: (arg0: any, arg1: boolean) => any; }; }, contractAddress: string, fromBlock: any, toBlock: number) {
    let callsCount = 0;

    for (let i = fromBlock; i <= toBlock; i++) {
        const block = await web3.eth.getBlock(i, true);

        if (block && block.transactions) {
            block.transactions.forEach(tx => {
                if (tx.to && tx.to.toLowerCase() === contractAddress.toLowerCase()) {
                    callsCount++;
                }
            });
        }
    }

    return callsCount;
}

export {checkWallet,getWalletAccount,setAccount,listenNewBlock,getBlockHeight,getLatestBlock,getTotalTransactions,getContractCalls}