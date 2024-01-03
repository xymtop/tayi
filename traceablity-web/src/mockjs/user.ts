import { Result } from "../types/result";
import { User } from "../types/user";

let user: User = {
  'userId': '1992',
  'username': 'admin',
}

//下面是第二份菜单
const menus = [
  {
    id: "/workspace",
    icon: "layui-icon-home",
    title: "工作空间",
    children: [
      {
        id: "/web3/home",
        icon: "layui-icon-find-fill",
        title: "数据总览"
      }
    ]
  },
  {
    id: "/table",
    icon: "layui-icon-align-left",
    title: "社区管理",
    children: [
      {
        id: "/web3/contracts/Index",
        icon: "layui-icon-file-b",
        title: "证明中心"
      },

      {
        id: "/web3/proposalManage/Index",
        icon: "layui-icon-carousel",
        title: "提案中心"
      }
    ]
  },
  {
    id: "/form",
    icon: "layui-icon-table",
    title: "溯源中心",
    children: [
      {
        id: "/web3/tr/Index",
        icon: "layui-icon-form",
        title: "产品管理"
      },
       {
        id: "/web3/tr/addTr",
        icon: "layui-icon-form",
        title: "添加产品"
      },

      {
        id: "/web3/tr/addProof",
        icon: "layui-icon-form",
        title: "添加溯源"
      },
      //   /web3/tr/addTrForCm
      {
        id: "/web3/tr/addTrForCm",
        icon: "layui-icon-form",
        title: "批量溯源"
      },
      // {
      //   id: '/system/file',
      //   icon: "layui-icon-file",
      //   title: '文件管理',
      // },
    ]
  },
  {
    id: '/enrollee',
    icon: "layui-icon-slider",
    title: '个人中心',
    children: [
      {
        id: '/web3/userManage/profile',
        icon: "layui-icon-username",
        title: '我的资料',
      },
      {
        id: '/enrollee/message',
        icon: "layui-icon-email",
        title: '我的消息',
      },
    ]
  },
  {
    id: '/system',
    icon: "layui-icon-set",
    title: '系统管理',
    children: [
      {
        id: '/web3/userManage/Index',
        icon: "layui-icon-user",
        title: '用户管理',
      }

    ]
  },
]

//第三份菜单
// const menus = [
//   {
//     id: "/workspace",
//     icon: "layui-icon-home",
//     title: "工作空间",
//     children: [
//       {
//         id: "/workspace/analysis",
//         icon: "layui-icon-chart-screen",
//         title: "分析页"
//       },
//       {
//         id: "/workspace/monitor",
//         icon: "layui-icon-find-fill",
//         title: "监控页"
//       }
//     ]
//   },
//   {
//     id: "/web3",
//     icon: "layui-icon-home",
//     title: "web3",
//     children: [
//       {
//         id: "/web3/NFT",
//         icon: "layui-icon-chart-screen",
//         title: "NFT"
//       }
//     ]
//   }
// ]
const getInfo = (req: any, res: any) => {
  let item = JSON.parse(req.body);
  let token = item ? item.token : null;
  let result: Result = {
    code: 200,
    msg: "操作成功",
    data: user,
    success: true
  }
  if (item || token) {
    result.code = 99998;
    result.msg = "请重新登录";
    result.success = false;
  }
  return result;
}

const getPermission = (req: any, res: any) => {
  let item = JSON.parse(req.body);
  let token = item ? item.token : null;
  let result: Result = {
    code: 200,
    msg: "操作成功",
    data: ['sys:user:add', 'sys:user:edit', 'sys:user:delete', 'sys:user:import', 'sys:user:export'],
    success: true
  }
  if (item || token) {
    result.code = 99998;
    result.msg = "请重新登录";
    result.success = false;
  }
  return result;
}

const getMenu = (req: any, res: any) => {
  let item = JSON.parse(req.body);
  let token = item ? item.token : null;
  let result: Result = {
    code: 200,
    msg: "操作成功",
    data: menus,
    success: true
  }
  if (item || token) {
    result.code = 99998;
    result.msg = "请重新登录";
    result.success = false;
  }
  return result;
}

const getLogin = (req: any, res: any) => {
  let item = JSON.parse(req.body);
  let account = item.account;
  let password = item.password;
  if (account === 'admin' && password === '123456') {
    return {
      'code': 200,
      'msg': '登陆成功',
      'data': {
        'userId': '35002',
        'token': 'eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiJhZG1pbiIsInVzZXJOYW1lIjoiYWRtaW4iLCJvcmdDb2RlIjoiMzUwMDAiLCJkZXB0Q29kZSI6IjM1MDAwIiwiYXVkIjoiYWRtaW4iLCJpc3MiOiJhZG1pbiIsImV4cCI6MTU5MzUzNTU5OH0.0pJAojRtT5lx6PS2gH_Q9BmBxeNlgBL37ABX22HyDlebbr66cCjVYZ0v0zbLO_9241FX9-FZpCkEqE98MQOyWw',
      }
    }
  } else {
    return {
      'code': 500,
      'msg': '登陆失败,账号密码不正确'
    }
  }
}

const getUpload = (req: any, res: any) => {
  return {
    'code': 200,
    'msg': '上传成功',
    'success': true
  }
}

export default {
  getInfo, getMenu, getLogin, getPermission, getUpload
}