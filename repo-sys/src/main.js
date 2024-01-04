import './assets/main.scss'

import { createApp } from 'vue'

// 导入Element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//导入路由
import router from '@/router'
//导入pinia用于在组件之间传递参数
import { createPinia } from 'pinia'

//导入persist模块
import { createPersistedState } from 'pinia-persistedstate-plugin'
// 导入ElementPlus的中文语言包（分页条会用到）
import locale from 'element-plus/dist/locale/zh-cn.js'
//全局注册icon
import * as ElementPlusIconsVue from '@element-plus/icons-vue'




import App from './App.vue'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }

const pinia = createPinia()
const presist = createPersistedState()
pinia.use(presist)
app.use(pinia)
app.use(router)
app.use(ElementPlus,{locale})
app.mount('#app')

