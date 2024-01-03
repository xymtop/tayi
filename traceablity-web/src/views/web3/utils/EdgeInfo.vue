<template>
  <div class="node-info">
    <h3>关系信息({{edgeData}})</h3>
<!--    <p><strong>源节点ID:</strong> {{ edgeData.value.source }}</p>-->
<!--    <p><strong>目标节点ID:</strong> {{ edgeData.value.target }}</p>-->
    <!-- 可以根据需要添加更多节点信息 -->
  </div>
</template>

<script>
export default {
  props: {
    edgeData: {
      type: Object,
      required: false,
      default: () => ({})
    }
  }
}
</script>

<style scoped>
.node-info {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.node-info h3 {
  margin-top: 0;
}

.node-info p {
  margin: 5px 0;
}
</style>
