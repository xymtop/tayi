

let socket:WebSocket = new WebSocket('ws://127.0.0.1:8081');

const newSocket = (ip:string)=>{
    socket  = new WebSocket('ws://127.0.0.1:8081');
    return socket
}

const sendMsg = (msg:string)=>{
    socket.send(msg)
}

export  { newSocket,sendMsg};
