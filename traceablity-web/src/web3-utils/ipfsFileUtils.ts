// ipfsUpload.js
import { IPFSPath } from 'ipfs-core-types/dist/src/utils';
import {create as ipfsHttpClient} from 'ipfs-http-client';
import axios from "axios";
import {truncateString} from "./strUtils";
import {layer} from "@layui/layui-vue";

// const client = ipfsHttpClient('https://ipfs.io'); // 使用 Infura 节点，你可以根据需要修改
const client = ipfsHttpClient({
    host: 'localhost',
    port: 5001,
    protocol: 'http',
});


//文件类型的枚举
export enum FileType {
    IMAGE = 'image',
    VIDEO = 'video',
    AUDIO = 'audio',
    TEXT = 'text',
    OTHER = 'other',
}

//文件类型枚举对应的文件type的映射
export const FileTypeToMimeTypeMap = {
    [FileType.IMAGE]: 'image/*',
    [FileType.VIDEO]: 'video/*',
    [FileType.AUDIO]: 'audio/*',
    [FileType.TEXT]: 'text/plain',
    [FileType.OTHER]: '*/*',
};


//根据字符串创建文件blob
export function createBlobByString(str: string, fileName:string,type = FileType.TEXT) {
    return new Blob([str], { type: FileTypeToMimeTypeMap[type] });
}
export async function uploadBlogToIPFS(blob: any) {
    try {
        // 使用 IPFS 客户端上传文件
        const added = await client.add(blob);

        // 返回文件的 IPFS 路径哈希
        return added.path;
    } catch (error) {
        console.error('Error uploading to IPFS:', error);
        throw error;
    }
}

export async function getFileFromIPFS(hash: any) {
    try {
        // 获取文件内容
        const chunks = [];
        for await (const chunk of client.cat(hash)) {
            chunks.push(chunk);
        }

        // 将文件内容转换为 Blob
        const blob = new Blob(chunks, { type: 'application/octet-stream' });
        return blob;
    } catch (error) {
        console.error('Error getting file from IPFS:', error);
        throw error;
    }
}

export   function readBlobAsText(blob: Blob) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();

        reader.onload = function(event) {
            resolve(event.target.result);
        };

        reader.onerror = function(error) {
            reject(error);
        };

        reader.readAsText(blob);
    });
}

export  function downloadBlob(blob: Blob | MediaSource, filename = 'file.txt') {
    // 创建一个隐藏的 a 标签
    const a = document.createElement('a');
    document.body.appendChild(a);
    a.style.display = 'none';

    // 使用 Blob 创建一个 URL
    const url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = filename;

    // 触发下载
    a.click();

    // 清理并移除 URL 对象
    window.URL.revokeObjectURL(url);
    document.body.removeChild(a);
}

// 定义文件信息题，包含地址，名称和长度，类型
export interface FileInfo {
    url: string;
    name: string;
    size: number;
    type: string;
}

//文件类型的映射表，映射response.headers['content-type']到文字
export const FileTypeToReadableMap = {
    [FileType.IMAGE]: '图片',
    [FileType.VIDEO]: '视频',
    [FileType.AUDIO]: '音频',
    [FileType.TEXT]: '文本',
    [FileType.OTHER]: '其他',
};

//通过response.headers['content-type']得到中文文件类型
export function getFileTypeReadable(type: string) {
    for (const key in FileTypeToReadableMap) {
       //使用正则来匹配
        if (type.match(FileTypeToMimeTypeMap[key])) {
            return FileTypeToReadableMap[key];
        }
    }
    return FileType.OTHER;
}

export async function getFileInformation(url: string) {
    console.log(url)
    return await axios.head(url)
        .then(response => {
            // console.log("Content-Type:", response.headers['content-type']); // 文件类型
            // console.log("Content-Length:", response.headers['content-length'], "bytes"); // 文件大小（字节）
            //返回文件信息体
            return {
                url,
                name: truncateString(url.substring(url.lastIndexOf('/') + 1),9),
                size: parseInt(response.headers['content-length']),
                type: getFileTypeReadable(response.headers['content-type']),
            };
        })
        .catch((err)=>{
            console.log(url+"不存在")
            layer.msg("获取文件"+url.substring(url.lastIndexOf('/') + 1)+"信息失败！可能IPFS节点暂未同步数据，请稍后再试！")
             return {
                 url,
                 name: "数据获取失败",
                 size: 0,
                 type: FileType.OTHER,
             };
        });
}
