import {Oprate} from "./Oprate";
import {getOperateDataPoolItem} from "./OperateDataPool";
import {OperateResult} from "./OperateResult";

const sendOperate = (socket:any,operate:Oprate):Promise<OperateResult>=> {
    return new Promise((resolve, reject) => {
        socket.send(JSON.stringify(operate))
        let id:string | undefined = operate.signature?.toString()
        // 开启轮询
       let intervalId =   setInterval(() =>{
           let data:OperateResult =   getOperateDataPoolItem(id)
            if (data!=undefined && data != null){
                resolve(data)
                //清除定时
                clearInterval(intervalId)
            }
        },500)
    })
}

export {sendOperate}