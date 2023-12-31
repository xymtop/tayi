// TaYi对象
import { User } from "./user/user";
import { Oprate } from "./oprate/Oprate";
import { Contract } from "./contract/Contract";
import { newSocket } from "../socket/SocketClient";
import { Event } from "./events/Event";
export class TaYi {
    //初始化
    constructor(user, ip) {
        //获取连接
        this.socket = newSocket(ip);
        //构建用户
        this.user = new User(user);
        this.oprate = new Oprate();
        this.contract = new Contract();
        this.event = new Event();
    }
}
