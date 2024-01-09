import { getOperateDataPoolItem } from "./OperateDataPool";
const sendOperate = (socket, operate) => {
    return new Promise((resolve, reject) => {
        var _a;
        socket.send(JSON.stringify(operate));
        let id = (_a = operate.signature) === null || _a === void 0 ? void 0 : _a.toString();
        // 开启轮询
        let intervalId = setInterval(() => {
            let data = getOperateDataPoolItem(id);
            if (data != undefined && data != null) {
                if (data.execResult != null && !data.execResult["resultFlag"]) {
                    console.error(data.execResult);
                }
                else {
                    resolve(data);
                }
                //清除定时
                clearInterval(intervalId);
            }
        }, 500);
    });
};
export { sendOperate };
