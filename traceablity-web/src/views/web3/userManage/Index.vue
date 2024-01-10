<template>
  <lay-card>
    <div class="search-div" style="display: flex;margin-top: 2%;margin-left: 20%">
      <lay-input style="width: 200px" v-model="searchTitle"></lay-input>
      <lay-button type="primary" style="margin-left: 10px" @click="toSearch"
      >查询</lay-button
      >
      <lay-button @click="toReset">重置</lay-button>
    </div>
  </lay-card>
  <div>
    <lay-table
        :page="page"
        :resize="true"
        :height="'100%'"
        :columns="columns"
        :loading="loading"
        :default-toolbar="true"
        :data-source="usersInfo"
        v-model:selected-keys="selectedKeys"
        @change="change"
        @sortChange="sortChange"
        style="margin-left: 2%;margin-right: 2%"
    >
      <template #isBanned="{ row }">
        <lay-switch :model-value="!row.isBanned" @change="changeisBanned($event , row)"></lay-switch>
      </template>
      <template #role="{ row }">
        <lay-input :model-value="checkRole(row.role)" @change="changeStatus($event , row)" disabled ></lay-input>
      </template>
      <template #gender="{ row }">
        <lay-input :model-value="row.gender?'男':'女'" @change="changeStatus($event , row)" disabled>{{row.gender?'男':'女'}}</lay-input>
      </template>

      <template v-slot:toolbar>
        <lay-button size="sm" type="primary" @click="addAlert">新增</lay-button>

      </template>
      <template v-slot:operator="{ row }">
        <lay-button size="xs" type="primary" @click="updateAlert(row)">更新</lay-button>
        <lay-button size="xs" @click="showViewAlert(row)">查看</lay-button>
      </template>
    </lay-table>


    <lay-layer v-model="showUserOpt.isShow" :title="showUserOpt.title" :area="['500px', '550px']">
      <div style="padding: 20px">
        <lay-form :model="showUserOpt.selectRow" ref="layFormRef11" >
          <lay-form-item label="地址" prop="account">
            <lay-input v-model="showUserOpt.selectRow.account" type="text" :disabled="showUserOpt.isDisabled"></lay-input>
          </lay-form-item>
          <lay-form-item label="昵称" prop="nickname">
            <lay-input v-model="showUserOpt.selectRow.nickname" type="text" :disabled="showUserOpt.isDisabled"></lay-input>
          </lay-form-item>
          <lay-form-item label="性别" prop="gender">
            <lay-select v-model="showUserOpt.selectRow.gender" type="text" :disabled="showUserOpt.isDisabled">
              <lay-select-option :value=true label="男"></lay-select-option>
              <lay-select-option :value=false label="女"></lay-select-option>
            </lay-select>
          </lay-form-item>
          <lay-form-item label="邮箱" prop="email">
            <lay-input v-model="showUserOpt.selectRow.email" type="text" :disabled="showUserOpt.isDisabled"></lay-input>
          </lay-form-item>
          <lay-form-item label="电话" prop="phone">
            <lay-input v-model="showUserOpt.selectRow.phone" type="text" :disabled="showUserOpt.isDisabled"></lay-input>
          </lay-form-item>
          <lay-form-item label="简介" prop="bio">
            <lay-textarea placeholder="请输入简介" v-model="showUserOpt.selectRow.bio" :disabled="showUserOpt.isDisabled"></lay-textarea>
          </lay-form-item>
          <lay-form-item label="权限" prop="role">
            <lay-select  v-model="showUserOpt.selectRow.role" :disabled="showUserOpt.isDisabled">
              <lay-select-option value="0" label="管理员"></lay-select-option>
              <lay-select-option value="1" label="商家"></lay-select-option>
              <lay-select-option value="2" label="普通用户"></lay-select-option>
            </lay-select>
          </lay-form-item>
          <lay-form-item label="状态" prop="isBanned">
            <lay-switch :model-value="!showUserOpt.selectRow.isBanned" :disabled="showUserOpt.isDisabled"></lay-switch>
          </lay-form-item>
        </lay-form>
        <div style="width: 100%; text-align: center">
          <lay-button size="sm" type="primary"
          v-if="!showUserOpt.isDisabled" @click="save">保存</lay-button
          >
<!--          <lay-button size="sm">取消</lay-button>-->
        </div>
      </div>
    </lay-layer>
  </div>
</template>

<script setup>
import { ref, watch, reactive } from 'vue';
import { layer } from '@layui/layui-vue';
import {banUser, getAllUsers, getUserInfo, updateUser} from "@/web3-utils/contracts/contracts-api/UserManager";
import {searchArrByKey} from "@/web3-utils/searchUtils";


    const loading = ref(false);

    const selectedKeys = ref([]);

    const page = reactive({ current: 1, limit: 10, total: 100 });

    const columns = ref([
      { title: '链上地址', width: '60px',key:'account', fixed: 'left' },
      { title: '昵称', width: '80px', key: 'nickname', fixed: 'left', sort: 'desc' },
      { title: '性别', width: '50px', key: 'gender' ,customSlot:'gender'},
      { title: '邮箱', width: '80px', key: 'email', sort: 'desc' },
      { title: '电话', width: '80px', key: 'phone' },
      { title: '简介', width: '120px', key: 'bio' },
      { title: '权限', width: '80px', key: 'role',customSlot:'role' },
      { title: '状态', width: '80px', key: 'isBanned' ,customSlot:'isBanned'},
      { title: '操作', width: '80px', key: 'operator',customSlot:'operator'},
    ]);


    const changeisBanned = async (e, row) => {
      const res = await banUser(row.account)
      if (res){
        layer.msg("成功")
      }else {
        layer.msg("失败")
      }
    }

    const changeStatus = (isChecked, row) => {
      usersInfo.value.forEach((item) => {
        if(item.id === row.id) {
          layer.msg("Success", { icon: 1 }, () => {
            item.status = isChecked;
          })
        }
      })
    }

    const remove = () => {
      layer.msg(selectedKeys.value, { area: '50%'})
    }

    const loadDataSource = (page, pageSize) => {
      var response = [];
      var startIndex = ((page - 1) * pageSize) + 1;
      var endIndex = page * pageSize;
      for (var i = startIndex; i <= endIndex; i++) {
        response.push({
          id:`${i}`,
          age:"18",
          sex: "男",
          name:`张三${i}`,
          email: "test@qq.com",
          remark: '花开堪折直须折,莫待无花空折枝.',
          joinTime: "2022-02-09",
          city: "浙江杭州",
          status: true
        })
      }
      return response;
    }

let  usersInfo = ref([])
const getAllUser = async () => {
  const usersAdd = await getAllUsers()

  for (let user of usersAdd){
    usersInfo.value.push(user)
  }
  // for (let user of usersAdd){
  //   let userInfo =    await getUserInfo(user)
  //   userInfo.address = user
  //   usersInfo.value.push(userInfo)
  //
  // }
}
getAllUser()


const  checkRole = (role)=>{
      if (role=='0'){
        return "管理员"
      }

    if (role=='1'){
      return "商家"
    }
    if (role=='2'){
      return "普通用户"
    }
}

let showUserOpt = reactive({
   isShow:false,
   selectRow:{},
   title:"查看",
   isDisabled:true,
   isNew:false
})
const showViewAlert = (row)=>{
  showUserOpt.isShow = true
  showUserOpt.selectRow = row
}

const  updateAlert = (row)=>{
  showUserOpt.isShow = true
  showUserOpt.selectRow = row
  showUserOpt.title = "更新"
  showUserOpt.isDisabled = false
}


const  addAlert = ()=>{
  showUserOpt.isShow = true
  showUserOpt.selectRow = {}
  showUserOpt.title = "新增"
  showUserOpt.isDisabled = false
}

let searchTitle = ref('')
const  toSearch = async () => {
  if (searchTitle.value === '') {
    usersInfo.value = await getAllUsers()
  }else {
    usersInfo.value = searchArrByKey(usersInfo.value, searchTitle.value, ["nickname", "email", "phone"])
  }

}
const toReset = ()=>{
  searchTitle.value = ''
}
const save = async () => {
  const res = await updateUser(showUserOpt.selectRow.account, showUserOpt.selectRow.nickname, Boolean(showUserOpt.selectRow.gender), showUserOpt.selectRow.email, showUserOpt.selectRow.bio, showUserOpt.selectRow.phone, showUserOpt.selectRow.role, Boolean(showUserOpt.selectRow.isBanned))
  if (res){
    layer.msg("成功")
  }else {
    layer.msg("失败")
  }
    }
</script>