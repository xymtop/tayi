import { User } from "./user/user";
import { Oprate } from "./oprate/Oprate";
import { Contract } from "./contract/Contract";
import { Socket } from "socket.io-client";
import { Event } from "./events/Event";
export declare class TaYi {
    socket: Socket | undefined;
    user: User | undefined;
    oprate: Oprate | undefined;
    contract: Contract | undefined;
    event: Event | undefined;
    constructor(user: string, ip: string);
}
