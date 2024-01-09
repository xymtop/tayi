<template>
  <div class="editor-container">
    <div class="code-editor">
      <!-- 这里可以放置代码编辑器组件 -->
      <textarea placeholder="在此输入代码..." style="background-color: #393d49;" disabled>

      </textarea>

    </div>
    <div class="code-output">
      <!-- 这里可以显示代码的运行结果 -->
      <p>代码运行结果将在这里显示。</p>

    </div>

    <div class="operate">
      <lay-form :model="model">
        <lay-form-item label="账户" prop="username">
          <lay-input v-model="model.username"></lay-input>
        </lay-form-item>
        <lay-form-item label="密码" prop="password">
          <lay-input v-model="model.password" type="password"></lay-input>
        </lay-form-item>
        <lay-form-item label="描述" prop="desc">
          <lay-textarea placeholder="请输入描述" v-model="model.describe"></lay-textarea>
        </lay-form-item>
        <lay-form-item style="text-align: center;">
          <lay-button type="primary">提交</lay-button>
          <lay-button type="default">重置</lay-button>
        </lay-form-item>
      </lay-form>
    </div>

  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from "vue";

let selectFunction = ref(1);

let model = reactive({})
</script>

<style scoped>
.editor-container {
  display: flex;
  height: 100vh; /* 或根据需要设置高度 */

}

.code-editor, .code-output {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  overflow: auto;
}

.code-editor {
  /* 可以添加更多针对编辑器的样式 */
}

.code-output {
  /* 可以添加更多针对输出结果的样式 */
}
.operate{
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  overflow: auto;
}
textarea {
  width: 100%;
  height: 90%;
}
</style>
