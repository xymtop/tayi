<template>
  <lay-container :fluid="true" style="padding: 2%">
    <lay-row>
      <lay-col :md="24" :sm="24" :xs="24">
        <lay-card class="transaction-card">
          <div class="content-area">
            <h3 class="title">最新交易</h3>
            <p class="info"><strong>哈希:</strong> {{ latestTransaction.hash }}</p>
            <p class="info"><strong>从:</strong> {{ latestTransaction.from }}</p>
            <p class="info"><strong>至:</strong> {{ latestTransaction.to }}</p>
            <p class="info"><strong>金额:</strong> {{ latestTransaction.value }} ETH</p>
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
import { getWeb3 } from "@/web3-utils/web3-init";

const latestTransaction = ref({
  hash: '',
  from: '',
  to: '',
  value: 0
});
const lastUpdated = ref('');

let intervalId = null;

const formatDate = (date) => {
  const options = { hour12: false };
  return new Intl.DateTimeFormat('zh-CN', options).format(date) + '.' + date.getMilliseconds();
};

const updateLatestTransaction = async () => {
  const web3 = await getWeb3();
  const latestBlockNumber = await web3.eth.getBlockNumber();
  const latestBlock = await web3.eth.getBlock(latestBlockNumber);
  if (latestBlock.transactions.length > 0) {
    const txnHash = latestBlock.transactions[latestBlock.transactions.length - 1];
    const txn = await web3.eth.getTransaction(txnHash);
    latestTransaction.value = {
      hash: txn.hash,
      from: txn.from,
      to: txn.to,
      value: web3.utils.fromWei(txn.value, 'ether')
    };
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
  }
};

onMounted(() => {
  updateLatestTransaction();
  intervalId = setInterval(updateLatestTransaction, 2000);
});

onUnmounted(() => {
  if (intervalId) clearInterval(intervalId);
});
</script>

