<template>
  <div class="node-info" v-if="nodeData">
    <h3>节点信息({{nodeData.id}})</h3>
    <p><strong>ID:</strong> {{ nodeData.id }}</p>
    <p><strong>标签:</strong> {{ nodeData.label }}</p>
<!--    <div id="offline" style="height: 400px;"></div>-->
<!--    按钮点击跳转到详情页面-->
    <lay-button type="primary" @click="showNodeInfoLayer" v-if="nodeData.id!='start'&&nodeData.id!='end'">查看详情</lay-button>

<!--    弹窗区域-->
<!--    <lay-layer v-model="showMap"  title="节点信息" :area="['500px', '550px']">-->

<!--    </lay-layer>-->

  </div>
</template>

<script>
import router from "@/router";
import {layer} from "@layui/layui-vue";
import {getNFTbyContract, NFTMetadataToTraceNodeNft} from "@/web3-utils/NFTUtils";
import {getFileFromIPFS} from "@/web3-utils/ipfsFileUtils";
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import {ref} from "vue";
export default {
  setup() {
    return {
      showMap: ref(false)
    }
  },
  props: {
    nodeData: {
      type: Object,
      required: true,
      default: () => ({})
    }
  },
  methods: {
     onmounted() {
       console.log("mounted")
     },
     async getNFTInfo(strNodeId) {
//以#分解为编号与合约地址
       let strNodeIdArr = strNodeId.split("#")
       if (strNodeIdArr.length != 2) {
         layer.msg("溯源信息错误")
         return
       }
//获取合约地址
       let contractAddress = strNodeIdArr[1]
//获取编号
       let id = strNodeIdArr[0]
       let meta = await getNFTbyContract(contractAddress, id)
       let  nodeInfo = await NFTMetadataToTraceNodeNft(meta)
       return nodeInfo
     },
    async showNodeInfoLayer() {
      //获取节点信息
      let info = await this.getNFTInfo(this.nodeData.id)
       this.jumpToDetail(info.data.traceabilityInfo.contentType, info.data.traceabilityInfo)
    },
    //传入类型和数据，判断类型，跳转到对应的页面if实现
    jumpToDetail(type, data) {
       console.log(type)
       if (type=='0'){
         layer.confirm(data.content)
       }else if (type=='1'){
         layer.confirm("是否要下载流程附件?",
             {
               btn: [
                 {text:'好的', callback: (id) => {
                     layer.close(id);
                   //下载文件
                  // let blob =   getFileFromIPFS(data.content)
                  //
                     window.open(data.content)
                 }
                 },
                 {text:'取消', callback: (id) => {
                     layer.msg("明白");
                     layer.close(id); }
                 }
               ]
             }
         );
       }else if (type=='2'){
         let pos = JSON.parse(data.content)
         // const map = L.map('offline').setView([pos.latitude, pos.longitude], 13);
         //   L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
         //   maxZoom: 19,
         //   attribution: '© OpenStreetMap contributors'
         // }).addTo(map);

         //构建链接https://www.openstreetmap.org/?mlat=${latitude}&mlon=${longitude}


          let url = `https://www.openstreetmap.org/?mlat=${pos.latitude}&mlon=${pos.longitude}`
          //打开一个窗口
         window.open(url)

       }

    }
  },
}
</script>

<style scoped>
.node-info {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.node-info h3 {
  margin-top: 0;
}

.node-info p {
  margin: 5px 0;
}
</style>
