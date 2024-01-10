import {getTaYi} from "./TaYiUtils";

let  UserContractAddress = "";
const getUser = async ()=>{
   let tayi =    getTaYi()
    UserContractAddress =  await tayi.deploy("QmXzPDop3jtfdXbU7PoCgfPRqvMvt74rSixa1by1U5nGJg")
    return tayi.user
}

const login = async () =>{


   let tayi =  getTaYi()

   // console.log(await   tayi.deploy("QmSGVKqxv32bXWtStasiXpUFCD5PJ8WYwajVA3SjdqWzNV"))

    let user = tayi.user

   let res = await tayi.call(UserContractAddress,"register",{
        "id":user.address
    })

    console.log(res)
}


export  {getUser,login}