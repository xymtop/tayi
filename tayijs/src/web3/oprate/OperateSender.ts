import {Oprate} from "./Oprate";
import {getOperateDataPoolItem} from "./OperateDataPool";
import {OperateResult} from "./OperateResult";

const sendOperate = (socket:WebSocket,operate:Oprate):Promise<OperateResult>=> {
    return new Promise((resolve, reject) => {
        socket.send(JSON.stringify(operate))
        let id:string | undefined = operate.signature?.toString()
        // 开启轮询
       let intervalId =   setInterval(() =>{
           let data:OperateResult =   getOperateDataPoolItem(id)
            if (data!=undefined && data != null){
                if (data.execResult!=null&&!data.execResult["resultFlag"]){
                    console.error(data.execResult)
                }else {
                    resolve(data)
                }
                //清除定时
                clearInterval(intervalId)
            }
        },500)
    })
}

export {sendOperate}