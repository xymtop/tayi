import { defineStore } from 'pinia'
import { menu, permission } from "../api/module/user";
import {  } from "vue";
export const useUserStore = defineStore({
  id: 'user',
  state: () => {
    return {
      token: '',
      userInfo: {},
      permissions: [],
      menus: [],
    }
  },
  actions: {
    async loadMenus(){
      const { data, code } = await menu();
    console.log(data,code);
    
      if(code == 200) {
        this.menus = data;
        const addrouter={
          id: '/lookup', 
          icon: 'layui-icon-set', 
          title: '展示页面', 
          children: [
            {
              id: '/lookup/display', 
              icon: 'layui-icon-set', 
              title: '展示页面',
          }
        ]
        }
        this.menus.push(addrouter);
      }
      
    },
    async loadPermissions(){
      const { data, code } = await permission();
      if(code == 200) {
        this.permissions = data;
      }
    }
  },
  persist: {
    storage: localStorage,
    paths: ['token', 'userInfo', 'permissions', 'menus' ],
  }
})