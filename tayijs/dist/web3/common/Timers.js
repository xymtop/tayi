//设置轮询器
import { idMap } from "../events/EventExec";
const intervals = [];
const addInterval = (fun, time) => {
    //初始化一个轮询
    let intervalId = setInterval(fun, time);
    intervals.push(intervalId);
    return intervalId;
};
const stopInterval = (id) => {
    clearInterval(id);
    intervals.filter(item => item == id);
};
const stopExecQuery = (hash) => {
    //获取id
    let id = idMap.get(hash);
    if (id != undefined && id != null) {
        stopInterval(id);
    }
};
export { addInterval, stopExecQuery };
