export declare class Oprate {
    address: string | undefined;
    signature: string | undefined;
    data: {
        operateId: string;
        operateType: string;
        operateCmd: string;
        sender: string;
        timestamp: number;
        nonce: number;
        operateMeta: {
            payload: {
                id: string;
                method: string;
            };
        };
    } | undefined;
}
