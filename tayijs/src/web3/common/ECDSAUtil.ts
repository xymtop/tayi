import { ec as EC } from "elliptic";
import CryptoJS from 'crypto-js';

class ECDSAUtil {
    ec: EC;
    constructor() {
         this.ec = new EC('secp256k1');
    }

    hashMessage(message: any) {
        // 使用简单的哈希函数（实际应用中可能需要更复杂的哈希算法）
        return CryptoJS.SHA256(message).toString();
    }

    signMessage(message: any, privateKey: any) {
        const keyPair = this.ec.keyFromPrivate(privateKey);
        const hash = this.hashMessage(message);
        const signature = keyPair.sign(hash);
        return signature.toDER('hex');
    }
}

export default ECDSAUtil;
