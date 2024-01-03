//EXECtype
import ECDSAUtil from "../common/ECDSAUtil";
const execType = [];
class Oprate {
}
class OperateData {
}
class OperateMeta {
}
const buildOprate = function (sender) {
    let operate = new Oprate();
    operate.address = sender;
    return operate;
};
const buildOperateNormal = (user, operateType, operateCmd, payload) => {
    let sender = user.address;
    let operate = buildOprate(sender);
    let meta = buildOperateMeta(payload);
    //data
    let data = buildOperateDataNomal(sender, operateType, operateCmd, user.nonce, meta);
    operate.data = data;
    //签名
    let signature = getSignature(user.privateKey, operate);
    operate.signature = signature;
    operate.data.operateHash = signature;
    return operate;
};
function getSignatureStrData(operate) {
    return JSON.stringify(operate.data);
}
function getSignature(privateUser, operate) {
    let ecd = new ECDSAUtil();
    //获取需要签名的字符串数据
    let operateStr = getSignatureStrData(operate);
    let sign = ecd.signMessage(operateStr, privateUser);
    return sign;
}
const buildOperateDataNomal = (sender, operateType, operateCmd, nonce, operateMeta) => {
    let operateData = new OperateData();
    operateData.operateType = operateType;
    operateData.operateCmd = operateCmd;
    operateData.nonce = nonce;
    operateData.oprateMeta = operateMeta;
    operateData.sender = sender;
    operateData.timestamp = new Date().getTime();
    return operateData;
};
const buildOperateMeta = (payload) => {
    let meta = new OperateMeta();
    meta.payload = payload;
    return meta;
};
export { Oprate, buildOprate, buildOperateNormal };
