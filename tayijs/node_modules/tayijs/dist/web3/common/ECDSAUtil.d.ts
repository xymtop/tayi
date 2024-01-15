import { ec as EC } from "elliptic";
declare class ECDSAUtil {
    ec: EC;
    constructor();
    hashMessage(message: any): string;
    signMessage(message: any, privateKey: any): string;
}
export default ECDSAUtil;
