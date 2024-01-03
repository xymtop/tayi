//通过链接读取json文件并返回
import {getFileFromIPFS} from "./ipfsFileUtils";

export async function getJsonByLink(link: string) {
  const response = await fetch(link);
  const json = await response.json();
  return json;
}

//解析该字符串获取哈希值https://cloudflare-ipfs.com/ipfs/QmcBhmD1Rt5G8rvWadsQNRqb8821DE2mA7KeUbd5fXeZcS
export function getHashFromIPFSLink(link: string) {
  const hash = link.split('/').pop();
  return hash;
}

//通过链接从ipfs获取文件
export async function getFileFromIPFSByLink(link: string) {
  const hash = getHashFromIPFSLink(link);
  const file = await getFileFromIPFS(hash);
  return file;
}

//从获取到的文件中解析出json
export async function getJsonFromIPFSByLink(link: string) {
  const file = await getFileFromIPFSByLink(link);
  const json = await file.text();
  return json;
}
