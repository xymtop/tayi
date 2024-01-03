import { User } from "../user/user";
import { Payload } from "./payload/Payload";
declare class Oprate {
    address: string | undefined;
    signature: string | undefined;
    data: OperateData | undefined;
}
declare class OperateData {
    operateId: string | undefined;
    operateHash: string | undefined;
    operateType: string | undefined;
    operateCmd: string | undefined;
    sender: string | undefined;
    timestamp: number | undefined;
    nonce: number | undefined;
    oprateMeta: OperateMeta | undefined;
}
declare class OperateMeta {
    payload: Payload | undefined;
}
declare const buildOprate: (sender: string) => Oprate;
declare const buildOperateNormal: (user: User, operateType: string, operateCmd: string, payload: Payload) => Oprate;
export { Oprate, buildOprate, buildOperateNormal };
