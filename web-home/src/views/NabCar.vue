<template>
  <lay-menu v-model:selected-key="selectedKey" v-model:open-keys="openKeys1">
    <lay-menu-item>首页</lay-menu-item>

    <lay-sub-menu>
      <template #title>
        区块链
      </template>
      <lay-menu-item>浏览器</lay-menu-item>
      <lay-menu-item>智能合约</lay-menu-item>
      <lay-menu-item>链上AI</lay-menu-item>
    </lay-sub-menu>
    <!--    <lay-menu-item>智能合约</lay-menu-item>-->
    <!--    <lay-menu-item>链上人工智能</lay-menu-item>-->
    <lay-sub-menu>
      <template #title>
        生态
      </template>
      <lay-menu-item>TaYiJs</lay-menu-item>
      <lay-menu-item>TaYiJavaContract</lay-menu-item>
    </lay-sub-menu>
    <lay-sub-menu>
      <template #title>
        论文
      </template>
      <lay-menu-item>测试论文</lay-menu-item>
      <lay-menu-item>测试论文</lay-menu-item>
    </lay-sub-menu>
  </lay-menu>
</template>

<script>
import { ref } from 'vue'

export default {
  setup() {

    const selectedKey = ref("5")
    const openKeys1 = ref(["7"])
    return {
      selectedKey,
      openKeys1
    }
  }
}
</script>