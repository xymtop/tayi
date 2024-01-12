# TaYi

## 介绍
TaYi Chain,基于NFT数据关系以及所有权转移和查询的区块链


### 如何引入
> Maven引入


```shell
<dependency>
  <groupId>com.xymtop</groupId>
  <artifactId>tayi</artifactId>
  <version>0.0.3</version>
</dependency>
```

> Gradle引入

```shell
dependencies {
  compile 'com.xymtop:tayi:0.0.3'
}
```

### 智能合约实例
```java
//为了实现自己的合约内容并且调用，你只需要7步
/*
* 1. 继承TaYiJavaContract
* 2.实现Serializable接口,方便序列化
* 3.实现info方法，注册合约信息
* 4.编写自己的方法
* 5.打包为jar包并且上传到IPFS
* 6.在TaYi中部署合约
* 7.调用智能合约方法
* */

@Data
public class Application extends TaYiJavaContract implements Serializable {

    private int count;

    private List<Cat> cats = new ArrayList<>();

    public boolean addCat(){
        Cat cat = new Cat();
        cat.setName("老王");
        cats.add(cat);
        return true;
    }


    public int getName(){
        return count;
    }

    public void  addName(){
         count++;
    }

    @Override
    public ContractInfo info() {
        ContractInfo info = new ContractInfo();
        info.setName("hello,TaYi!");
        return info;
    }


    //获取单属性
    public String getHeight() throws Exception {
        That that = getThat();
        BlockUtils blockUtils = that.getBlockUtils();
        long chainBlockHeight = blockUtils.getChainBlockHeight();
        return "当前区块高度:" + chainBlockHeight;
    }

    //获取对象
    public  Object getObj() throws Exception {
        //执行其他
        NFTMeta nftMeta = new NFTMeta();

        nftMeta.setTitle("TaYi");
        nftMeta.setDescription("美好的生活从现在开始!");
        nftMeta.setImage("https://upload.wikimedia.org/wikipedia/zh/4/4a/Xinjiang_University_logo.png");


        return nftMeta;
    }
    

}

```

> TaYi的前端web3js框架引入

```shell
npm install tayijs
```

```shell
yarn add tayijs
```


### 前端初始化示例
```ts
import {TaYi} from "tayijs/dist/web3/TaYi";


let tayi:TaYi;

const connTaYi = ()=>{
    try {
        tayi =  new TaYi("0x1048f6de58fe9a82d6aa0245f3adbedca08a5fa4","ws://127.0.0.1:8081");

    }catch (e) {
        
    }
 
}

const getTaYi = ()=>{
    if (tayi == null){
        connTaYi();
    }
    return tayi;
}


export {getTaYi}
```

```ts
let  trNFT = "a6e79b6648968a496e75f9949d4f0f0c33e5d7fd999f804ccd4f06976fd9ea4d";

let tayi = getTaYi()
//部署合约
// console.log(await tayi.deploy("Qmcgx8bz2yujVQksvafgxiCmFfg9FJXrFd2NAtyJhhmbLN"))



// mint(address to, string memory tokenURI)
async function mintNFT(to:any,tokenURI:any){
   //调用合约
    return tayi.call(trNFT,"mintNFT",{
        id:Date.now(),
        to,
        tokenURI
    })
}

```