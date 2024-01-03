<template>
  <div class="scanner-container">
    <layui-card class="scanner-card">
      <layui-card-header>
        扫码溯源
      </layui-card-header>
      <layui-card-body>
        <div class="scanner-video">
          <video ref="video" autoplay wh-full @play="scanQRCode"></video>
        </div>
      </layui-card-body>
    </layui-card>
    <layui-card class="scanner-card">
      <layui-card-header>
        溯源信息
      </layui-card-header>
      <layui-card-body>
        <div class="scanner-video">
          <div style="width: 100%; height: 210px; text-align: center">
            <img
                :src="tracData.metadata.image"
                alt=""
                style="width: 80px; height: 80px"
            />
            <div
                style="font-size: 20px; font-weight: 500; margin-top: 20px"
            >
              产品信息已上链！
            </div>
            <div style="font-size: 14px; margin-top: 10px">
              创建合约地址: {{tracData.metadata.address}}
            </div>
          </div>
          <div style="height: 20px"></div>
          <div style="font-size: 14px; margin-bottom: 10px">
            产品名称: {{tracData.metadata.name}}
          </div>
          <div style="width: 100%; height: 1px; background: #e8e8e8"></div>
          <div style="height: 20px"></div>
          <div style="font-size: 14px; margin-bottom: 10px">
            产品简介: {{tracData.metadata.description}}
          </div>
          <div style="width: 100%; height: 1px; background: #e8e8e8"></div>
          <div style="font-size: 14px; margin-bottom: 10px">
            溯源描述:
          </div>
          <pre v-html="tracData.metadata.description"></pre>
          <!--            一个代码展示框，需要json高亮-->
          <div style="height: 20px"></div>
          <div style="font-size: 14px; margin-bottom: 10px">
            额外数据:
          </div>
          <pre v-html="tracData.attributes"></pre>
        </div>
      </layui-card-body>
    </layui-card>
    <lay-tooltip content="溯源配置 " position="left">
      <lay-backtop @click="toshowStrInfo" :showHeight="0" :bottom="160" bgcolor="#599e5e" circle disabled>
        <lay-icon type="layui-icon-set" size="15px"></lay-icon>
      </lay-backtop>
    </lay-tooltip>

    <lay-layer v-model="showStrInfo"  title="溯源配置" :area="['500px', '550px']">
      <lay-form style="margin-top: 2%">
        <lay-form-item  label="溯源中心" prop="name" required>
          <lay-autocomplete v-model="triConfig.tracCenterAddress"  placeholder="请输入溯源中心地址"></lay-autocomplete>
        </lay-form-item>
        <lay-form-item  label="溯源信息类型" prop="name"  required>
          <lay-select v-model="triConfig.traceType" size="lg">
            <lay-select-option :value="1" label="图片"></lay-select-option>
            <lay-select-option :value="2" label="位置"></lay-select-option>
          </lay-select>
        </lay-form-item>
        <lay-form-item  label="是否自动确认" prop="name"  required>
          <lay-select v-model="triConfig.auto" size="lg">
            <lay-select-option :value="1" label="是"></lay-select-option>
            <lay-select-option :value="2" label="否"></lay-select-option>
          </lay-select>
        </lay-form-item>
        <lay-form-item  label="自动确认时间" prop="name"  required>
          <lay-input-number v-model="triConfig.waitTime" size="lg"></lay-input-number>
        </lay-form-item>
      </lay-form>
    </lay-layer>

    <lay-layer v-model="showStr"  title="溯源信息" :area="['500px', '550px']">
      <lay-form style="margin-top: 2%">
        <lay-form-item label="位置信息" prop="img" v-show="triConfig.traceType==2" required>
          <lay-input
              :allow-clear="true"
              v-model="content"
              disabled
          ></lay-input>
        </lay-form-item>
        <lay-form-item label="图片信息" prop="img" v-show="triConfig.traceType==1" required>
          <div style="width: 100%; height: 210px; text-align: center">
            <img
                :src="content"
                alt=""
                style="width: 80px; height: 80px"
            />
          </div>
        </lay-form-item>
<!--        一个确定按钮-->
        <lay-form-item style="margin-left: 20%">
          <lay-button type="primary" @click="sendData">确认</lay-button>
        </lay-form-item>
      </lay-form>
    </lay-layer>

  </div>
</template>

<script setup>
import { onMounted, onUnmounted, ref } from 'vue';
import jsQR from 'jsqr';
import NodeInfo from "@/views/web3/utils/NodeInfo.vue";
import {getNFTMetadata} from "@/web3-utils/contracts/contracts-api/trNFT";
import {NFTMetadataToTraceAblilityInfo} from "@/web3-utils/NFTUtils";
import {layer} from "@layui/layui-vue";

const video = ref(null);
const canvasElement = document.createElement('canvas');
const canvas = canvasElement.getContext('2d');
let scanning = ref(true);
const scanResult = ref(null);
const traceInfo = ref({ productName: '', productionDate: '', batchNumber: '' });

const showStr = ref(false);

//模拟可用数据
let tracData = ref({
  metadata: {
    name: "测试产品",
    description: "这是一个测试产品",
    image: "https://img-blog.csdnimg.cn/2021040816104872.png",
    address: "0x1234567890"
  },
  attributes:JSON.stringify( {
    "生产日期": "2021-04-08",
    "生产批次": "20210408",
    "生产厂家": "测试厂家",
  })
})

let triConfig = ref({
  tracCenterAddress: "",
  traceType: 2,
  auto: 1,
  waitTime:1000
})
let content = ref('sasa')

const getTraceAblilityInfo = async ()=>{

}
const getMapData =async ()=>{
  if ('geolocation' in navigator) {
    navigator.geolocation.getCurrentPosition(
        (pos) => {
          const { latitude, longitude } = pos.coords;
          layer.msg("获取位置成功")
          content.value =  JSON.stringify({latitude,longitude})
        },
        (err) => {
          console.error('Error getting location:', err);
          layer.msg("获取位置失败")
        }
    );
  }
}
const sendData = async ()=>{
  showStr.value = false;

  layer.msg("溯源成功")
  // scanning.value = true
}

let showStrInfo = ref(false);
const toshowStrInfo = () => {
  showStrInfo.value = true;
}
const scanQRCode = async () => {
  const scan = async () => {
    if (video.value.readyState === video.value.HAVE_ENOUGH_DATA && scanning.value) {
      canvasElement.height = video.value.videoHeight;
      canvasElement.width = video.value.videoWidth;
      canvas.drawImage(video.value, 0, 0, canvasElement.width, canvasElement.height);
      const imageData = canvas.getImageData(0, 0, canvasElement.width, canvasElement.height);
      const code = jsQR(imageData.data, imageData.width, imageData.height, {
        inversionAttempts: 'dontInvert',
      });

      if (code) {
         await   controllerQR(code.data)
      }
    }

    if (scanning.value) {
      requestAnimationFrame(scan);
    }
  };
    await scan();
};

const controllerQR =async (codeStr)=>{

  console.log(codeStr)
     //关闭扫描
     //  scanning.value = false;
  if (codeStr.length > 0){
    await execData(codeStr)
  }else {
    layer.msg("二维码错误")
  }

}
const execData = async (codeStr) => {
  if (triConfig.value.auto == 1) {
    //自动确认
    await strFun(codeStr)
    setTimeout(async () => {
      await sendData()
    }, triConfig.value.waitTime);
  }else {
     await strFun(codeStr)
  }
}

const strFun = async (codeStr) => {
  if (triConfig.value.traceType == 1) {
    //图片
    content.value = codeStr
  } else if (triConfig.value.traceType == 2) {
    //位置
    await getMapData()
  }
   showStr.value = true;
}


onMounted(() => {
  navigator.mediaDevices.getUserMedia({ video: true })
      .then(stream => {
        video.value.srcObject = stream;
      })
      .catch(error => {
        console.error('无法访问摄像头:', error);
      });
});

onUnmounted(() => {
  scanning.value = false;
  const stream = video.value.srcObject;
  const tracks = stream.getTracks();
  tracks.forEach(track => track.stop());
  video.value.srcObject = null;
});
</script>




<style>

.trace-info-form {
  font-size: 14px;
}

.form-item {
  margin-bottom: 10px;
}

.form-item label {
  font-weight: bold;
}

.floating-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: #1E9FFF;
  color: white;
  padding: 10px;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,0.2);
}

.floating-button i {
  font-size: 24px;
}
.scanner-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-top: 20px;
}

.scanner-card, .sidebar {
  flex-basis: 48%;
}

.scanner-video {
  border: 3px solid #8dc6e5;
  padding: 10px;
}

layui-card-header {
  background-color: #f5f5f5;
  padding: 10px;
  font-weight: bold;
}

layui-card-body {
  padding: 20px;
}

video {
  width: 100%;
  height: auto;
}
</style>
