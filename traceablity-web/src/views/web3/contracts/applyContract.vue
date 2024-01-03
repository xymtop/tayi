<template>
  <div>
    <lay-card style="margin-left: 2%;margin-right: 2%;margin-top: 2%">
      <lay-container :fluid="true" style="padding: 10px; padding-top: 0px">
        <lay-card style="padding: 40px">
          <lay-step :active="active" current-status="primary" center>
            <lay-step-item title="第一步" content="填写基础信息">
              <template #pace>
                <lay-icon type="layui-icon-ok"></lay-icon>
              </template>
            </lay-step-item>
            <lay-step-item title="第二步" content="上传项目资料"></lay-step-item>
            <lay-step-item title="第三步" content="构建提案"></lay-step-item>
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
                <lay-form-item label="合约logo" prop="img" required>
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
                <lay-form-item  label="合约名称" prop="name" required>
                  <lay-input
                      :allow-clear="true"
                      v-model="formValue.name"
                  ></lay-input>
                </lay-form-item>
                <lay-form-item  label="合约地址" prop="address" required>
                  <lay-input
                      :allow-clear="true"
                      v-model="formValue.address"
                  ></lay-input>
                </lay-form-item>
                <lay-form-item  label="合约描述" prop="description" required>
                  <lay-textarea
                      :allow-clear="true"
                      v-model="formValue.description"
                  ></lay-textarea>
                </lay-form-item>
              </div>


              <div  v-show="active==2">
                <lay-card style="margin-top: 2%">
                  <lay-quote>请点击[构建提案]进入构建提案页面</lay-quote>
                </lay-card>
              </div>
            </lay-form>


            <div v-show="active == 1">
              <lay-upload
                  :disabledPreview="false"
                  :drag="true"
                  :auto=false
                   @change="uploadDoc"
                  :multiple="true"
              ></lay-upload>
                <lay-quote>已上传文件数:{{files.length}}</lay-quote>
            </div>

            <lay-layer v-model="isAddPro" title="构建提案" :area="['500px', '550px']">
              <v-md-editor v-model="text"  height="400px" @save="saveContent"></v-md-editor>
            </lay-layer>
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
              <lay-button size="sm"  v-show="active == 2" @click="buildP" >
                构建提案
              </lay-button>
              <lay-button size="sm" v-show="active > 0" @click="previous">
                上一步
              </lay-button>

              <lay-button
                  type="primary"
                  size="sm"
                  @click="applyContract"
                  v-if="active==2"
                  :style="{
                marginRight: '15px',
              }"
              >
                发布
              </lay-button>
            </lay-row>
          </div>
        </lay-card>
      </lay-container>
    </lay-card>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, reactive } from 'vue'
import { layer } from '@layui/layer-vue'
import hljs from 'highlight.js';
import 'highlight.js/styles/default.css';
import {checkWallet} from "@/web3-utils/wallet-utils";
import {getNFTMetadataURL, uploadNFTMetadataToIPFS} from "@/web3-utils/NFTUtils";
import {mintNFT} from "@/web3-utils/contracts/contracts-api/trNFT";
import {createBlobByString, uploadBlogToIPFS} from "@/web3-utils/ipfsFileUtils";
import router from "@/router";
import {registerContract} from "@/web3-utils/contracts/contracts-api/ContractRegistry";

let text = ref('')
let isAddPro = ref(false)
let files = ref([])

let formValue = reactive({
  name: '',
  description: '',
  address: '',
  image: '',
  attributes: '',
  external_url: '',
})

const buildP = ()=>{
  router.push("/web3/proposalManage/editProposal")
}

//初始化formValue数据
const  initData = async () => {
  formValue.name = ""
  formValue.description = ""
  formValue.address = ""
  formValue.image = ""
  formValue.attributes = ""
  formValue.proposalId=""
}

initData()

const upload = async (e: any) => {
  let blob = new Blob([e.target.files[0]], {type: e.target.files[0].type});
  let url = URL.createObjectURL(blob);
  //上传blob
  const res = await uploadBlogToIPFS(blob)
  formValue.image =getNFTMetadataURL(res)
  console.log(formValue.image)
}

const uploadDoc = async (e:any)=>{
  for (let file of e.target.files) {
    let blob = new Blob([file], {type: file.type});
    let url = URL.createObjectURL(blob);
    //上传blob
    const res = await uploadBlogToIPFS(blob)
    files.value.push(res)
    console.log(res)
  }
}

//控制动态的表单的对象
let inputs = ref([])

let attributesStr = ref("")

const formattedJson =async () =>{
   attributesStr.value =  hljs.highlight(formValue.attributes, { language: 'json' }).value
}

const loading = ref(true)
const active = ref(0)
const next = () => {
  active.value++
}
const previous = () => {
  if (active.value-- === 0) active.value = 0
}

const applyContract = async ()=>{
  //合约地址
  const address = formValue.address
  //合约名称
  const name = formValue.name
  //合约描述
  const description = formValue.description
  //合约logo
  //需要对image进行处理拿到哈希值

  const image = formValue.image.substring(formValue.image.lastIndexOf('/') + 1 )
  //所有文件
  const filesData = []
  filesData.push(image)
  filesData.push(...files.value)

  let res =   await registerContract( address, name, description, filesData)
  console.log(res)
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