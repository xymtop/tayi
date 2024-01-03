import BaseLayout from '../../layouts/BaseLayout.vue';
import Login from '../../views/login/index.vue';


export default [

  {
    path: '/',
    redirect: '/web3/home',
  },
  {
    path: '/web3',
    redirect: '/web3/web3',
    component: BaseLayout,
    meta: { title: 'web3' },
    children: [
      {
        path: '/web3/NFT',
        name: 'NFT',
        component: () => import('../../views/web3/NFT.vue'),
        meta: { title: 'NFT', requireAuth: false, affix: true, closable: true },
      },
      {
        path: '/web3/userManage/profile',
        name: 'profile',
        component: () => import('../../views/web3/userManage/profile.vue'),
        meta: { title: '信息维护', requireAuth: true, affix: true, closable: true },
      },
      {
        path: '/web3/userManage/Index',
        name: 'userManageIndex',
        component: () => import('../../views/web3/userManage/Index.vue'),
        meta: { title: '用户管理', requireAuth: true, affix: true, closable: true },
      },
      {
        path: '/web3/proposalManage/Index',
        name: 'proposalManageIndex',
        component: () => import('../../views/web3/proposalManage/Index.vue'),
        meta: { title: '提案管理', requireAuth: true, affix: true, closable: true },
      },
        //editProposal
        {
            path: '/web3/proposalManage/editProposal',
            name: 'editProposal',
            component: () => import('../../views/web3/proposalManage/editProposal.vue'),
            meta: { title: '提案编辑', requireAuth: true, affix: true, closable: true }
        }
        //viewProposal
        ,{
            path: '/web3/proposalManage/viewProposal',
            name: 'viewProposal',
            component: () => import('../../views/web3/proposalManage/viewProposal.vue'),
            meta: { title: '提案详情', requireAuth: true, affix: true, closable: true }
        }
        //addTr
        ,{
            path: '/web3/tr/addTr',
            name: 'addTr',
            component: () => import('../../views/web3/tr/addTr.vue'),
            meta: { title: '添加产品', requireAuth: true, affix: true, closable: true }
        },
        // Inxde
        {
            path: '/web3/tr/Index',
            name: 'trIndex',
            component: () => import('../../views/web3/tr/Index.vue'),
            meta: { title: '产品管理', requireAuth: true, affix: true, closable: true }
        },
    //     displayNft
    {
        path: '/web3/tr/displayPro',
        name: 'displayPro',
        component: () => import('../../views/web3/tr/displayPro.vue'),
        meta: { title: '产品详情', requireAuth: true, affix: true, closable: true }
    },
        //contracts/Index
        {
            path: '/web3/contracts/Index',
            name: 'contractsIndex',
            component: () => import('../../views/web3/contracts/Index.vue'),
            meta: { title: '证明中心', requireAuth: true, affix: true, closable: true }
        },
    //     viewContract
        {
            path: '/web3/contracts/viewContract',
            name: 'viewContract',
            component: () => import('../../views/web3/contracts/viewContract.vue'),
            meta: { title: '证明中心详情', requireAuth: true, affix: true, closable: true }
        },
    //     applyContract
        {
            path: '/web3/contracts/applyContract',
            name: 'applyContract',
            component: () => import('../../views/web3/contracts/applyContract.vue'),
            meta: { title: '证明中心申请', requireAuth: true, affix: true, closable: true }
        },
    //     addProof
        {
            path: '/web3/tr/addProof',
            name: 'addProof',
            component: () => import('../../views/web3/tr/addProof.vue'),
            meta: { title: '添加证明', requireAuth: true, affix: true, closable: true }
        },
      //   addTrForCm
        {
            path: '/web3/tr/addTrForCm',
            name: 'addTrForCm',
            component: () => import('../../views/web3/tr/addTrForCm.vue'),
            meta: { title: '批量溯源', requireAuth: true, affix: true, closable: true }
        },
      {
        path: '/web3/home',
        name: '数据总览',
        component: () => import('../../views/web3/home/index.vue'),
        meta: { title: '数据总览', requireAuth: true, affix: true, closable: true }
      },
    ]
  },
  {
    path: '/login',
    component: Login,
    meta: { title: '登录页面' },
  },
  {
    path: '/workspace',
    redirect: '/workspace/workbench',
    component: BaseLayout,
    meta: { title: '工作空间' },
    children: [
      {
        path: '/workspace/workbench',
        name: 'Workbench',
        component: () => import('../../views/workSpace/workbench/index.vue'),
        meta: { title: '工作台', requireAuth: true, affix: true, closable: false },
      },
      {
        path: '/workspace/console',
        component: () => import('../../views/workSpace/console/index.vue'),
        meta: { title: '控制台', requireAuth: true },
      },
      {
        path: '/workspace/analysis',
        component: () => import('../../views/workSpace/analysis/index.vue'),
        meta: { title: '分析页', requireAuth: true },
      },
      {
        path: '/workspace/monitor',
        component: () => import('../../views/workSpace/monitor/index.vue'),
        meta: { title: '监控页', requireAuth: true },
      }
    ]
  }, {
    path: '/error',
    component: BaseLayout,
    meta: { title: '错误页面' },
    children: [
      {
        path: '/error/401',
        component: () => import('../../views/error/401.vue'),
        meta: { title: '401' },
      },
      {
        path: '/error/403',
        component: () => import('../../views/error/403.vue'),
        meta: { title: '403' },
      },
      {
        path: '/error/404',
        component: () => import('../../views/error/404.vue'),
        meta: { title: '404' },
      },
      {
        path: '/error/500',
        component: () => import('../../views/error/500.vue'),
        meta: { title: '500' },
      }
    ]
  }, {
    path: '/system',
    component: BaseLayout,
    meta: { title: '系统管理' },
    children: [
      {
        path: '/system/user',
        component: () => import('../../views/system/user/index.vue'),
        meta: { title: '用户管理', requireAuth: true },
      },
      {
        path: '/system/role',
        component: () => import('../../views/system/role/index.vue'),
        meta: { title: '角色管理', requireAuth: true },
      },
      {
        path: '/system/menu',
        component: () => import('../../views/system/menu/index.vue'),
        meta: { title: '菜单管理', requireAuth: true },
      },
      {
        path: '/system/organization',
        component: () => import('../../views/system/organization/index.vue'),
        meta: { title: '机构管理', requireAuth: true },
      },
      {
        path: '/system/dictionary',
        component: () => import('../../views/system/dictionary/index.vue'),
        meta: { title: '字典管理', requireAuth: true },
      },
      {
        path: '/system/file',
        component: () => import('../../views/system/file/index.vue'),
        meta: { title: '文件管理', requireAuth: true },
      },
      {
        path: '/system/login',
        component: () => import('../../views/system/login/index.vue'),
        meta: { title: '登录日志', requireAuth: true },
      },
      {
        path: '/system/option',
        component: () => import('../../views/system/option/index.vue'),
        meta: { title: '操作日志', requireAuth: true },
      },
    ]
  }, {
    path: '/result',
    component: BaseLayout,
    meta: { title: '错误页面' },
    children: [
      {
        path: '/result/success',
        component: () => import('../../views/result/success.vue'),
        meta: { title: '成功页面', requireAuth: true },
      },
      {
        path: '/result/failure',
        component: () => import('../../views/result/failure.vue'),
        meta: { title: '失败页面', requireAuth: true },
      },
    ]
  }, {
    path: '/list',
    component: BaseLayout,
    meta: { title: '列表页面' },
    children: [
      {
        path: '/table/base',
        component: () => import('../../views/table/base.vue'),
        meta: { title: '查询列表', requireAuth: true },
      },
      {
        path: '/table/card',
        component: () => import('../../views/table/card.vue'),
        meta: { title: '卡片列表', requireAuth: true },
      },
      {
        path: '/table/project',
        component: () => import('../../views/table/project.vue'),
        meta: { title: '项目列表', requireAuth: true },
      },
      {
        path: '/table/article',
        component: () => import('../../views/table/article.vue'),
        meta: { title: '文章列表', requireAuth: true },
      }
    ]
  }, {
    path: '/form',
    component: BaseLayout,
    meta: { title: '表单页面' },
    children: [
      {
        path: '/form/base',
        component: () => import('../../views/form/base.vue'),
        meta: { title: '基础表单', requireAuth: true },
      },
      {
        path: '/form/step',
        component: () => import('../../views/form/step.vue'),
        meta: { title: '分步表单', requireAuth: true },
      },
      {
        path: '/form/intricate',
        name: 'Intricate',
        component: () => import('../../views/form/intricate.vue'),
        meta: { title: '复杂表单', requireAuth: true },
      },
      {
        path: '/form/step',
        name: 'Step',
        component: () => import('../../views/form/step.vue'),
        meta: { title: '分步表单', requireAuth: true },
      },
    ]
  }, {
    path: '/directive',
    component: BaseLayout,
    meta: { title: '内置指令' },
    children: [
      {
        path: '/directive/permission',
        component: () => import('../../views/directive/permission.vue'),
        meta: { title: '权限指令', requireAuth: true },
      },
    ]
  }, {
    path: '/component',
    component: BaseLayout,
    meta: { title: '常用组件' },
    children: [
      {
        path: '/component/qrcode',
        component: () => import('../../views/component/qrcode.vue'),
        meta: { title: '二维码', requireAuth: true },
      },
      {
        path: '/component/barcode',
        component: () => import('../../views/component/barcode.vue'),
        meta: { title: '条形码', requireAuth: true },
      },
      {
        path: '/component/treeSelect',
        component: () => import('../../views/component/treeSelect.vue'),
        meta: { title: '下拉树', requireAuth: true },
      },
    ]
  }, {
    path: '/enrollee',
    component: BaseLayout,
    meta: { title: '个人中心' },
    children: [
      {
        path: '/enrollee/profile',
        component: () => import('../../views/enrollee/profile/index.vue'),
        meta: { title: '我的资料', requireAuth: true },
      },
      {
        path: '/enrollee/message',
        component: () => import('../../views/enrollee/message/index.vue'),
        meta: { title: '我的消息', requireAuth: true },
      },

    ]
  },{
    path: '/lookup',
    redirect: '/lookup/display',
    component: BaseLayout,
    meta: { title: '展示页面' },
    children: [ 
      {
        path: '/lookup/display',
        name: 'Workbench',
        component: () => import('../../views/lookupDisplay/lookupDisplay.vue'),
        meta: { title: '展示页面', requireAuth: true, affix: true, closable: false },
      },        
    ]
  },


]
