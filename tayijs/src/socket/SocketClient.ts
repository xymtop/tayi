import { DefaultEventsMap } from '@socket.io/component-emitter';
import io, {Socket} from 'socket.io-client';


//socket不能为空
let socket: Socket<DefaultEventsMap, DefaultEventsMap>  = io("localhost:6666")

const newSocket = (ip:string)=>{
  socket  = io(ip);
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

export default { connect, disconnect, onEvent, emitEvent };
