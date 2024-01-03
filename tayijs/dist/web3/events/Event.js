//定时查询队列
import { Result } from "../common/Result";
import { buildOperateResult } from "../oprate/OperateResult";
import { addOperateDataPoolItem } from "../oprate/OperateDataPool";
export class Event {
    constructor() {
        //接收到消息的事件
        this.onMessage = (msgData) => {
            //判断是否为json字符串
            if (isJSONString(msgData)) {
                let data = JSON.parse(msgData);
                let result = new Result(data);
                if (result != null) {
                    if (result.result) {
                        if (canParasOprateResult(result.data)) {
                            let operateResult = buildOperateResult(result.data);
                            this.OnResult(operateResult);
                        }
                        else {
                            this.onResultHash(result.data);
                        }
                    }
                }
            }
        };
        //接收到操作执行的哈希值
        this.onResultHash = (hash) => {
            console.log(hash);
        };
        //获取到哈希的结果
        this.OnResult = (resultData) => {
            let hash = resultData.operateHash;
            addOperateDataPoolItem(hash, resultData);
        };
    }
}
function isJSONString(str) {
    try {
        JSON.parse(str);
        return true;
    }
    catch (e) {
        return false;
    }
}
function canParasOprateResult(obj) {
    try {
        // @ts-ignore
        let execResult = buildOperateResult(obj);
        if (execResult.operateHash == null || execResult.operateHash.length == 0) {
            return false;
        }
        return true;
    }
    catch (e) {
        // console.log(e)
        return false;
    }
}
