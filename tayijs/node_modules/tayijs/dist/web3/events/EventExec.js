import { addInterval, stopExecQuery } from "../common/Timers";
const num = 1000;
let idMap = new Map();
const onResultHashExec = (hash) => {
    //设置定时器，获取到结果
    let id = addInterval(getResult(hash), num);
    idMap.set(hash, id);
};
const getResult = (hash) => {
    //发送请求获取结果
    stopExecQuery(hash);
};
export { idMap, onResultHashExec, getResult };
