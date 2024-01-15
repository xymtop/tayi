//函数池
const funPools = new Map();
const addPoolItem = (hash, fun) => {
    funPools.set(hash, fun);
};
const getPoolItem = (hash) => {
    return funPools.get(hash);
};
export { addPoolItem, getPoolItem };
