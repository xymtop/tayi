export class Result{
   public result : boolean
   public messgae: string
   public data:string


    constructor(msgData:any) {
       this.result = msgData.result;
       this.messgae = msgData.messgae;
       this.data = msgData.data;
    }
}