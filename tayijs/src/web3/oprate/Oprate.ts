 class Oprate{
    public address: string | undefined;
    public signature: string | undefined;
    public data: {
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


const buildOprate  = function (){
    return new Oprate();
 }

export {Oprate,buildOprate}
