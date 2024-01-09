import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'


const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'test',
    component: import("@/views/IndexPage.vue")
  },
  {
    path: '/contractView',
    name: 'contractView',
    component: import("@/views/contract/ContractView.vue")
  }


]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
