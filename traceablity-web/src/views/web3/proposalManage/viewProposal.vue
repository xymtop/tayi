<template>
  <lay-layer v-model="isShow" title="提案信息" :area="['500px', '550px']">
<lay-card style="margin-top: 2%;margin-left: 2%;margin-right: 2%">
  <lay-quote>提案描述</lay-quote>
  <lay-form label-width="100px">
    <lay-form-item label="提案标题">
      <lay-input v-model="proposal.title" placeholder="请输入提案标题" disabled></lay-input>
    </lay-form-item>
<!--    <lay-form-item label="提案描述">-->
<!--      <lay-input v-model="proposal.descriptionCID" placeholder="请输入提案描述" disabled></lay-input>-->
<!--    </lay-form-item>-->
<!--    同意数量-->
    <lay-form-item label="同意数量">
      <lay-input v-model="proposal.positiveVotes" placeholder="请输入同意数量" disabled></lay-input>
    </lay-form-item>
<!--    拒绝数量-->
    <lay-form-item label="拒绝数量">
      <lay-input v-model="proposal.negativeVotes" placeholder="请输入拒绝数量" disabled></lay-input>
    </lay-form-item>
<!--      执行状态-->
    <lay-form-item label="执行状态">
      <lay-select  v-model="proposal.executed" disabled>
        <lay-select-option :value=true label="已执行"></lay-select-option>
        <lay-select-option :value=false label="未执行"></lay-select-option>
      </lay-select>
    </lay-form-item>
  </lay-form>
</lay-card>
  </lay-layer>
  <lay-card style="margin-top: 2%;margin-left: 2%;margin-right: 2%">
    <lay-quote>{{ proposal.title }}</lay-quote>
    <v-md-preview :text="text"></v-md-preview>
  </lay-card>

  <!-- 使用默认插槽自定义 -->

  <lay-tooltip content="详细信息 " position="left">
    <lay-backtop @click="info()" :showHeight="0" :bottom="310" bgcolor="#5FB878" circle disabled>
      <lay-icon type=" layui-icon-tips" size="30px"></lay-icon>
    </lay-backtop>
  </lay-tooltip>
  <lay-tooltip content="同意提案 " position="left">
    <lay-backtop @click="agree()" :showHeight="0" :bottom="240" bgcolor="#5FB878" circle disabled>
      <lay-icon type="layui-icon-success" size="30px"></lay-icon>
    </lay-backtop>
  </lay-tooltip>
  <lay-tooltip content="不同意提案 " position="left">
    <lay-backtop @click="refuse()" :showHeight="0" :bottom="170" bgcolor="#c94f4f" circle disabled>
      <lay-icon type="layui-icon-error" size="30px"></lay-icon>
    </lay-backtop>
  </lay-tooltip>

  <lay-tooltip content="执行提案 " position="left" v-show="!proposal.executed">
    <lay-backtop @click="execute()" :showHeight="0" :bottom="100" bgcolor="#599e5e" circle disabled>
      <lay-icon type="layui-icon-engine" size="30px"></lay-icon>
    </lay-backtop>
  </lay-tooltip>
</template>
<script setup lang="ts">
 import {ref} from "vue";
 import router from "@/router";
 import {downloadBlob, getFileFromIPFS, readBlobAsText} from "@/web3-utils/ipfsFileUtils";
 import {executeProposal, getProposal, vote} from "@/web3-utils/contracts/contracts-api/CenterDao";
 import {useUserStore} from "@/store/user";
const store =useUserStore()


  let text = ref('')
 let proposal = ref({})

 const getData = async () => {
   //获取到前一个页面使用router传递过来的id值
   let id = sessionStorage.getItem('proposalId');

//获取详细提案信息
   proposal.value = await getProposal(id)
   if (proposal.value.descriptionCID==null||proposal.value.descriptionCID.length == 0) {
      text.value = "暂无描述"
   } else {
     //获取描述文件
     const blob = await getFileFromIPFS(proposal.value.descriptionCID)
     const res = await readBlobAsText(blob)
     text.value = res
   }
 }

 getData()


 //同意投票
 const agree = async () => {
   //投票同意
   const res = await vote(proposal.value.id, true)
 }
 //拒绝投票
  const refuse = async () => {
    //投票拒绝
    const res = await vote(proposal.value.id, false)
  }

  //执行提案
  const execute = async () => {
    //执行提案
    const res = await executeProposal(proposal.value.id)
  }

 let isShow = ref(false)
  const  info = ()=>{
  isShow.value = true
  }
</script>
<style scoped>

</style>