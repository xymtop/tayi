

<template>
  <lay-card style="margin-left: 2%;margin-right: 2%;margin-top: 2%">
    <lay-container :fluid="true" style="padding: 10px; padding-top: 0px">
      <lay-card style="padding: 40px">
        <div class="step-form">
          <lay-notice-bar
              leftIcon="layui-icon-help-circle"
              rightIcon="layui-icon-close"
              text="请确认您填写的信息无误,上链后不可修改哦"
              background="#ecf5ff"
          ></lay-notice-bar>
        </div>
        <lay-form style="margin-top: 2%">
          <lay-form-item  label="溯源中心" prop="name" required>
            <lay-autocomplete v-model="tracCenterAddress" :fetchSuggestions="fetchTracCenterSuggestions" placeholder="请输入溯源中心地址"></lay-autocomplete>
          </lay-form-item>
<!--          <lay-form-item  label="产品描述" prop="description" required>-->
<!--            <lay-textarea-->
<!--                :allow-clear="true"-->
<!--            ></lay-textarea>-->
<!--          </lay-form-item>-->
          <lay-form-item  label="溯源产品ID" prop="name" required>
            <lay-input-number v-model="traceProId" size="lg"></lay-input-number>
          </lay-form-item>
          <lay-form-item  label="溯源信息类型" prop="name"  required :disabled="content.length==0">
            <lay-select v-model="traceType" size="lg" @change="updateData">
              <lay-select-option :value="0" label="文本"></lay-select-option>
              <lay-select-option :value="1" label="文件"></lay-select-option>
              <lay-select-option :value="2" label="位置"></lay-select-option>
            </lay-select>
          </lay-form-item>
          <lay-form-item  label="溯源信息" prop="name" required v-show="traceType==0">
            <lay-textarea
                :allow-clear="true"
                v-model="content"
            ></lay-textarea>
          </lay-form-item>
          <lay-form-item label="文件资料" prop="img" v-show="traceType==1" required>
            <lay-upload
                :disabledPreview="true"
                :drag="true"
                :auto=false
                @change="upload"
            ></lay-upload>
          </lay-form-item>
          <lay-form-item label="位置信息" prop="img" v-show="traceType==2" required>
            <lay-input
                :allow-clear="true"
                v-model="content"
                disabled
            ></lay-input>
          </lay-form-item>

<!--          确认按钮-->
          <lay-form-item style="margin-left: 20%">
            <lay-button type="primary" @click="sendData">确认</lay-button>
          </lay-form-item>
        </lay-form>
      </lay-card>
    </lay-container>
  </lay-card>

</template>
<script setup lang="ts">
import {ref} from "vue";
import {addTrac, getAllContractDetails, getAllContracts} from "@/web3-utils/contracts/contracts-api/ContractRegistry";
import {getAddress, getNFTMetadata} from "@/web3-utils/contracts/contracts-api/trNFT";
import {getNFTMetadataURL, mintNFTbyContract, uploadNFTMetadataToIPFS} from "@/web3-utils/NFTUtils";
import {layer} from "@layui/layui-vue";
import {TraceNode} from "@/web3-utils/infoTypes";
import {checkWallet} from "@/web3-utils/wallet-utils";
import {uploadBlogToIPFS} from "@/web3-utils/ipfsFileUtils";

//溯源中心
let tracCenterAddress = ref('')

//所有的溯源中心地址
let tracCenterList = ref([])

//溯源产品ID
let traceProId = ref(0)

let traceType = ref(0)
let content = ref('')
const getMapData = ()=>{
  if ('geolocation' in navigator) {
    navigator.geolocation.getCurrentPosition(
        (pos) => {
          const { latitude, longitude } = pos.coords;
          content.value =JSON.stringify({latitude,longitude})
           layer.msg("获取位置成功")
        },
        (err) => {
          console.error('Error getting location:', err);
          traceType.value = 0
          layer.msg("获取位置失败")
        }
    );
  }
}
const upload = async (e: any) => {
  let blob = new Blob([e.target.files[0]], {type: e.target.files[0].type});
  let url = URL.createObjectURL(blob);

  //上传blob
  const res = await uploadBlogToIPFS(blob)
  content.value = getNFTMetadataURL(res)
  layer.msg("上传成功")
}
const updateData = ()=>{
  content.value = ''
  if (traceType.value==2){
      getMapData()
  }
}
const sendData = async () => {

  //构建溯源信息
  let tracData : TraceNode= {
      proofCenterAddress: tracCenterAddress.value,
      content:content.value,
      contentType:traceType.value,
      time: new Date().getTime().toString(),
      creator:await checkWallet()
  }
  let tracDataStr = JSON.stringify(tracData)

  //通过id获取溯源的产品的地址
  let proAddress = await getAddress(traceProId.value)

  //获取到当前产品的信息
  let proMetaData = await getNFTMetadata(traceProId.value)

  //构建一个MetaData
  let metaData = {
    "name": proMetaData.name,
    "description": proMetaData.name+"溯源信息",
    "image": proMetaData.image,
    "external_url": proMetaData.external_url,
    "traceType": traceType.value
  }
  //上传metaData到IPFS
  let metaDataUrl = await uploadNFTMetadataToIPFS(metaData.name,metaData.description,proMetaData.image,"xymtop.com",[tracDataStr])

  if (proAddress == null || proAddress == undefined || proAddress == '') {
    layer.msg('溯源产品ID不存在');
    return
  }
  //调用mintNFTbyContract方法
  let result = await mintNFTbyContract(tracCenterAddress.value,proAddress,metaDataUrl)
  let resData = await addTrac(proAddress,tracCenterAddress.value)
}

//获取所有的溯源中心地址
const getTracCenterAddressList = async () => {
  tracCenterList.value =   await getAllContractDetails()
  console.log(tracCenterList.value)
}
getTracCenterAddressList()

const fetchTracCenterSuggestions = ()=>{
  return searchTracCenter(tracCenterAddress.value);
}

const searchTracCenter =async (query: string)=>{
  let tempAdds =  tracCenterList.value.filter((item: string)=>{
    return item.name.indexOf(query) > -1||item.description.indexOf(query) > -1||item.contractAddress.indexOf(query) > -1;
  });
  let result = tempAdds.map((item: string)=>{
    return {
      value: item.contractAddress,
    }
  });
  return result;
};
</script>
<style scoped>

</style>