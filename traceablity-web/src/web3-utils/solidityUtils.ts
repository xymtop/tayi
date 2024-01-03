import {getWeb3} from "./web3-init";

const web3 =await  getWeb3();
/**
 * 检查地址是否为智能合约地址。
 *
 * @param {string} address - 要检查的地址。
 * @returns {Promise<boolean>} - 是否为智能合约地址。
 */
export  async function isContractAddress(address: string) {
    const code = await web3.eth.getCode(address);
    return code !== '0x';
}

/**
 * 检查智能合约是否具有指定的无参数方法。
 *
 * @param {string} contractAddress - 智能合约地址。
 * @param {string} methodName - 方法名（无参数）。
 * @returns {Promise<boolean>} - 是否具有该方法。
 */
export  async function hasMethod(contractAddress: string, methodName: string) {
    try {
        const data = web3.eth.abi.encodeFunctionSignature(`${methodName}()`);
        await web3.eth.call({ to: contractAddress, data });
        return true; // 如果没有错误，假定方法存在
    } catch (error) {
        return false; // 如果出现错误，假定方法不存在
    }
}
