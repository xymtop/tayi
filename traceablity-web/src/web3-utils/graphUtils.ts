//根据某个字段按顺序排序
import {TraceAblilityInfo, TraceAblilityProduct, TraceNodeNft} from "./infoTypes";
import {getDomCenter, getScreenCenter} from "./screenUtils";

//设置偏移固定值
const offSet = 150

let  root:GNode = {id:"root",label:"开始节点",x:0,y:0};


//屏幕xy
const s = {
    x: window.innerWidth,
    y: window.innerHeight ,
}

export const sortNodes = (nodes: any[], field: string) => {

  return nodes.sort((a, b) => {
      return Number(a[field]) - Number(b[field]);
  });
};

//给出一个父节点和若干个子节点，返回一个树结构
export const getTree = (parent: any, children: any[]) => {
  return {
    parent: parent,
    children: children,
  };
};

// const data = {
//     nodes: [
//         { id: 'root', label: '根节点',    x: w.x, y: w.y },
//         { id: 'child1', label: '子节点1', x: 100, y: 200 },
//         { id: 'child2', label: '子节点2', x: 300, y: 200 },
//         { id: 'child3', label: '子节点3', x: 500, y: 200 },
//     ],
//     edges: [
//         { source: 'root', target: 'child1' },
//         { source: 'root', target: 'child2' },
//         { source: 'root', target: 'child3' },
//     ]
// };




//{ id: 'root', label: '根节点',    x: w.x, y: w.y },
// GNode的结构interface
export interface GNode {
    id: string;
    label: string;
    x: number;
    y: number;
    // 元数据
    metadata?: any;
    children?: GNode[];
}


//  { source: 'root', target: 'child1' },
// GEdge的结构interface
export interface GEdge {
    source: string;
    target: string;
}

//给出图结构定义
export interface GraphData {
    nodes: GNode[];
    edges: GEdge[];
}

//给出上面数据的结构实体
export const getGraphData = (nodes: GNode[], edges: GEdge[]):GraphData => {
  return {
    nodes: nodes,
    edges: edges,
  };
};



//将node.getModel();类型转为GNode类型
export const nodeToGNode = (node: any): GNode => {
    const tempModel = node.getModel();
  return {
    id: tempModel.id,
    label: tempModel.label,
    x: tempModel.x,
    y: tempModel.y,
  };
};

//将edge.getModel();类型转为GEdge类型
export const edgeToGEdge = (edge: any): GEdge => {
    console.log(edge)
  return {
    source: edge.source,
    target: edge.target,
  };
};


//将TraceAblilityInfo类型转为GNode类型，考虑循环
export const traceAblilityInfoToGNode =async (traceAblilityInfo: TraceAblilityInfo) => {

    // console.log("________________")

    let  productNft =    traceAblilityInfo.productNft
    let  traceNodesNft =    traceAblilityInfo.traceNodesNft
    // console.log("________________")
    // console.log(productNft.name)

    root = productNftToGNode(productNft)

    let s = getScreenCenter()
    root.x = s.x-offSet*2
    root.y = s.y-offSet*1.5

    //根据id排序
    traceNodesNft = sortNodes(traceNodesNft,"id")

    //构建一个二维数组，把两个id一样的放在一个维度
    let traceNodesNftArray:TraceNodeNft[][] = []
    for (let i = 0; i < traceNodesNft.length; i++) {
        let tempArr:TraceNodeNft[] = []

        if (traceNodesNft[i+1]!=null&&traceNodesNft[i].id==traceNodesNft[i+1].id){
            tempArr.push(traceNodesNft[i])
            tempArr.push(traceNodesNft[i+1])
            i++
        }else{
            tempArr.push(traceNodesNft[i])
        }
        traceNodesNftArray.push(tempArr)
    }

    let   tempRoot =  traceNodesNftArrayToTraceNodesNft(traceNodesNftArray)

    tempRoot.nodes.push(root)
    tempRoot.edges.push({source:root.id,target:tempRoot.nodes[0].id})


    //设置开始节点信息
    let start = {id:"root",label:"开始溯源点",x:tempRoot.nodes[0].x-offSet,y:tempRoot.nodes[0].y};
    //设置结束节点信息
    let end = {id:"end",label:"当前溯源点",x:tempRoot.nodes[tempRoot.nodes.length-2].x+offSet,y:tempRoot.nodes[tempRoot.nodes.length-2].y};


    //添加开始到第一个的关系
    tempRoot.edges.push({source:start.id,target:tempRoot.nodes[tempRoot.nodes.length-1].id})


    //添加最后一个到结束的关系
    tempRoot.edges.push({source:tempRoot.nodes[tempRoot.nodes.length-2].id,target:end.id})


    //添加到节点
    tempRoot.nodes.push(start)
    tempRoot.nodes.push(end)



    return  tempRoot
};

//通过二维数组构建一个TraceNodeNft数组
export const traceNodesNftArrayToTraceNodesNft = (traceNodesNftArray: TraceNodeNft[][]) :GraphData=> {
    let nodes:GNode[] = []
    let edges:GEdge[] = []


    let s = getScreenCenter()


    //id编号
    let id = 0;
    let tempRoot:GNode = root  ;
    let lastNodes = []
    for (let i = 0; i < traceNodesNftArray.length; i++) {
        for (let j = 0; j < traceNodesNftArray[i].length; j++) {
           let node =  traceNodesNftToGNode(traceNodesNftArray[i][j])
            //设置x，y
            nodes.push(node)

            node.x = tempRoot.x+offSet
            node.y = tempRoot.y+(j+1)*offSet

            //创建关系对
            let  gEdge:GEdge = {
                source:tempRoot.id,
                target:node.id
           }
            edges.push(gEdge)
        }



        lastNodes = traceNodesNftArray[i]
        tempRoot = nodes[i]
    }
    return {
        nodes:nodes,
        edges:edges
    }
};

//通过两个GNode构建新的GNode
export const twoGNodeToGNode = (node1: GNode,node2: GNode) => {
    //前一个为父，后一个为子
    node1.children?.push(node2)
    node2.x = node1.x+60
    node2.y = node1.y+60
};

//通过productNft构建GNode
export const productNftToGNode = (productNft: TraceAblilityProduct): GNode => {
    return {
        id: productNft.id.toString()+"#"+productNft.contractAddress,
        label: productNft.name,
        x: 0,
        y: 0,
        metadata:productNft
    };
};


//traceNodesNft转为Gnode
export const traceNodesNftToGNode = (traceNodesNft: TraceNodeNft): GNode => {
    return {
        id: traceNodesNft.id.toString()+"#"+traceNodesNft.data.proofCenterAddress,
        label: traceNodesNft.data.traceabilityInfo.content,
        x: 0,
        y: 0,
        metadata:traceNodesNft
    };
};