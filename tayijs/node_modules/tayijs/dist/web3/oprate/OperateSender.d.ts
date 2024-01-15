import { Oprate } from "./Oprate";
import { OperateResult } from "./OperateResult";
declare const sendOperate: (socket: WebSocket, operate: Oprate) => Promise<OperateResult>;
export { sendOperate };
