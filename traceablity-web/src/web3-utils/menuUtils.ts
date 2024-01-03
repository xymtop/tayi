//定义一个菜单的结构
export interface Menu {
  id?: number;
  name: string;
}

//返回模拟的多个订单数据
// export const getMenus = (): Menu[] => {
//   return [
//     {
//       id: 0,
//       name: '开始溯源',
//     },
//     {
//       id: 1,
//       name: '导出图片',
//     },
//     {
//       id: 2,
//       name: '导出JSON',
//     },
//     {
//       id  : 3,
//       name: '刷新数据',
//     }
//     ];
// };