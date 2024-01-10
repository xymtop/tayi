<template>
  <lay-container :fluid="true" style="padding: 10px">
    <lay-row :span="24" style="display: flex">
      <lay-col style="max-width: 400px" :xs="24">
        <lay-card shadow="hover" class="info-user">
          <div style="text-align: center">
            <lay-avatar :src="src" radius class="user-avatar"></lay-avatar>
            <div class="user-name">{{userModel.nickname}}</div>
            <div class="user-briefing">{{ userModel.bio }}</div>
          </div>

          <lay-row>
            <lay-icon type="layui-icon-username"> </lay-icon
            >&nbsp;&nbsp;&nbsp;{{userModel.gender?'男':'女'}}
          </lay-row>
          <lay-row>
            <lay-icon type="layui-icon-cellphone"> </lay-icon
            >&nbsp;&nbsp;&nbsp;{{userModel.phone}}
          </lay-row>
          <lay-row>
            <lay-icon type="layui-icon-template"> </lay-icon
            >&nbsp;&nbsp;&nbsp;{{userModel.email}}
          </lay-row>

          <lay-line border-style="dashed" border-width="1px"></lay-line>
          <h1 style="margin: 10px 0">标签</h1>
          <template v-for="tag in tagsList" :key="tag.id">
            <lay-tag style="margin: 0 6px 6px 0">
              &nbsp;<lay-badge type="dot" :theme="tag.theme" ripple></lay-badge>
              &nbsp;&nbsp;{{ tag.tag }}
            </lay-tag>
          </template>
        </lay-card>
      </lay-col>
      <lay-col style="flex: 1; background-color: #fff" :xs="24">
        <lay-tab type="brief" v-model="activeTab">
          <lay-tab-item title="基本信息" id="baseInfo">
            <div class="tab-content">
              <lay-form
                :model="userModel"
                ref="layFormRef4"
                label-width="60"
                size="sm"
              >
                <lay-form-item label="昵称" prop="nickname" required>
                  <lay-input v-model="userModel.nickname" allow-clear></lay-input>
                </lay-form-item>
                <lay-form-item label="性别" prop="gender">
                  <lay-select
                    v-model="userModel.gender"
                    style="width: 100%"
                    placeholder="请选择性别"
                  >
                    <lay-select-option
                      :value=true
                      label="男"
                    ></lay-select-option>
                    <lay-select-option
                      :value=false
                      label="女"
                    ></lay-select-option>
                  </lay-select>
                </lay-form-item>
                <lay-form-item label="邮箱" prop="email">
                  <lay-input
                    v-model="userModel.email"
                    type="email"
                    allow-clear
                  ></lay-input>
                </lay-form-item>
                <lay-form-item label="个人简介" prop="bio">
                  <lay-textarea
                    placeholder="还没有个人简介哦~"
                    v-model="userModel.bio"
                    allow-clear
                  ></lay-textarea>
                </lay-form-item>
                <lay-form-item label="联系电话" prop="phone" required>
                  <lay-input
                    v-model="userModel.phone"
                    type="phone"
                    allow-clear
                  ></lay-input>
                </lay-form-item>
                <lay-form-item style="text-align: center">
                  <lay-button type="primary" @click="registUser">提交</lay-button>
                  <lay-button @click="clearValidate4">清除校验</lay-button>
                </lay-form-item>
              </lay-form>
            </div>
          </lay-tab-item>
        </lay-tab>
      </lay-col>
    </lay-row>
  </lay-container>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { layer } from '@layui/layer-vue'
import {getUserInfo, registerUser, updateUser} from "@/web3-utils/contracts/contracts-api/UserManager";
import {checkWallet} from "@/web3-utils/wallet-utils";
import {useUserStore} from "../../../store/user";
import {User} from "@/web3-utils/tayi/types/User";



    const src =
      'https://foruda.gitee.com/avatar/1677022544584087390/4835367_jmysy_1578975358.png'

    const tagsList = ref([
      { id: 1, tag: '雪', theme: 'blue' },
      { id: 2, tag: '风是从哪儿来wu', theme: '' },
      { id: 3, tag: '漫游星河', theme: 'orange' },
      { id: 4, tag: '春日之花', theme: 'green' },
      { id: 5, tag: 'Hey', theme: 'cyan' },
      { id: 6, tag: '风吹一夏', theme: '' }
    ])
    const activeTab = ref('baseInfo')

    let userModel = ref({
      nickname:'',
      gender:false,
      email:'',
      bio:'',
      phone:'',
      role:'',
      isBanned:false
    })

    const layFormRef4 = ref()


const  getData = async function (){
     let data =  await getUserInfo(await  checkWallet())
      if (data!=undefined){
        userModel.value = data
      }

}

getData()



    const registUser = async function () {
      const account = await  checkWallet();
      let info =  await getUserInfo(await checkWallet())
      if (info == undefined){
        userModel.value.isBanned = false
        userModel.value.role = '2'
        const res:User =   await registerUser(userModel.value.nickname,Boolean(userModel.value.gender),userModel.value.email,userModel.value.bio,userModel.value.phone,userModel.value.role,userModel.value.isBanned)
        if (res.account.length>0){
          userModel.value = res
          layer.msg("成功")

        }else {
          layer.msg("失败")
        }

      }else {
        const res:boolean =   await updateUser(account,userModel.value.nickname,Boolean(userModel.value.gender),userModel.value.email,userModel.value.bio,userModel.value.phone,userModel.value.role,userModel.value.isBanned)
        if (res){
          layer.msg("成功")
        }else {
          layer.msg("失败")
        }
      }
      // let user = "";
      // if (!user.hasOwnProperty("nickname")){
      //   userModel.value.isBanned = false
      //   userModel.value.role = '2'
      //   const res =   await registerUser(userModel.value.nickname,Boolean(userModel.value.gender),userModel.value.email,userModel.value.bio,userModel.value.phone,userModel.value.role,userModel.value.isBanned)
      //   console.log(res)
      // }else {
      //   const res =   await updateUser(account,userModel.value.nickname,Boolean(userModel.value.gender),userModel.value.email,userModel.value.bio,userModel.value.phone,userModel.value.role,userModel.value.isBanned)
      //   console.log(res)
      // }


    }

    const clearValidate4 = () => {
      layFormRef4.value.clearValidate()
    }

    const reset4 = () => {
      layFormRef4.value.reset()
    }



</script>

<style scoped>
.layui-tag .layui-tag-text {
  margin: 0 8px 8px 0;
}
.info-user {
  max-width: 400px;
  min-width: 320px;
  padding: 20px;
  margin-right: 10px;
}
.user-avatar {
  width: 40%;
  height: 40%;
}
.user-name {
  font-size: 24px;
  margin: 15px;
}
.user-briefing {
  color: #0006;
  font-size: 14px;
  margin-bottom: 10px;
}

.header-title {
  font-size: 20px;
  font-weight: 500;
  margin-top: 12px;
  margin-bottom: 20px;
}

.header-describe {
  font-size: 14px;
  margin-bottom: 12px;
}

.tab-content {
  height: 368px;
  max-width: 500px;
  padding: 0 20px;
  background-color: #fff;
}
.account-item {
  display: flex;
  width: 99%;
  height: 41px;
  line-height: 43px;
  margin-right: 10px;
  padding: 12px 0;
  border-bottom: #ccc solid 1px;
}
.account-item:last-child {
  border-bottom: none;
}
.account-desc {
  flex: 1;
  color: #0006;
  line-height: 24px;
  display: inline-block;
}
.account-option {
  width: 45px;
  display: inline-block;
  color: var(--global-primary-color);
}
</style>