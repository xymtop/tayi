<template>
  <lay-card style="margin-top: 2%;margin-left: 2%;margin-right: 2%">
    <div ref="graphContainer" class="graph-container" v-contextmenu:contextmenu></div>
    <lay-tooltip content="溯源基本信息 " position="left">
      <lay-backtop @click="toViewBaseInfo" :showHeight="0" :bottom="160" bgcolor="#599e5e" circle disabled>
        <lay-icon type="layui-icon-tabs" size="15px"></lay-icon>
      </lay-backtop>
    </lay-tooltip>
  </lay-card>
  <!--  弹层，显示节点信息-->
  <lay-layer v-model="showNodeInfoLayer"  title="节点信息" :area="['500px', '550px']">
    <NodeInfo :node-data="selectedNode"></NodeInfo>
  </lay-layer>

<!--  <lay-layer v-model="showEdgeInfoLayer" title="关系信息" :area="['500px', '550px']">-->
<!--    <EdgeInfo :edge-data="selectedEdge"></EdgeInfo>-->
<!--  </lay-layer>-->

  <lay-layer v-model="showBaseInfo" title="溯源基本信息" :area="['500px', '550px']">
 <lay-card>
   <div style="width: 100%; height: 210px; text-align: center">
     <img
         :src="tracData.metadata.image"
         alt=""
         style="width: 80px; height: 80px"
     />
     <div
         style="font-size: 20px; font-weight: 500; margin-top: 20px"
     >
       产品信息已上链！
     </div>
     <div style="font-size: 14px; margin-top: 10px">
       创建合约地址: {{tracData.metadata.address}}
     </div>
   </div>
   <div style="height: 20px"></div>
   <div style="font-size: 14px; margin-bottom: 10px">
     产品名称: {{tracData.metadata.name}}
   </div>
   <div style="width: 100%; height: 1px; background: #e8e8e8"></div>
   <div style="height: 20px"></div>
   <div style="font-size: 14px; margin-bottom: 10px">
     产品简介: {{tracData.metadata.description}}
   </div>
   <div style="width: 100%; height: 1px; background: #e8e8e8"></div>
   <div style="font-size: 14px; margin-bottom: 10px">
     溯源描述:
   </div>
   <pre v-html="tracData.metadata.description"></pre>
   <!--            一个代码展示框，需要json高亮-->
   <div style="height: 20px"></div>
   <div style="font-size: 14px; margin-bottom: 10px">
     额外数据:
   </div>
   <pre v-html="tracData.attributes"></pre>

 </lay-card>
  </lay-layer>
  <v-contextmenu ref="contextmenu">
    <v-contextmenu-item  @click="clickMenu(1)">导出图片</v-contextmenu-item>
    <v-contextmenu-item  @click="clickMenu(2)">导出JSON</v-contextmenu-item>
    <v-contextmenu-item  @click="clickMenu(3)">刷新数据</v-contextmenu-item>
  </v-contextmenu>

</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import G6, {Graph, GraphData, TreeGraphData} from '@antv/g6';
import {getDomCenter, getScreenCenter} from "@/web3-utils/screenUtils";
import NodeInfo from "@/views/web3/utils/NodeInfo.vue";
import {edgeToGEdge, nodeToGNode, traceAblilityInfoToGNode} from "@/web3-utils/graphUtils";
import {mockTraceAblilityInfos} from "@/web3-utils/mock";
import {getProjectData} from "@/web3-utils/displayProUtils";
import {getNFTMetadata} from "@/web3-utils/contracts/contracts-api/trNFT";
import {NFTMetadataToTraceAblilityInfo} from "@/web3-utils/NFTUtils";

const graphContainer = ref(null);
let showNodeInfoLayer = ref(false);
let showEdgeInfoLayer = ref(false);
let showBaseInfo = ref(false);


//已经选择的节点
let selectedNode = ref(null);
//已经选择的边
let selectedEdge = ref(null);
let data: GraphData | TreeGraphData | null | undefined = null;
let graph: Graph | null = null;

let tracData = ref({})

const getTraceAblilityInfo = async ()=>{
  const id = localStorage.getItem("proId")
  let proNftData = await getNFTMetadata(id);
  tracData = await NFTMetadataToTraceAblilityInfo(id,proNftData);
  console.log(tracData)
}
getTraceAblilityInfo()

onMounted(async () => {
   graph = new G6.Graph({
    container: graphContainer.value,
    width: graphContainer.value.offsetWidth || 500,
    height: graphContainer.value.offsetHeight || 500,
    modes: {
      default: ['drag-canvas', 'zoom-canvas', 'drag-node'] // 在默认模式下启用节点拖动
    },
    layout: {
      type: 'dagre',
      rankdir: 'LR', // 设置布局方向为从左到右
      nodesep: 50, // 节点之间的间距
      ranksep: 100, // 每一层之间的间距
    },

    defaultNode: {
      type: 'circle',
      size: [30],
      style: {
        fill: '#009688',
        stroke: '#009688',
        lineWidth: 2
      },
      labelCfg: {
        style: {
          fill: '#000',
          fontSize: 12
        }
      }
    },
    defaultEdge: {
      type: 'trace-line',
      style: {
        stroke: '#a1c05e',
        lineWidth: 2,
        endArrow: {
          path: G6.Arrow.vee(10, 20),
        },
      }
    },
  });

   // 注册边
  G6.registerEdge('customEdge', {
    draw(cfg, group) {
      const { startPoint, endPoint } = cfg;
      const shape = group.addShape('line', {
        attrs: {
          x1: startPoint.x,
          y1: startPoint.y,
          x2: endPoint.x,
          y2: endPoint.y,
          stroke: '#f00',
          lineWidth: 2,
          // 更多自定义样式
        },
        name: 'trace-line',
      });
      return shape;
    },
    // 其他自定义逻辑...
  }, 'single-edge'); // 继承自内置的 'single-edge'


  //获取到储存的id
  const id = localStorage.getItem("proId")

   //获取项目数据
   data = await getProjectData(id)
  graph.data(data);
  graph.render();

  // 节点点击事件处理函数
  graph.on('node:click', (evt) => {
    const node = evt.item;
    selectedNode.value = nodeToGNode(node);
    showNodeInfoLayer.value = true;
  });

  // 边点击事件处理函数
  graph.on('edge:click', (evt) => {
    const edge = evt.item;
    selectedEdge.value = edgeToGEdge(edge.getModel());
    console.log(selectedEdge.value)
    showEdgeInfoLayer.value = true;
  });



  onUnmounted(() => {
    if (graph) {
      graph.destroy();
    }
  });
});

const  clickMenu = async (item: any) => {
  if (item === 1) {
// 获取 Canvas 元素
    const canvas = graph.get('canvas').get('el');

// 将 Canvas 内容转换为数据 URL（PNG 格式）
    const imgData = canvas.toDataURL('image/png');

// 创建一个新的 <a> 元素用于下载
    const downloadLink = document.createElement('a');
    downloadLink.href = imgData;
    downloadLink.download = 'graph.png'; // 指定下载文件名

// 触发下载
    downloadLink.click();
  } else if (item === 2) {
    //下载数据为JSON文件
    const graphData = graph.save(); // 获取G6图的数据
    function replacer(key, value) {
      if (typeof value === 'bigint') {
        return value.toString(); // 或者其他表示形式，如Number(value)等
      } else {
        return value; // 返回非BigInt值不变
      }
    }



    const dataStr = JSON.stringify(graphData, replacer, 2); // 转换为格式化的JSON字符串

// 触发下载JSON文件
    const blob = new Blob([dataStr], { type: "application/json" });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = "graph-data.json";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);

  } else if (item === 3) {
    // 刷新
    const data = await getProjectData(localStorage.getItem("proId"))
    graph.data(data);
    graph.render();
  }
}

const toViewBaseInfo = ()=>{
  showBaseInfo.value = true;
}
</script>

<style scoped>
.graph-container {
  background-color: #f0f2f5;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  width: 100%;
  height: 500px;
}
</style>
