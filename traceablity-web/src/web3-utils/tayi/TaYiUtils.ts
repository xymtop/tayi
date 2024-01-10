import {TaYi} from "tayijs/dist/web3/TaYi";
import {layer} from "@layui/layui-vue";


let tayi:TaYi;

const connTaYi = ()=>{
    try {
        tayi =  new TaYi("0x1048f6de58fe9a82d6aa0245f3adbedca08a5fa4","ws://127.0.0.1:8081");

    }catch (e) {
        layer.msg("连接节点失败",{icon:2})
    }
 
}

const getTaYi = ()=>{
    if (tayi == null){
        connTaYi();
    }
    return tayi;
}


export {getTaYi}