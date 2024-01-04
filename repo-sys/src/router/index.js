import { createRouter, createWebHistory } from 'vue-router'

//导入组件-主页面
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
//导入组件-子页面
import GoodManage from '@/components/GoodManage.vue'
import GoodRecord from '@/components/GoodRecords.vue'
import GoodType from '@/components/GoodType.vue'
import PasswordReset from '@/components/PasswordReset.vue'
import UserCenter from '@/components/UserCenter.vue'
import Analyze from '@/components/Analyze.vue'
import HomePage from '@/components/HomePage.vue'
//测试页
import TestPage from '@/components/test.vue'


//定义路由关系
const routes = [
    { path: '/login', component: LoginVue },
    //对于Layout页面配置多个子路由
    {
        path: '/home', 
        component: LayoutVue,
        children:[
            {path:'/good/manage',component: GoodManage},
            {path:'/good/record',component: GoodRecord},
            {path:'/good/type',component: GoodType},
            {path:'/user/info',component: UserCenter},
            {path:'/good/reset',component: PasswordReset},
            {path:'/good/analyze',component: Analyze},
            {path:'/home',component: HomePage},
        ]
    },
    {
        path: '/', redirect: '/login'
    },{
        path: '/t', component:TestPage
    }

]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

//导出路由
export default router
