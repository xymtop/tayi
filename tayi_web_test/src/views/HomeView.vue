<template>
  <div class="home">
      <textarea v-model="text" style="width: 200px;height: 400px"></textarea>
    <button @click="send">发送</button>
  </div>
</template>

<script lang="ts" setup>
import {newTaYi} from "tayijs/dist/web3/TaYi";
import {ref} from "vue";
import {buildOperateNormal} from "tayijs/dist/web3/oprate/Oprate";
import {User} from "tayijs/dist/web3/user/user";

let text = ref("")

const send = async ()=>{
  // let data = await tayi.call("bde67d5f2b8e6729b92e038644e66e82493c930af61ed9aba78affcb3f8b148d","getObj")
  // let data = await tayi.sendQuery("getNonce",[tayi.user.address])


  // let user = new User("0x57146332153008169404945773936")
  // console.log(JSON.stringify([1,3,"1",user]))
  let id = await tayi.deploy("QmeoARi7QMKngG98MaZAgQWtcCcUN6vfZNzj9NqmsJU87b")
  let data =await tayi.call(id,"addUser",[tayi.user])
  console.log(data)
}

const tayi = newTaYi("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEK7TOwzqbriKma1KgulnG1zjTZNIXaPMtcdZyR8aup8ySUNGfscgecCHxh","ws://127.0.0.1:8081")
// let data = tayi.buildOperate("EXEC","executeContract",{
//   "id": "bde67d5f2b8e6729b92e038644e66e82493c930af61ed9aba78affcb3f8b148d",
//   "method": "getName"
// })


</script>
