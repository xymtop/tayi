import { createRouter, createWebHashHistory, NavigationGuardNext, RouteLocationNormalized } from 'vue-router'
import routes from './module/base-routes'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { useUserStore } from "../store/user";
import {checkWallet} from "../web3-utils/wallet-utils";
import {layer} from "@layui/layui-vue";

NProgress.configure({ showSpinner: false })

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

/**
 * Router 前置拦截
 * 
 * 1.验证 token 存在, 并且有效, 否则 -> login.vue
 * 2.验证 permission 存在, 否则 -> 403.vue
 * 3.验证 router 是否存在, 否则 -> 404.vue
 * 
 * @param to 目标
 * @param from 来至 
 */
router.beforeEach(async (to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) => {
  NProgress.start();
  //验证是否需要验证
  if (to.meta.requireAuth) {
       const userStore = useUserStore()
        const token = userStore.token
        if (!token) {
            layer.msg("请先登录")
            next({path: '/login'})
            return
        }
  }
  next()
})

router.afterEach(() => {
  NProgress.done();
})

export default router