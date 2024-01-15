import {Payload} from "./payload/Payload";

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

export function buildOperateResult(data: any ) {
    let operateResult;

    return operateResult = {
        operateId: data.operateId || "",
        operateHash: data.operateHash || "",
        operateType: data.operateType || "",
        operateCmd: data.operateCmd || "",
        sender: data.sender || "",
        timestamp: data.timestamp || 0,
        nonce: data.nonce || 0,
        oprateMeta: data.oprateMeta || {payload: ""},
        operateReceipt: data.operateReceipt || {status: ""},
        execResult: data.execResult || {args: [], cmd: "", result: null, resultFlag: false}
    };
}

// const data: OperateResult = {
//     operateId: "de513cf1bfe84dcbbd7fb74b93002feb",
//     operateHash: "e9d304370267e93b0ca509328d041e98c775eccc6f249b7830a19d5c6a0b090a",
//     operateType: "EXEC",
//     operateCmd: "executeContract",
//     sender: "0x1234abcd5678efgh",
//     timestamp: 1617625500000,
//     nonce: 5,
//     oprateMeta: {
//         payload: "{\"id\":\"bde67d5f2b8e6729b92e038644e66e82493c930af61ed9aba78affcb3f8b148d\",\"method\":\"getName\"}"
//     },
//     operateReceipt: {
//         status: "SUCCESS"
//     },
//     execResult: {
//         args: [
//             "bde67d5f2b8e6729b92e038644e66e82493c930af61ed9aba78affcb3f8b148d",
//             "getName"
//         ],
//         cmd: "executeContract",
//         result: 2,
//         resultFlag: true
//     }
// };
//
// console.log(data); // 打印操作结果对象
