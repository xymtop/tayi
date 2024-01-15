import { Payload } from "./Payload";
interface ExecPayload extends Payload {
}
interface ExecPayloadArgs extends ExecPayload {
    id: string | undefined;
    method: string | undefined;
    args: string[] | undefined;
}
declare const buildExecPayload: (id: string, method: string, args: string[]) => ExecPayload;
export { ExecPayload, ExecPayloadArgs, buildExecPayload };
