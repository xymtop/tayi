
import levenshtein from 'fast-levenshtein';

export function searchArrByKey(datas: any[], keyword: any, ...fields: string[]) {
    let filteredData: any[] = [];
    const threshold = 3;

    for (let data of datas) {
        console.log(data)
        for (let field of fields) {
            // 确保字段存在且为字符串
            if (data[field] == undefined){
                continue
            }
            console.log(keyword.toLowerCase())
                if (data[field].toString().toLowerCase().includes(keyword.toLowerCase())) {
                    filteredData.push(data);
                    break; // 匹配到一个字段后跳出内部循环
                }
        }
    }

    return filteredData;
}
