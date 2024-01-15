//函数池

const funPools: Map<string, Function> = new Map();

const  addPoolItem = (hash:string,fun:Function)=>{
    funPools.set(hash,fun)
}


const  getPoolItem = (hash:string) : Function|undefined =>{
    return funPools.get(hash)
}

export {addPoolItem,getPoolItem}