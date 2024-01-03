<template>
  <lay-container :fluid="true" style="padding: 10px">
    <lay-row :space="10">
      <lay-col :md="24">
        <lay-card>
          <lay-form style="margin-top: 20px">
            <lay-row>
              <lay-col :md="4">
                <lay-form-item :label-width="0">
                  <lay-input
                      v-model="searchTitle"
                      style="width: 100%"
                  ></lay-input>
                </lay-form-item>
              </lay-col>
              <lay-col :md="6">
                <lay-form-item label-width="0">
                  <lay-button
                      type="primary"
                      style="margin-left: 10px"
                      @click="toSearch"
                  >查询</lay-button
                  >
                  <lay-button @click="toReset">重置</lay-button>
                </lay-form-item>
              </lay-col>
            </lay-row>
          </lay-form>
        </lay-card>
      </lay-col>
      <lay-col :md="24">
        <!-- <lay-card> -->
        <lay-row :space="10">
          <lay-col :md="6" v-for="(item, index) in allContract" :key="index">
            <lay-card class="card-list-item" @click="toView(item)">
              <img
                  :src=item.whitepaperIPFSHashes[0]
              />
              <div class="button-list">
                <div class="title">{{ item.name }}</div>
                <div class="content">
                  {{
                    item.description
                  }}
                </div>
                <div class="time-avater">
                  {{ truncateString(item.contractAddress,6) }}
                  <div style="flex: 1; text-align: right">
                    <lay-avatar-list>
                      <lay-tooltip
                          :visible="visible"
                          trigger="click"
                          :content=white
                          v-for="(white, index) in item.whitepaperIPFSHashes"
                      >
                        <lay-avatar :src="white" size="sm" radius></lay-avatar>
                      </lay-tooltip>
                    </lay-avatar-list>
                  </div>
                </div>
              </div>
            </lay-card>
          </lay-col>
        </lay-row>
<!--        <lay-page-->
<!--            v-model="page.current"-->
<!--            :total="page.total"-->
<!--            :limit="page.limit"-->
<!--            :showPage="true"-->
<!--        >-->
<!--        </lay-page>-->
        <!-- </lay-card> -->
      </lay-col>
    </lay-row>
  </lay-container>
  <lay-tooltip content="证明中心申请 " position="left">
    <lay-backtop @click="toApply" :showHeight="0" :bottom="160" bgcolor="#5FB878" circle disabled>
      <lay-icon type="layui-icon-add-circle" size="30px"></lay-icon>
    </lay-backtop>
  </lay-tooltip>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { layer } from '@layui/layer-vue'
import {getAllContractDetails} from "@/web3-utils/contracts/contracts-api/ContractRegistry";
import {truncateString} from "@/web3-utils/strUtils";
import {searchArrByKey} from "@/web3-utils/searchUtils";
import router from "@/router";
    const visible = ref(false)
    const searchTitle = ref('')
    async function toSearch() {
      if (searchTitle.value === '') {
        allContract.value = await getAllContractDetails()
      }else {
        allContract.value = searchArrByKey(allContract.value, searchTitle.value, ['name', 'description'])
      }
    }
    function toReset() {
      searchTitle.value = ''
    }


//获取所有合约

let allContract = ref([])

 const getAllContract = async () => {
    allContract.value = await getAllContractDetails()
   console.log(allContract.value)
  }

  getAllContract()

const toView = (item:any) => {
  // 保存数据
  sessionStorage.setItem('contractId', item.contractAddress);
  router.push("/web3/contracts/viewContract")
}

const toApply = () => {
  router.push("/web3/contracts/applyContract")
}
</script>

<style scoped>
:deep(.card-list-item .layui-card-body img) {
  width: 100%;
}

:deep(.card-list-item .layui-card-body) {
  padding: 0px !important;
}
.layui-card:last-child {
  border-radius: 4px;
}
.button-list {
  height: 140px;
  padding: 10px;
}

.title {
  font-size: 18px;
  color: #000000e0;
  margin: 15px 0px 5px;
}
.content {
  font-size: 14px;
  color: #ccc;
}
.time-avater {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  color: #ccc;
  margin-top: 15px;
}
.card-list-item:hover {
  box-shadow: 2px 2px 14px #ccc;
  cursor: pointer;
}
</style>
