import {Payload} from "./Payload";

interface ExecPayload extends Payload{

}

interface ExecPayloadArgs extends ExecPayload{
    id: string | undefined
    method: string | undefined
    args: string[] | undefined
}


interface ExecPayloadNoArgs extends ExecPayload{
    id: string | undefined
    method: string | undefined
}
const buildExecPayload = (id: string, method: string, args: string[]): ExecPayload => {
    return {id, method, args}
}

export {ExecPayload,ExecPayloadArgs,buildExecPayload}