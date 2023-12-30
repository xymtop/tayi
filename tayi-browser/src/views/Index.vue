<template>
  <div class="blockchain-explorer">


    <!-- 内容区域 -->
    <div class="content">
      <!-- 最新区块信息 -->
      <div class="latest-block card-container">
        <h3>最新区块</h3>
        <layui-card>
          <p><strong>区块号:</strong> {{ blockData.blockNumber }}</p>
          <p><strong>哈希值:</strong> {{ blockData.hash }}</p>
          <p><strong>上一个哈希:</strong> {{ blockData.blockHead.previousHash }}</p>
          <p><strong>默克尔根哈希:</strong> {{ blockData.blockHead.merkleRootHash }}</p>
          <p><strong>时间戳:</strong> {{ new Date(Number(blockData.blockHead.timeStamp)).toLocaleString() }}</p>
          <p><strong>目标:</strong> {{ blockData.blockHead.target }}</p>
          <p><strong>Nonce:</strong> {{ blockData.blockHead.nonce }}</p>
          <p><strong>区块大小:</strong> {{ blockData.blockSize }}</p>
          <p><strong>额外数据:</strong> {{ blockData.extraData }}</p>
        </layui-card>
      </div>

      <!-- 最新交易信息 -->
      <div class="latest-transaction card-container">
        <h3>最新交易</h3>
        <layui-card v-for="(transaction, index) in transactions" :key="index">
          <p><strong>操作ID:</strong> {{ transaction.operateId }}</p>
          <p><strong>操作哈希:</strong> {{ transaction.operateHash }}</p>
          <p><strong>操作类型:</strong> {{ transaction.operateType }}</p>
          <p><strong>命令:</strong> {{ transaction.operateCmd }}</p>
          <p><strong>发送者:</strong> {{ transaction.sender }}</p>
          <p><strong>时间戳:</strong> {{ new Date(transaction.timestamp).toLocaleString() }}</p>
          <p><strong>Nonce:</strong> {{ transaction.nonce }}</p>
          <p><strong>执行结果:</strong> {{ transaction.execResult.result }}</p>
        </layui-card>
      </div>
    </div>

    <!-- 版权信息 -->
    <div class="footer">
      <p>© 2023 区块链浏览器</p>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { InputSearch, Card } from 'layui-vue';

const blockData = ref({
  blockNumber: "3387",
  hash: "948183c5fd2544e4efd800957f5f4c1d1d8ff4794c66eac6caf34780aa572576",
  blockHead: {
    version: "1",
    previousHash: "21790e1e74a062c57a31aa739681bcd8a5b6be2b0ae1e5c52bc2eb3839fe7b9a",
    merkleRootHash: "80729e9acdb2a5040ea66bc0c19e43daf13a884706f4f2d2e26c9d19de74b691",
    timeStamp: "1703702366599",
    target: "1",
    nonce: "0"
  },
  operateList: [],
  blockSize: "0",
  extraData: "tayi"
});

const transactions = ref([
  {
    operateId: "3e18d59a104f417fb2518b0703f4295b",
    operateHash: "37337420b829a935999feddcd1b4135d82cbcf8cb2de7e0da4e7d296c0f1e28b",
    operateType: "ADD",
    operateCmd: "executeContract",
    sender: "0x1234abcd5678efgh",
    recipient: null,
    timestamp: 1617625500000,
    nftId: null,
    nonce: 5,
    oprateMeta: {
      sourceObject: null,
      targetObject: null,
      payload: "{\"id\":\"06661bc852345760ce2aa58abf02734a254ddf8cfb132deb527772533a4c3886\",\"method\":\"getHeight\"}"
    },
    operateReceipt: {
      operateHash: null,
      operateIndex: null,
      blockHash: null,
      blockIndex: null,
      sender: null,
      receiver: null,
      status: "SUCCESS"
    },
    execResult: {
      args: [
        "06661bc852345760ce2aa58abf02734a254ddf8cfb132deb527772533a4c3886",
        "getHeight"
      ],
      cmd: "executeContract",
      result: "当前区块高度:3388",
      resultFlag: true
    }
  }
  // 更多交易数据...
]);
</script>

<style scoped>
.blockchain-explorer {
  text-align: center;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #333;
}

.search-container {
  margin: 20px 0;
}

.content {
  display: flex;
  justify-content: space-around;
  padding: 20px;
}

.card-container {
  width: 45%;
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
}

h3 {
  color: #4b4b4b;
}

.footer {
  margin-top: 40px;
  font-size: 0.9em;
  color: #666;
}
</style>
