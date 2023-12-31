export declare class Contract {
    deploy: () => string;
    execute: (args: string[]) => string;
    call: () => string;
}
