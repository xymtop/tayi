let socket = new WebSocket('ws://127.0.0.1:8081');
const newSocket = (ip) => {
    socket = new WebSocket('ws://127.0.0.1:8081');
    return socket;
};
const sendMsg = (msg) => {
    socket.send(msg);
};
export { newSocket, sendMsg };
