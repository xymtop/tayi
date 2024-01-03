
//EXECtype

import ECDSAUtil from "../common/ECDSAUtil";
import {User} from "../user/user";
import {Payload} from "./payload/Payload";

const execType:string[] = [];

class Oprate{
    public address: string | undefined;
    public signature: string | undefined ;
    public data: OperateData|undefined
}

class OperateData{
    operateId: string | undefined;
    operateHash:string|undefined
    operateType: string | undefined;
    operateCmd: string| undefined;
    sender: string| undefined;
    timestamp: number| undefined;
    nonce: number| undefined;
    oprateMeta: OperateMeta | undefined;
}

class OperateMeta{
    payload: Payload | undefined
}

const buildOprate  = function (sender:string){

    let operate =  new Oprate();
    operate.address =  sender;

    return operate
 }


const buildOperateNormal = (user:User,operateType:string,operateCmd:string,payload:Payload)=>{


    let sender:string = user.address

    let operate = buildOprate(sender);

    let meta = buildOperateMeta(payload);

    //data
    let data =  buildOperateDataNomal(sender,operateType,operateCmd,user.nonce,meta)
    operate.data = data;

     //签名
    let  signature =  getSignature(user.privateKey,operate)

    operate.signature = signature;
    operate.data.operateHash = signature



    return operate;
 }


function getSignatureStrData(operate: Oprate) {
    return JSON.stringify(operate.data)
}

function getSignature(privateUser:string|undefined,operate: Oprate) {
   let ecd =   new ECDSAUtil()
    //获取需要签名的字符串数据
   let operateStr =  getSignatureStrData(operate)

    let sign =  ecd.signMessage(operateStr,privateUser)
    return sign
}

const buildOperateDataNomal = (sender:string,operateType:string,operateCmd:string,nonce:number,operateMeta:OperateMeta)=>{
   let operateData =  new OperateData();
   operateData.operateType  = operateType;
   operateData.operateCmd = operateCmd;
   operateData.nonce = nonce;
   operateData.oprateMeta = operateMeta
    operateData.sender = sender

    operateData.timestamp = new Date().getTime();
   return operateData;
 }

 const buildOperateMeta = (payload:Payload)=>{
   let meta = new OperateMeta();
   meta.payload = payload;
   return meta
 }

export {Oprate,buildOprate,buildOperateNormal}
