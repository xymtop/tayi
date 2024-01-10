<template>
  <lay-container :fluid="true" style="padding: 2px">
    <lay-card>
      <div class="search-div">
        <lay-input style="width: 200px" v-model="searchTitle"></lay-input>
        <lay-button type="primary" style="margin-left: 10px" @click="toSearch"
         >查询</lay-button
        >
        <lay-button @click="toReset">重置</lay-button>
      </div>
    </lay-card>

    <lay-card style="margin-top: 10px; border-radius: 5px">
      <div v-for="(item, index) in allProposals" :key="index">
        <div class="article-item">
          <div class="article-item-content">
            <div style="margin: 30px 0 15px">{{ item.title }}</div>
            <div>

              <lay-tag variant="light" type="normal">{{ item.descriptionCID }}</lay-tag>
              <lay-tag variant="light" type="normal" >{{ formatter.format(new Date(Number(item.deadline))) }}</lay-tag>
              <lay-tag variant="light" type="normal" >{{ item.executed?'已执行':'未执行' }}</lay-tag>
            </div>
            <div style="font-size: 14px; margin: 15px 0">
              {{ item.title }}
            </div>
            <div class="content-userInfo" >
              <lay-avatar
                  src="https://foruda.gitee.com/avatar/1677022544584087390/4835367_jmysy_1578975358.png"
                  radius
                  ></lay-avatar>
              &nbsp;&nbsp;{{ item.creator }}
            </div>
            <div class="content-start">
              <div class="content-start-item borderR">
                <lay-icon type="layui-icon-success"></lay-icon>
                {{ item.positiveVotes }}
              </div>
              <div class="content-start-item borderR">
                <lay-icon type="layui-icon-error"></lay-icon> {{ item.negativeVotes }}
              </div>


            </div>
          </div>
          <div class="article-item-img" @click="toContent(item)">
            <img
                src="https://gw.alipayobjects.com/zos/rmsportal/iXjVmWVHbCJAyqvDxdtx.png"
            />
          </div>
        </div>
      </div>
<!--      <div class="getmore">-->
<!--        <lay-button @click="toGetMore">加载更多</lay-button>-->
<!--      </div>-->
    </lay-card>

  </lay-container>
  <!-- 使用默认插槽自定义 -->
  <lay-tooltip content="发布提案 " position="left">
    <lay-backtop @click="toAddProposal" :showHeight="0" :bottom="160" bgcolor="#5FB878" circle disabled>
      <lay-icon type="layui-icon-release" size="30px"></lay-icon>
    </lay-backtop>
  </lay-tooltip>
</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import { layer } from '@layui/layui-vue'
import {getAllProposals} from "@/web3-utils/contracts/contracts-api/CenterDao";
import router from "@/router";
import {useUserStore} from "@/store/user";
import {searchArrByKey} from "@/web3-utils/searchUtils";
import {CenterDaoType} from "@/web3-utils/tayi/types/CenterDaoType";

const store = useUserStore()

    const page = ref({ total: 100, limit: 10, current: 2 })

    const articleList = ref([
      {
        id: '1',
        title: 'layui-vue',
        content:
            'layui - vue（谐音：类 UI) 是 一 套 Vue 3.0 的 桌 面 端 组 件 库',
        tags: ['layui-vue', 'UI框架', '设计语言'],
        user: '就眠儀式'
      }
    ])

    let searchTitle = ref('')
    async function toSearch() {
      if (searchTitle.value === '') {
        allProposals.value = await getAllProposals()
      } else {
        //编写一个搜索功能，找出所有的title中包含searchTitle的提案
      allProposals.value =   searchArrByKey(allProposals.value, searchTitle.value, ["title"])

      }
    }
    function toReset() {
      searchTitle.value = ''
    }

    //获取所有提案
    let allProposals = ref([])
    async function getAllProposal() {
      allProposals.value = await getAllProposals();
    }

     getAllProposal()


 const  toContent = (item: any)=>{

   // 保存数据
   sessionStorage.setItem('proposalId', item.id);
   router.push('/web3/proposalManage/viewProposal')
 }

const toAddProposal = () => {
  router.push('/web3/proposalManage/editProposal')
}

//日期
const formatter = new Intl.DateTimeFormat('en-US', {
  year: 'numeric',
  month: 'long',
  day: 'numeric',
  hour: 'numeric',
  minute: 'numeric',
  second: 'numeric'
});


</script>
<style scoped>
:deep(.card-list-item .layui-card-body img) {
  width: 100%;
}

:deep(.card-list-item .layui-card-body) {
  padding: 0px !important;
}
</style>
<style lang="less" scoped>
.button-list {
  display: flex;
}

.button-list > div {
  flex: 1;
  text-align: center;
  color: #909399;
}

.search-div {
  width: 100%;
  padding: 10px 0;
  text-align: center;
}

.article-item {
  display: flex;
  width: 100%;
  height: 230px;
  padding: 10px;
  margin-bottom: 10px;
  box-sizing: border-box;
  border-bottom: 1px solid #ebeef5;
}
.article-item-content {
  flex: 1;
  font-size: 18px;
  color: #131313;
}
.article-item-img {
  width: 300px;
  height: 100%;
  padding-right: 20px;
  box-sizing: border-box;
  > img {
    width: 280px;
    height: 190px;
    border-radius: 5px;
  }
  > img:hover {
    cursor: pointer;
    box-shadow: 1px 1px 10px #dfdfdf;
  }
}
.content-tags {
  display: inline-block;
  > .layui-tag {
    margin-right: 10px;
  }
}
.content-userInfo {
  color: #898989;
  font-size: 14px;
}
.content-start {
  width: 160px;
  margin-top: 5px;
  font-size: 12px;
  color: #878787;
  display: flex;
}
.content-start-item {
  flex: 1;
  height: 20px;
  line-height: 20px;
  display: inline-block;
  text-align: center;
}
.borderR {
  border-right: 1px solid #ebeef5;
}
.getmore {
  width: 100%;
  height: 30px;
  margin: 20px auto;
  text-align: center;
}
</style>
