<template>
  <div>
    <lay-card style="margin-left: 2%;margin-right: 2%;margin-top: 2%">
    <lay-container :fluid="true" style="padding: 10px; padding-top: 0px">
      <lay-card style="padding: 40px">
        <lay-step :active="active" current-status="primary" center>
          <lay-step-item title="第一步" content="填写产品信息">
            <template #pace>
              <lay-icon type="layui-icon-ok"></lay-icon>
            </template>
          </lay-step-item>
          <lay-step-item title="第二步" content="填写额外信息"></lay-step-item>
          <lay-step-item title="第三步" content="确认信息"></lay-step-item>
        </lay-step>

        <div class="step-form">
          <lay-notice-bar
            leftIcon="layui-icon-help-circle"
            rightIcon="layui-icon-close"
            text="请确认您填写的信息无误,上链后不可修改哦"
            background="#ecf5ff"
          ></lay-notice-bar>
          <div style="height: 20px"></div>


          <lay-form
            :model="formValue"
            label-width="120"
            :pane="active == 1"
          >
            <div v-if="active==0">
<!--            图片拖拽上传框-->
            <lay-form-item label="产品图片" prop="img" required>
              <lay-upload
                  :disabledPreview="true"
                  :drag="true"
                  :auto=false
                  @change="upload"
                  :url="formValue.image"
              ></lay-upload>
<!--              预览图片-->
              <img
                  :src="formValue.image"
                  alt=""
                  style="width: 80px; height: 80px;margin-left: 2%"
                  v-if="formValue.image.length>0"
              />
            </lay-form-item>
            <lay-form-item  label="产品名称" prop="name" required>
              <lay-input
                :allow-clear="true"
                v-model="formValue.name"
              ></lay-input>
            </lay-form-item>
            <lay-form-item  label="产品描述" prop="description" required>
              <lay-textarea
                :allow-clear="true"
                v-model="formValue.description"
              ></lay-textarea>
            </lay-form-item>
          </div>


            <div  v-show="active==1">
                <lay-form-item :label="item.key" prop="value" v-for="(item,index) in inputs">
                  <lay-input v-model="item.value" ></lay-input>
                </lay-form-item>
            </div>
          </lay-form>


          <div v-if="active == 2" class="option-result">
<!-- 一个展示nft信息的卡片-->
             <div style="width: 100%; height: 210px; text-align: center">
                <img
                  :src="formValue.image"
                  alt=""
                  style="width: 80px; height: 80px"
                />
                <div
                  style="font-size: 20px; font-weight: 500; margin-top: 20px"
                >
                  请注意，产品信息上链后将不能修改！
                </div>
                <div style="font-size: 14px; margin-top: 10px">
                  创建者地址: {{formValue.address}}
                </div>
              </div>
              <div style="height: 20px"></div>
              <div style="font-size: 14px; margin-bottom: 10px">
                产品名称: {{formValue.name}}
              </div>
              <div style="width: 100%; height: 1px; background: #e8e8e8"></div>
              <div style="height: 20px"></div>
              <div style="font-size: 14px; margin-bottom: 10px">
                产品简介: {{formValue.description}}
              </div>
              <div style="width: 100%; height: 1px; background: #e8e8e8"></div>
<!--            一个代码展示框，需要json高亮-->
              <div style="height: 20px"></div>
              <div style="font-size: 14px; margin-bottom: 10px">
                额外数据:
              </div>
             <pre v-html="attributesStr"></pre>
              <div style="width: 100%; height: 1px; background: #e8e8e8"></div>
          </div>
          <lay-row style="margin-top: 2%">
            <div style="display: inline-block; width: 120px"></div>
            <lay-button
              type="primary"
              :style="{
                marginRight: '15px',
                marginLeft: active < 1 ? '15px' : '0px'
              }"
              size="sm"
              @click="next"
              v-if="active < 2"
            >
              下一步
            </lay-button>
            <lay-button size="sm"  v-show="active == 1" @click="addInput" >
              添加数据
            </lay-button>
            <lay-button size="sm" v-show="active > 0" @click="previous">
              上一步
            </lay-button>

            <lay-button
                type="primary"
                size="sm"
                @click="addNft"
                v-if="active==2"
                :style="{
                marginRight: '15px',

              }"
            >
              上链
            </lay-button>
          </lay-row>
        </div>
      </lay-card>
    </lay-container>
    </lay-card>
  </div>
  <lay-layer v-model="isShowQrCode" title="溯源二维码" :area="['500px', '550px']">
<!--    居中-->
    <div style="text-align: center">
      <div style="font-size: 20px; font-weight: 500; margin-top: 20px">
        <lay-qrcode :text="qrCodeData" id="myDiv"></lay-qrcode>
<!--        按钮-->
      </div>
      <lay-button type="primary" @click="printQRcode">打印</lay-button>
      <lay-button type="primary" @click="downloadQRCode">下载</lay-button>
    </div>

  </lay-layer>
</template>

<script setup lang="ts">
import { ref, watch, reactive } from 'vue'
import { layer } from '@layui/layer-vue'
import hljs from 'highlight.js';
import 'highlight.js/styles/default.css';
import {checkWallet} from "@/web3-utils/wallet-utils";
import {getNFTMetadataURL, uploadNFTMetadataToIPFS} from "@/web3-utils/NFTUtils";
import {mintNFT} from "@/web3-utils/contracts/contracts-api/trNFT";
import {uploadBlogToIPFS} from "@/web3-utils/ipfsFileUtils";

let formValue = reactive({
  name: '',
  description: '',
  address: '',
  image: '',
  attributes: '',
  external_url: '',
})

// 计算二维码数据

let qrCodeData = ref("")

const printQRcode = () => {
  //打印二维码
  window.print()

}

const downloadQRCode = () => {
  // 获取包含二维码的元素
  const qrcodeEl = document.getElementById('myDiv').querySelector('canvas, img');
  if (!qrcodeEl) {
    console.error('二维码元素未找到');
    return;
  }

  // 创建一个链接元素用于下载
  const link = document.createElement('a');
  link.style.display = 'none';

  // 检查二维码是Canvas还是Image
  if (qrcodeEl.tagName === 'CANVAS') {
    // 如果是Canvas，转换为图片
    link.href = qrcodeEl.toDataURL('image/png');
  } else if (qrcodeEl.tagName === 'IMG') {
    // 如果已经是图片，直接获取src
    link.href = qrcodeEl.src;
  }

  // 设置下载的文件名
  link.download = 'qrcode.png';

  // 触发下载
  document.body.appendChild(link);
  link.click();

  // 清理DOM
  document.body.removeChild(link);
};

const getQrCodeData = (id:string) => {
  // 转base64
  qrCodeData.value = btoa(id)

}

//初始化formValue数据
const  initData = async () => {
  formValue.name = ""
  formValue.description = ""
  formValue.address = await checkWallet();
  formValue.image = ""
  formValue.attributes = ""
}

initData()

const upload = async (e: any) => {
  let blob = new Blob([e.target.files[0]], {type: e.target.files[0].type});
  let url = URL.createObjectURL(blob);
  formValue.image = url


  //上传blob
  const res = await uploadBlogToIPFS(blob)
  formValue.image = getNFTMetadataURL(res)
  console.log(formValue.image)
}


let fileList = ref([])

//控制动态的表单的对象
let inputs = ref([])

let attributesStr = ref("")

// 二维码
let isShowQrCode = ref(false)

const formattedJson =async () =>{
  attributesStr.value =  hljs.highlight(formValue.attributes, { language: 'json' }).value
}

const addNft = async function () {
  //根据formValue的值，调用合约创建nft
  formValue.external_url = "https://www.xymtop.com"
  //分别把formvalue的所有值单独传入函数uploadNFTMetadataToIPFS
  const uri = await uploadNFTMetadataToIPFS(formValue.name,formValue.description,formValue.image,formValue.external_url,formValue.attributes)

  // mintNFT
  let account = await checkWallet()

  const res = await mintNFT(account,uri)


  if (res != null){

    //打开二维码页面
    getQrCodeData(res.id)
    isShowQrCode.value = true
  }

}
const  addInput = ()=>{
   let key = prompt("请输入数据键")
  //key不能为空
  if(key == null || key == ""){
    layer.msg("数据键不能为空")
    return
  }
  inputs.value.push(
      {
        "key":key,
        "value":""
      }
  )
}
const loading = ref(true)
const active = ref(0)
const next = () => {
  if (active.value == 1){
    //第二步
    //将inputs转换为字符串
    formValue.attributes = JSON.stringify(inputs.value)
    formattedJson()
  }
  active.value++
}
const previous = () => {
  if (active.value-- === 0) active.value = 0
}


</script>

<style lang="less" scoped>

.top-title {
  font-size: 20px;
  font-weight: 500;
  margin-top: 12px;
  margin-bottom: 20px;
}

.describe {
  font-size: 14px;
  margin-bottom: 12px;
}
.step-form {
  width: 100%;
  padding: 20px 150px;
  box-sizing: border-box;

  .layui-row:after,
  .layui-row:before {
    display: none;
  }
}
.option-result {
  width: 100%;
  height: 400px;
  font-size: 14px;

  .title {
    height: 40px;
    line-height: 40px;
    padding: 0 10px;
    display: inline-block;
    background: #f7f7f7;
    border-top: 1px solid #e8e8e8;
    border-left: 1px solid #e8e8e8;
  }
  .content {
    height: 40px;
    line-height: 40px;
    padding: 0 3px 0 10px;
    border-top: 1px solid #e8e8e8;
    border-left: 1px solid #e8e8e8;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    text-align: left;
  }
  .borderR {
    border-right: 1px solid #e8e8e8;
  }
  .borderB {
    border-bottom: 1px solid #e8e8e8;
  }
}
</style>