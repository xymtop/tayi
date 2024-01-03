import {getWeb3} from "./web3-init";


const  getContract = async function (abi: any,address:any){
    let web3 = await getWeb3();
    const contract = new web3.eth.Contract(abi,address);
    return contract;
}


export {getContract}
