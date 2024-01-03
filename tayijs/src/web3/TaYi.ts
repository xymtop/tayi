
// TaYi对象
import {User} from "./user/user";
import {buildOperateNormal, buildOprate, Oprate} from "./oprate/Oprate";
import {Contract} from "./contract/Contract";
import {Socket} from "socket.io-client";
import {newSocket} from "../socket/SocketClient";
import {Event} from "./events/Event";
import {sendOperate} from "./oprate/OperateSender";
import {Payload} from "./oprate/payload/Payload";
import {OperateResult} from "./oprate/OperateResult";

export class TaYi{

    //连接
   public socket:WebSocket

    //用户
    public user : User


    //操作
    public oprate : Oprate|undefined

    //智能合约
    public contract : Contract|undefined

    //事件
    public  event:Event|undefined

    //初始化
    constructor(user:string,ip:string) {

        //获取连接
       this.socket = newSocket(ip)

        //构建用户

        this.user = new User(user);

       this.oprate = buildOprate(user);

       this.contract = new Contract();

       this.event = new Event();

        if (this.socket != undefined){
            this.socket.addEventListener('open', (event) => {
                console.log('已连接到节点');
            });

            this.socket.addEventListener('message', (event) => {
                // console.log('收到消息:', event.data);
                this.event?.onMessage(event.data)
            });

            this.socket.addEventListener('error', (event) => {
                console.error('WebSocket 错误:', event);
            });
        }

    }



    toSendOperate = (operate:Oprate):Promise<OperateResult>=>{
       return  sendOperate(this.socket,operate)
    }


    //构建无参执行的合约调用
    buildOperateContractExecNoargs =  (contract:string,funName:string)=>{
      return  buildOperateNormal(this.user,"EXEC","executeContract",{
           id:contract,
           method:funName
       })
    }

    buildOperateContractExecArgs = (contract:string,funName:string,args:string[])=>{
        return  buildOperateNormal(this.user,"EXEC","executeContract",{
            id:contract,
            method:funName,
            args:args
        })
    }


    buildOperate = (operateType:string,operateCmd:string,payload:Payload) =>{
       return  buildOperateNormal(this.user,operateType,operateCmd,payload)
    }

    //调用合约
    call = async (contract: string, funName: string, args?: string[]) => {

        let operate = null;

        if (args == undefined) {
            //没有参数
            operate = this.buildOperateContractExecNoargs(contract, funName)
        } else {
            //有参数
            operate = this.buildOperateContractExecArgs(contract, funName, args)
        }

        //发送请求
        let data:OperateResult = await this.toSendOperate(operate)

        return  data.execResult.result
    }


}


let tayi =  null;

const newTaYi = (user:string,ip:string) => {
    tayi =  new TaYi(user,ip)
    return tayi
}


export {newTaYi,tayi}