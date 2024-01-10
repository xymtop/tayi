
//定时查询队列
import {Result} from "../common/Result";
import {getResult} from "./EventExec";
import {buildOperateResult, OperateResult} from "../oprate/OperateResult";
import {addOperateDataPoolItem} from "../oprate/OperateDataPool";

export class Event{
    //接收到消息的事件
   public onMessage = (msgData:string) =>{


       //判断是否为json字符串
       if (isJSONString(msgData)){
           let data = JSON.parse(msgData);
           let result = new Result(data);
           if (result != null){
               if (result.result){
                   if (canParasOprateResult(result.data)){
                      let  operateResult = buildOperateResult(result.data);
                      this.OnResult(operateResult)
                   }else {

                       this.onResultHash(result.data)
                   }
               }else {


               }

           }
       }
   }

   //接收到操作执行的哈希值
    public onResultHash = (hash:string)=>{
      // console.log(hash)
    }

    //获取到哈希的结果
    public OnResult = (resultData:OperateResult)=>{

        let hash = resultData.operateHash
        addOperateDataPoolItem(hash,resultData)
    }
}

function isJSONString(str: string) {
    try {
        JSON.parse(str);
        return true;
    } catch (e) {
        return false;
    }
}

function canParasOprateResult(obj:any){
    try {
       // @ts-ignore
        let execResult = buildOperateResult(obj)
        if (execResult.operateHash == null || execResult.operateHash.length == 0){
            return false
        }

        return  true
    }catch (e){
        // console.log(e)
        return false;
    }
}

