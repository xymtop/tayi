<template>
  <lay-container :fluid="true" style="padding: 2%">
    <lay-row>
      <lay-col :md="24" :sm="24" :xs="24">
        <lay-card class="transaction-card">
          <div class="content-area">
            <h3 class="title">最新区块</h3>
            <p class="info"><strong>哈希:</strong> {{ latestTransaction.hash }}</p>
            <p class="info"><strong>区块高度:</strong> {{ latestTransaction.blockNumber }}</p>
            <p class="info"><strong>区块大小:</strong> {{ latestTransaction.blockSize }}</p>
            <p class="info"><strong>附言:</strong> {{ latestTransaction.extraData }} </p>
            <p class="info"><strong>最后更新时间:</strong> {{ lastUpdated }}</p>
          </div>
        </lay-card>
      </lay-col>
    </lay-row>
  </lay-container>
</template>


<style scoped>
.transaction-card {
  max-width: 600px;
  margin: 20px auto;
  background-color: #1e1e1e; /* 深色背景 */
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  color: #fff; /* 亮色文本 */
}

.title {
  text-align: center;
  color: #4caf50; /* 亮绿色标题 */
  margin-bottom: 15px;
}

.info {
  font-size: 16px;
  padding: 5px 20px;
  border-bottom: 1px solid #333;
  word-wrap: break-word; /* 允许单词在必要时换行 */
  white-space: pre-wrap; /* 保留空白符，允许自然换行 */
}

.info:last-child {
  border-bottom: none;
}

.content-area {
  padding: 20px;
}
</style>


<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import {getTaYi} from "@/web3-utils/tayi/TaYiUtils";

const latestTransaction = ref({
  hash: '',
  blockNumber: '',
  blockSize: '',
  extraData: ""
});
const lastUpdated = ref('');

let intervalId = null;

const formatDate = (date) => {
  const options = { hour12: false };
  return new Intl.DateTimeFormat('zh-CN', options).format(date) + '.' + date.getMilliseconds();
};

const updateLatestTransaction = async () => {


  let tayi = getTaYi()

   let res = await tayi.toSendOperate(tayi.buildOperate("QUERY","getBlockHeight"))
   let data = await  tayi.toSendOperate(tayi.buildOperate("QUERY","getLastBlock"))
  latestTransaction.value = data.execResult.result
  // console.log(data.execResult.result)

    const formatDate = (date) => {
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      const hours = date.getHours().toString().padStart(2, '0');
      const minutes = date.getMinutes().toString().padStart(2, '0');
      const seconds = date.getSeconds().toString().padStart(2, '0');
      const milliseconds = date.getMilliseconds().toString().padStart(2, '0');

      return `${year}年${month}月${day}日 ${hours}:${minutes}:${seconds} ${milliseconds}`;
    };

// 使用方式
    lastUpdated.value = formatDate(new Date());

};

onMounted(() => {
  updateLatestTransaction();
  intervalId = setInterval(updateLatestTransaction, 2000);
});

onUnmounted(() => {
  if (intervalId) clearInterval(intervalId);
});
</script>

