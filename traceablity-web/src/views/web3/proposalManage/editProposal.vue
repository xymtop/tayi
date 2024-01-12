<template>
  <lay-card style="margin-top: 2%">
    <v-md-editor v-model="text"  height="400px" @save="saveContent"></v-md-editor>
  </lay-card>
  <lay-layer v-model="showUserOpt.isShow" :title="showUserOpt.title" :area="['500px', '550px']">
    <div style="padding: 20px">
      <lay-form :model="showUserOpt.selectRow" ref="layFormRef11" >
        <lay-form-item label="提案地址" prop="address">
          <lay-input v-model="showUserOpt.selectRow.descriptionCID" type="text" disabled></lay-input>
        </lay-form-item>
        <lay-form-item label="提案标题" prop="title">
          <lay-input v-model="showUserOpt.selectRow.title" type="text"></lay-input>
        </lay-form-item>
        <lay-form-item label="截止时间" prop="address">
          <lay-date-picker v-model="showUserOpt.selectRow.deadline" type='datetime' timestamp></lay-date-picker>
        </lay-form-item>
      </lay-form>
      <div style="width: 100%; text-align: center">
        <lay-button size="sm" type="primary"
                     @click="save">保存</lay-button
        >
        <!--          <lay-button size="sm">取消</lay-button>-->
      </div>
    </div>
  </lay-layer>
</template>

<script setup>
import {reactive, ref} from "vue";
import {layer} from "@layui/layui-vue";
import {createBlobByString, readBlobAsText, uploadBlogToIPFS} from "@/web3-utils/ipfsFileUtils";
import {createProposal} from "@/web3-utils/contracts/contracts-api/CenterDao";

let text = ref('')

let showUserOpt = reactive({
  isShow:false,
  selectRow:{},
  title:"查看",
  isDisabled:true,
  isNew:false
})

//保存save
const save = async ()=>{
   //新增提案
 const res = await createProposal(showUserOpt.selectRow.title,showUserOpt.selectRow.descriptionCID,showUserOpt.selectRow.deadline)
 if (res){
   layer.msg("成功")
 }else {
   layer.msg("失败")
 }
}

//打开弹窗
const toShow = (item)=>{
  showUserOpt.isShow = true
  showUserOpt.selectRow = item
  showUserOpt.isDisabled = true
  showUserOpt.title = "信息补充"
}

//完成
const saveContent = async (md, html) => {
  const file = createBlobByString(md)
  const res = await uploadBlogToIPFS(file)
  // const  res = "QmPqnT9ceqXPCqfEkv5K1L9oLYUt3RYqHeVokH7NXngtDM";
  toShow({descriptionCID:res})
}
</script>