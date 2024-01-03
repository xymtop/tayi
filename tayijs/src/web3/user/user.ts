
export class User{

     constructor(user: string) {
          this.address = user
     }


     //用户公钥
     public address : string

    //用户私钥
     public privateKey: string = "MIGNAgEAMBAGByqGSM49AgEGBSuBBAAKBHYwdAIBAQQgn3Wv5+HgzT7sxsR7nevMzqH3Y6vMknTH7WeOSu6BLOOgBwYFK4EEAAqhRANCAAQrtM7DOpuuIqZrUqC6WcbXONNk0hdo8y1x1nJHxq6nzJJQ0Z+xyB5wIfGH7eTiwnJ2MzoaV6dz15S0ukHs+lr0"

    //用户交易次数
     public  nonce: number = 0

    //用户余额
     public balance : number = 0

}