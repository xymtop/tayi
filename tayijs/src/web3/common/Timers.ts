//设置轮询器
import {idMap} from "../events/EventExec";

const intervals: NodeJS.Timeout[] = [];

const addInterval = (fun:any, time: number | undefined)=>{
    //初始化一个轮询
    let intervalId = setInterval(fun, time);
    intervals.push(intervalId)
    return intervalId;
}

const stopInterval = (id: string | number | NodeJS.Timeout | undefined)=>{
    clearInterval(id);
    intervals.filter(item => item ==id);
}


const stopExecQuery = (hash:string)=>{
    //获取id
   let id =  idMap.get(hash)

    if (id != undefined &&id != null ){
        stopInterval(id)
    }
}
export {addInterval,stopExecQuery}
