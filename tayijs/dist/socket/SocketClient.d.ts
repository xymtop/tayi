import { DefaultEventsMap } from '@socket.io/component-emitter';
import { Socket } from 'socket.io-client';
declare const newSocket: (ip: string) => Socket<DefaultEventsMap, DefaultEventsMap>;
declare const connect: () => void;
declare const disconnect: () => void;
declare const onEvent: (eventName: any, callback: any) => void;
declare const emitEvent: (eventName: any, data: any) => void;
export { newSocket, connect, disconnect, onEvent, emitEvent };
