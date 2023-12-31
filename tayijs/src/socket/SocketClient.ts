import { DefaultEventsMap } from '@socket.io/component-emitter';
import io, {Socket} from 'socket.io-client';


//socket不能为空
let socket: Socket<DefaultEventsMap, DefaultEventsMap>  = io("ws://127.0.0.1:8081/rpc")

const newSocket = (ip:string)=>{
   socket  = io(ip);
   return socket
}

const connect = () => {
    socket.connect();
};

const disconnect = () => {
    if (socket.connected) {
        socket.disconnect();
    }
};

const onEvent = (eventName: any, callback: any) => {
    socket.on(eventName, callback);
};

const emitEvent = (eventName: any, data: any) => {
    socket.emit(eventName, data);
};

export  { newSocket,connect, disconnect, onEvent, emitEvent };
