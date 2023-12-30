import io from 'socket.io-client';
//socket不能为空
let socket = io("localhost:6666");
const newSocket = (ip) => {
    socket = io(ip);
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
export default { connect, disconnect, onEvent, emitEvent };
