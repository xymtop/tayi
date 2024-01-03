import {TaYi} from "tayijs/dist/web3/TaYi";


let tayi:TaYi;

const connTaYi = ()=>{
    tayi =  new TaYi("0xsasasahkhkahskasjgak","ws://127.0.0.1:8081");
}

const getTaYi = ()=>{
    if (tayi == null){
        connTaYi();
    }
    return tayi;
}
export {getTaYi}