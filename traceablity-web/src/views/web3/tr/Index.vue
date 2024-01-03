<template>
  <div class="nft-container">
    <lay-row :gutter="24">
      <lay-col :xs="24" :sm="12" :md="8" :lg="6" v-for="nft in nftList" :key="nft.id" @click="toDetail(nft.id)">
        <lay-card class="nft-card" style="margin-left: 1%;margin-top: 1%;margin-right: 2%">
          <div class="nft-image" :style="{ backgroundImage: 'url(' + nft.image + ')' }"></div>
          <div class="nft-body">
           <lay-card>
             <h3 class="nft-title">{{ nft.name }}</h3>
             <p class="nft-description">{{ nft.description }}</p>
<!--             <p class="nft-description">{{ nft.address }}</p>-->
           </lay-card>
          </div>
        </lay-card>
      </lay-col>
    </lay-row>
  </div>
</template>




<script setup>
import {ref} from "vue";
import {getAllTokens, tokensOfOwner} from "@/web3-utils/contracts/contracts-api/trNFT";
import router from "@/router";

let nftList = ref([])

const  getNfts = async () => {
  nftList.value =  await getAllTokens()
}
const toDetail = (id) => {
  //储存id为proId
  localStorage.setItem("proId",id)
  router.push({path: '/web3/tr/displayPro'})
}

getNfts()
</script>

<style scoped>
.nft-container {
  padding: 30px;
  background-color: #f9f9f9;
}

.nft-card {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.12);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.nft-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.18);
}

.nft-image {
  height: 220px;
  background-size: cover;
  background-position: center;
}

.nft-body {
  padding: 20px;
  background: white;
  text-align: center; /* 居中文本 */
}

.nft-title {
  margin-top: 0;
  color: #333;
  font-size: 18px; /* 增大标题字号 */
}

.nft-description {
  color: #666;
  font-size: 14px;
  margin-bottom: 0;
}
</style>