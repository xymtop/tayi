var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
// TaYi对象
import { User } from "./user/user";
import { buildOperateNormal, buildOprate } from "./oprate/Oprate";
import { Contract } from "./contract/Contract";
import { newSocket } from "../socket/SocketClient";
import { Event } from "./events/Event";
import { sendOperate } from "./oprate/OperateSender";
export class TaYi {
    //初始化
    constructor(user, ip) {
        this.toSendOperate = (operate) => {
            return sendOperate(this.socket, operate);
        };
        //构建无参执行的合约调用
        this.buildOperateContractExecNoargs = (contract, funName) => {
            return buildOperateNormal(this.user, "EXEC", "executeContract", {
                id: contract,
                method: funName
            });
        };
        this.buildOperateContractExecArgs = (contract, funName, args) => {
            return buildOperateNormal(this.user, "EXEC", "executeContract", {
                id: contract,
                method: funName,
                args: args
            });
        };
        this.buildOperate = (operateType, operateCmd, payload) => {
            return buildOperateNormal(this.user, operateType, operateCmd, payload);
        };
        //部署合约
        this.deploy = (contract) => __awaiter(this, void 0, void 0, function* () {
            let operate = buildOperateNormal(this.user, "EXEC", "deployContract", contract);
            let data = yield this.toSendOperate(operate);
            return data.execResult.result;
        });
        //调用合约
        this.call = (contract, funName, args) => __awaiter(this, void 0, void 0, function* () {
            let operate = null;
            if (args == undefined) {
                //没有参数
                operate = this.buildOperateContractExecNoargs(contract, funName);
            }
            else {
                //有参数
                operate = this.buildOperateContractExecArgs(contract, funName, args);
            }
            //发送请求
            let data = yield this.toSendOperate(operate);
            console.log(data.execResult);
            return data.execResult.result;
        });
        this.buildQueryOperate = (cmd, args) => {
            return buildOperateNormal(this.user, "QUERY", cmd, {
                args: args
            });
        };
        //发送查询
        this.sendQuery = (cmd, args) => __awaiter(this, void 0, void 0, function* () {
            let operate = this.buildQueryOperate(cmd, args);
            let data = yield this.toSendOperate(operate);
            return data.execResult.result;
        });
        //部署ai
        this.deployAi = (aiHash) => __awaiter(this, void 0, void 0, function* () {
            let operate = buildOperateNormal(this.user, "EXEC", "deployAI", {
                aiHash: aiHash
            });
            let data = yield this.toSendOperate(operate);
            return data.execResult.result;
        });
        //调用ai
        this.callAi = (aiHash, aiInput) => __awaiter(this, void 0, void 0, function* () {
            let operate = buildOperateNormal(this.user, "EXEC", "callAI", {
                aiHash: aiHash,
                aiInput: aiInput
            });
            let data = yield this.toSendOperate(operate);
            return data.execResult.result;
        });
        //训练ai
        this.trainAi = (aiHash, trainDataAddress) => __awaiter(this, void 0, void 0, function* () {
            let operate = buildOperateNormal(this.user, "EXEC", "trainAi", {
                aiHash: aiHash,
                trainDataAddress: trainDataAddress
            });
            let data = yield this.toSendOperate(operate);
            return data.execResult.result;
        });
        //获取连接
        this.socket = newSocket(ip);
        //构建用户
        this.user = new User(user);
        this.oprate = buildOprate(user);
        this.contract = new Contract();
        this.event = new Event();
        if (this.socket != undefined) {
            this.socket.addEventListener('open', (event) => {
                console.log('已连接到节点');
            });
            this.socket.addEventListener('message', (event) => {
                var _a;
                // console.log('收到消息:', event.data);
                (_a = this.event) === null || _a === void 0 ? void 0 : _a.onMessage(event.data);
            });
            this.socket.addEventListener('error', (event) => {
                console.log('WebSocket 错误:', event);
            });
            this.socket.addEventListener('close', (event) => {
                console.log('WebSocket 连接已关闭:', event);
            });
        }
    }
}
let tayi = null;
const newTaYi = (user, ip) => {
    if (tayi == null) {
        tayi = new TaYi(user, ip);
    }
    return tayi;
};
export { newTaYi, tayi };
