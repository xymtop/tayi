
const operateDataPool: Map<string, any> = new Map();


const  addOperateDataPoolItem = (hash:string,item:any)=>{
    operateDataPool.set(hash,item)
}


const  getOperateDataPoolItem = (hash: string | undefined) : any =>{
    let data = operateDataPool.get(<string>hash)
    return data
}

export {addOperateDataPoolItem,getOperateDataPoolItem}