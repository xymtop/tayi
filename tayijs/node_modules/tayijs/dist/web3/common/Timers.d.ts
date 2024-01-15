/// <reference types="node" />
declare const addInterval: (fun: any, time: number | undefined) => NodeJS.Timeout;
declare const stopExecQuery: (hash: string) => void;
export { addInterval, stopExecQuery };
