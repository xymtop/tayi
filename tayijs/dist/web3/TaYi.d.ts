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
    buildOperateContractExecArgs: (contract: string, funName: string, args: string[]) => Oprate;
    buildOperate: (operateType: string, operateCmd: string, payload: Payload) => Oprate;
    call: (contract: string, funName: string, args?: string[]) => Promise<any>;
}
declare let tayi: any;
declare const newTaYi: (user: string, ip: string) => TaYi;
export { newTaYi, tayi };
