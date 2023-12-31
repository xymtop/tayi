
export class User{

     constructor(user: string) {
          this.address = user
     }


     //用户公钥
     public address : string | undefined

    //用户私钥
     public privateKey: string | undefined

    //用户交易次数
     public  nonce: string | undefined

    //用户余额
     public balance : string | undefined

}