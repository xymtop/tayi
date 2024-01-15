import { User } from "./user/user";
import { Oprate } from "./oprate/Oprate";
import { Contract } from "./contract/Contract";
import { Event } from "./events/Event";
import { Payload } from "./oprate/payload/Payload";
import { OperateResult } from "./oprate/OperateResult";
export declare class TaYi {
    socket: WebSocket;
    user: User;
    oprate: Oprate | undefined;
    contract: Contract | undefined;
    event: Event | undefined;
    constructor(user: string, ip: string);
    toSendOperate: (operate: Oprate) => Promise<OperateResult>;
    buildOperateContractExecNoargs: (contract: string, funName: string) => Oprate;
    buildOperateContractExecArgs: (contract: string, funName: string, args: Object) => Oprate;
    buildOperate: (operateType: string, operateCmd: string, payload: Payload) => Oprate;
    deploy: (contract: string) => Promise<any>;
    call: (contract: string, funName: string, args?: Object) => Promise<any>;
    buildQueryOperate: (cmd: string, args?: string[]) => Oprate;
    sendQuery: (cmd: string, args?: string[]) => Promise<any>;
    deployAi: (aiHash: string) => Promise<any>;
    callAi: (aiHash: string, aiInput: Object) => Promise<any>;
    trainAi: (aiHash: string, trainDataAddress: string) => Promise<any>;
}
declare let tayi: any;
declare const newTaYi: (user: string, ip: string) => TaYi;
export { newTaYi, tayi };
