<template>
  <lay-layer v-model="isShow" title="合约信息" :area="['500px', '550px']">
    <lay-card style="margin-top: 2%;margin-left: 2%;margin-right: 2%">
      <lay-quote>合约描述</lay-quote>
      <lay-form label-width="100px">
        <lay-form-item label="合约标题">
          <lay-input v-model="contract.name" placeholder="请输入合约标题" disabled></lay-input>
        </lay-form-item>
        <!--    <lay-form-item label="提案描述">-->
        <!--      <lay-input v-model="proposal.descriptionCID" placeholder="请输入提案描述" disabled></lay-input>-->
        <!--    </lay-form-item>-->
        <lay-form-item label="合约地址">
          <lay-input v-model="contract.contractAddress" placeholder="请输入拒绝数量" disabled></lay-input>
        </lay-form-item>

        <lay-form-item label="合约简介">
          <lay-textarea v-model="contract.description" placeholder="请输入同意数量" disabled></lay-textarea>
        </lay-form-item>

        <lay-form-item label="申请状态">
          <lay-select  v-model="contract.status" disabled>
            <lay-select-option  value=0 label="申请中" ></lay-select-option>
            <lay-select-option  value=1 label="已上线"></lay-select-option>
            <lay-select-option  value=2 label="已下线"></lay-select-option>
          </lay-select>
        </lay-form-item>
      </lay-form>
    </lay-card>
  </lay-layer>

  <lay-layer v-model="isChange" title="切换资料" :area="['500px', '550px']">
    <lay-card style="margin-top: 2%;margin-left: 2%;margin-right: 2%">
      <lay-quote>切换资料</lay-quote>
      <lay-form label-width="100px">
        <lay-form-item label="选择资料">
<!--          下拉选择-->
          <lay-select v-model="selectFile" placeholder="请选择资料" style="width: 100%" @change="changeFile">
            <lay-select-option v-for="(item, index) in filesInfo" :key="index" :value="item" :label="item.name+'['+item.size+'bit]'+'['+item.type+']'" ></lay-select-option>
          </lay-select>
        </lay-form-item>
      </lay-form>
    </lay-card>
  </lay-layer>


<div>

  <lay-card style="margin-top: 2%;margin-left: 2%;margin-right: 2%" v-if="viewType=='文本'">
    <lay-quote>{{ contract.name }}</lay-quote>
    <v-md-preview :text="text"></v-md-preview>
  </lay-card>



  <lay-card style="margin-top: 2%;margin-left: 2%;margin-right: 2%;" v-if="viewType=='视频'">
    <lay-quote>{{ contract.name }}</lay-quote>
    <video-player class="vjs-custom-skin" :options="playerOptions"></video-player>
  </lay-card>

  <!--  音频-->
  <lay-card style="margin-top: 2%;margin-left: 2%;margin-right: 2%" v-if="viewType=='音频'">
    <lay-quote>{{ contract.name }}</lay-quote>
    <audio-player
        ref="audioPlayer"
        :audio-list="audioSource.map(elm => elm.url)"
        theme-color="#ff2929"
    />
  </lay-card>

  <!--  图片-->
  <lay-card style="margin-top: 2%;margin-left: 2%;margin-right: 2%" v-if="viewType=='图片'">
    <lay-quote>{{ contract.name }}</lay-quote>
    <v-zoomer style="width: 500px; height: 500px; border: solid 1px silver;">
      <img
          :src=imageUrl
          style="object-fit: contain; width: 100%; height: 100%;"
      >
    </v-zoomer>
  </lay-card>
</div>

<!--  其他-->
  <lay-card style="margin-top: 2%;margin-left: 2%;margin-right: 2%" v-if="viewType=='其他'">
    <lay-quote>{{ contract.name }}预览文件已开始下载，请在电脑上打开</lay-quote>
  </lay-card>
  <!-- 使用默认插槽自定义 -->

  <lay-tooltip content="详细信息 " position="left">
    <lay-backtop @click="info()" :showHeight="0" :bottom="310" bgcolor="#5FB878" circle disabled>
      <lay-icon type=" layui-icon-tips" size="30px"></lay-icon>
    </lay-backtop>
  </lay-tooltip>
  <lay-tooltip content="切换资料 " position="left">
    <lay-backtop @click="change()" :showHeight="0" :bottom="240" bgcolor="#5FB878" circle disabled>
      <lay-icon type="layui-icon-file-b" size="30px"></lay-icon>
    </lay-backtop>
  </lay-tooltip>
</template>
<script setup lang="ts">
import {ref} from "vue";
import router from "@/router";
import {downloadBlob, getFileFromIPFS, getFileInformation, readBlobAsText} from "@/web3-utils/ipfsFileUtils";
import {executeProposal, getProposal, vote} from "@/web3-utils/contracts/contracts-api/CenterDao";
import {useUserStore} from "@/store/user";
import {ContractInfo, getContractInfo} from "@/web3-utils/contracts/contracts-api/ContractRegistry";
const store =useUserStore()
// 预览的类型
let viewType = ref('文本')
//预览配置
let  playerOptions = ref({
  autoplay: true,
  controls: true,
  width: 800,
  height: 450,
  sources: [{
    src: '',
    type: 'video/mp4'
  }]
})

let audioSource =  ref([])
let imageUrl = ref("")



let text = ref('')
let contract:ContractInfo = ref({})
let selectFile = ref({})
const getData = async () => {
  //获取到前一个页面使用router传递过来的id值
  let id = sessionStorage.getItem('contractId');
  //获取到合约信息
  contract = await   getContractInfo(id)
   await getAllFilesInfo()
  //默认查看第0个
  await viewFile(filesInfo.value[0].url, filesInfo.value[0].type)
}

getData()

//获取所有文件的信息
let filesInfo = ref([])
const getAllFilesInfo = async ()=>{
  let files = []
  for (let i = 0; i < contract.whitepaperIPFSHashes.length; i++) {
    let fileInfo = await getFileInformation(contract.whitepaperIPFSHashes[i])
    files.push(fileInfo)
  }
  filesInfo.value = files
}


let isShow = ref(false)
let isChange = ref(false)

const  info = ()=>{
  isShow.value = true
}

const change = async () => {
  await getAllFilesInfo()
  isChange.value = true
}

const changeFile = async (item:any)=>{
  await viewFile(item.url, item.type)
}

// 根据传入的文件类型来判断不同的操作
const  viewFile = async (uri:string,type:any)=>{

  //如果是图片类型，中文
  if(type=="图片"){
    imageUrl.value = uri
  }
  //文本
  if(type=="文本"){
    //先转url为哈希
    let blob = await getFileFromIPFS(uri.substring(uri.lastIndexOf('/') + 1))
    const res = await readBlobAsText(blob)
    text.value = res
  }
  //视频
  if(type=="视频"){
    playerOptions.value.sources[0].src = uri
  }
  //音频
  if(type=="音频"){
    //需要封装为可用数据
    const item = {
      name:uri.substring(uri.lastIndexOf('/') + 1),
      url:uri
    }
    audioSource.value.push(item)
    console.log(audioSource.value)
  }
  //其他
  if(type=="其他"){
    let blob = await getFileFromIPFS(uri)
    let url = URL.createObjectURL(blob)
    window.open(url)
  }
  viewType.value = type
  isChange.value = false

}
</script>
<style scoped>

</style>