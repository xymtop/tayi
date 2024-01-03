//定义溯源流程的信息结构，每个信息都是一个NFT
import {checkWallet} from "./wallet-utils";

export interface TraceAblilityInfo {
    //被溯源的产品的NFT对象
    productNft: TraceAblilityProduct;

    //溯源节点
    traceNodesNft: TraceNodeNft[];

}


// 被溯源的产品的数据结构
export interface TraceAblilityProduct extends Nft {
    //产品的名称
    name: string;

    //产品的描述
    description: string;

    //产品的图片
    image: string;

    //产品的外部链接
    external_url: string;

    //产品的属性
    attributes: string[];
}


// nft元数据结构体
export interface NFTMetadata {
    id?:string,
    address?:string,
    name: string;
    description: string;
    image: string;
    external_url: string;
    attributes: string[];
}


//评论NFT的结构，继承NFT
export interface CommentNft extends Nft {
    data: Comment;
}


//溯源信息的NFT，继承NFT
export interface TraceNodeNft extends Nft {
    data: TraceNode;
}



//溯源信息的结构
export interface TraceNode {
    //证明中心地址
    proofCenterAddress: string;

    // 溯源的信息内容
    content: string;

    //溯源的信息内容的类型
    contentType: TraceNodeContentType;

    //溯源的时间
    time: string;

    //创建人
    creator: string;

}

// TraceNodeContentType
export enum TraceNodeContentType {
    TEXT = "TEXT",
    IMAGE = "IMAGE",
    VIDEO = "VIDEO",
    AUDIO = "AUDIO",
    LINK = "LINK",
    //位置坐标
    LOCATION = "LOCATION",
    OTHER = "OTHER",
}


//定义一个标准NFT的结构
export interface Nft {
    //NFT的id
    id: number;

    //NFT的所有者
    owner: string;

    //颁发的合约地址
    contractAddress: string;

    //NFT的元数据
    metadata: NFTMetadata;
}


//定义评论相关结构
export interface Comment {
    //发送者id
    senderId: string;

    //评论的对象id
    targetId: string;

    //评论内容
    content: string;

    //评论的内容的类型
    contentType: CommentType;

    //评论的时间
    time: string;
}

//评论的内容可能是很多种类型，这里定义一个枚举
export enum CommentType {
    TEXT = "TEXT",
    IMAGE = "IMAGE",
    VIDEO = "VIDEO",
    AUDIO = "AUDIO",
    LINK = "LINK",
    OTHER = "OTHER",
}


//定义合约的结构接口
export interface ContractInfo {
    contractAddress: string;
    name: string;
    description: string;
    owner: string;
    applicationTime: string;
    whitepaperIPFSHashes: string[];
    status: string;
}


// 合约的数据结构，包含评论数组
export interface ContractInfoWithComments extends ContractInfo {
    comments?: Comment[];
}

//构建当前可以构建的溯源节点的基础信息
async function buildNewTraceNodeBaseInfo() {
    let traceNode: TraceNode = {
        proofCenterAddress: "",
        content: "",
        contentType: TraceNodeContentType.TEXT,
        time: Date.now().toString(),
        creator: await checkWallet(),
    }
    return traceNode;
}

//构建一个基础的NFT
async function buildBaseNft() {
    let nft: Nft = {
        id: 0,
        owner: await checkWallet(),
        contractAddress: "",
        metadata: {
            name: "",
            description: "",
            image: "",
            external_url: "",
            attributes: [],
        }
    }
    return nft;
}