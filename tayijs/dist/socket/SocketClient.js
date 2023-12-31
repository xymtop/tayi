import io from 'socket.io-client';
//socket不能为空
let socket = io("ws://127.0.0.1:8081/rpc");
const newSocket = (ip) => {
    socket = io(ip);
    return socket;
};
const connect = () => {
    socket.connect();
};
const disconnect = () => {
    if (socket.connected) {
        socket.disconnect();
    }
};
const onEvent = (eventName, callback) => {
    socket.on(eventName, callback);
};
const emitEvent = (eventName, data) => {
    socket.emit(eventName, data);
};
export { newSocket, connect, disconnect, onEvent, emitEvent };
