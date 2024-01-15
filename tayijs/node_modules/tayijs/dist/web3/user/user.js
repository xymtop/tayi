export class User {
    constructor(user) {
        //用户私钥
        this.privateKey = "MIGNAgEAMBAGByqGSM49AgEGBSuBBAAKBHYwdAIBAQQgn3Wv5+HgzT7sxsR7nevMzqH3Y6vMknTH7WeOSu6BLOOgBwYFK4EEAAqhRANCAAQrtM7DOpuuIqZrUqC6WcbXONNk0hdo8y1x1nJHxq6nzJJQ0Z+xyB5wIfGH7eTiwnJ2MzoaV6dz15S0ukHs+lr0";
        //用户交易次数
        this.nonce = 0;
        //用户余额
        this.balance = 0;
        this.address = user;
    }
}
