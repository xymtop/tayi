import { OperateResult } from "../oprate/OperateResult";
export declare class Event {
    onMessage: (msgData: string) => void;
    onResultHash: (hash: string) => void;
    OnResult: (resultData: OperateResult) => void;
}
