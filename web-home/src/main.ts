import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Layui from '@layui/layui-vue'
import '@layui/layui-vue/lib/index.css'
// import ElementPlus from 'element-plus'
// import 'element-plus/dist/index.css'

const app = createApp(App)
    app.use(store).use(router)
// app.use(ElementPlus)
app.use(Layui)
        app.mount('#app')
