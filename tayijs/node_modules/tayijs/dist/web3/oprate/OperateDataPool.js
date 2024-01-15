const operateDataPool = new Map();
const addOperateDataPoolItem = (hash, item) => {
    operateDataPool.set(hash, item);
};
const getOperateDataPoolItem = (hash) => {
    let data = operateDataPool.get(hash);
    return data;
};
export { addOperateDataPoolItem, getOperateDataPoolItem };
