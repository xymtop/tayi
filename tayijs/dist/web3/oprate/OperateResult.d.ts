import { Payload } from "./payload/Payload";
interface OperateMeta {
    payload: Payload;
}
interface OperateReceipt {
    status: string;
}
interface ExecResult {
    args: string[];
    cmd: string;
    result: any;
    resultFlag: boolean;
}
export interface OperateResult {
    operateId: string;
    operateHash: string;
    operateType: string;
    operateCmd: string;
    sender: string;
    timestamp: number;
    nonce: number;
    oprateMeta: OperateMeta;
    operateReceipt: OperateReceipt;
    execResult: ExecResult;
}
export declare function buildOperateResult(data: any): {
    operateId: any;
    operateHash: any;
    operateType: any;
    operateCmd: any;
    sender: any;
    timestamp: any;
    nonce: any;
    oprateMeta: any;
    operateReceipt: any;
    execResult: any;
};
export {};
