
// TaYi对象
import {User} from "./user/user";
import {buildOprate, Oprate} from "./oprate/Oprate";
import {Contract} from "./contract/Contract";
import {Socket} from "socket.io-client";
import {newSocket} from "../socket/SocketClient";
import {Event} from "./events/Event";

export class TaYi{

    //连接
   public socket : Socket|undefined

    //用户
    public user : User|undefined


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

       this.oprate = buildOprate();

       this.contract = new Contract();

       this.event = new Event();
    }

}