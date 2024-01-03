import { Oprate } from "./Oprate";
import { OperateResult } from "./OperateResult";
declare const sendOperate: (socket: any, operate: Oprate) => Promise<OperateResult>;
export { sendOperate };
