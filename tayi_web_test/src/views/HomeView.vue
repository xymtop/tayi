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
  // let id = await tayi.deploy("QmdVYMYbp3HpVRHkFdjBo4QyGkTGm8hYxi6Lr4Da35h16X")
  // let data =await tayi.call(id,"getUserList")
  // console.log(data)


  // let id = await  tayi.deployAi("QmVVpHn9nJFNh7wHAbcky8chxRXLGyjE1x8mUtYpYtyBSp")

  // console.log(id)

   // let data = await tayi.callAi("QmT3xwZos5QzLTutv4k1b61hcVidD9KcrWzdf36DHW56Tp","QmSSnm1wDbnHyQMEe96Z2AHYrrJA9f6W2y2EgkwgR5MnP6")

  // console.log(data)


  let data = await  tayi.deploy("QmZrgFgw6WyqndkZimusPdP6A8jraFxCNkfHP5k7xdCfyX")
  console.log(await tayi.call(data,"creatNft",
      {
        "address": "0x123456789abcdef",
        "owner": "0xa1b2c3d4e5f67890",
        "meta": {
          "title": "Mystical Artwork",
          "description": "A unique digital art piece representing the fusion of technology and nature.",
          "image": "https://example.com/images/mystical-artwork.jpg",
          "attributes": {
            "name": "hi"
          }
        },
        "resource": "https://example.com/nft/mystical-artwork",
        "time": "2024-01-09T12:00:00Z"
      }

  ))
}

const tayi = newTaYi("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEK7TOwzqbriKma1KgulnG1zjTZNIXaPMtcdZyR8aup8ySUNGfscgecCHxh","ws://127.0.0.1:8081")
// let data = tayi.buildOperate("EXEC","executeContract",{
//   "id": "bde67d5f2b8e6729b92e038644e66e82493c930af61ed9aba78affcb3f8b148d",
//   "method": "getName"
// })


</script>
