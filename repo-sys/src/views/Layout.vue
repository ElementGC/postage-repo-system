<template>
    <!-- 登陆成功后进入页面 -->
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="300px">
            <div class="el-aside__logo" @click="backHome">

            </div>
            <el-menu active-text-color="gray" background-color="#232323" text-color="#fff" router>
                <el-menu-item index="/good/type" @click="changeSub('商品分类')">
                    <div class="sidemain">
                        <el-icon>
                            <Management />
                        </el-icon>
                        <span>商品分类</span>
                    </div>
                </el-menu-item>
                <el-menu-item index="/good/manage" @click="changeSub('商品管理')">
                    <div class="sidemain">
                        <el-icon>
                            <Promotion />
                        </el-icon>
                        <span>商品管理</span>
                    </div>
                </el-menu-item>
                <el-menu-item index="/good/record" @click="changeSub('出、入库记录')">
                    <div class="sidemain">
                        <el-icon>
                            <List />
                        </el-icon>
                        <span>出、入库记录</span>
                    </div>
                </el-menu-item>
                <el-menu-item index="/good/analyze" @click="changeSub('统计分析')">
                    <div class="sidemain">
                        <el-icon>
                            <Histogram />
                        </el-icon>
                        <span>统计分析</span>
                    </div>
                </el-menu-item>

                <el-menu-item @click="logout">
                    <div class="sidemain">
                        <el-icon>
                            <SwitchButton />
                        </el-icon>
                        <span>退出登录</span>
                    </div>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>
                <div style="margin-left: 20px;">欢迎访问&nbsp;&nbsp;&nbsp;<strong>仓库管理系统</strong></div>
                <el-row class="headright">
                    <el-col span="0">
                        <div>
                            您好，用户 &ensp; {{ userInfoStore.info.username }}
                        </div>
                        <div class="timetable">
                            {{ current }}
                        </div>
                    </el-col>
                    <el-dropdown placement="bottom-end" @command="handleCommand">
                        <span class="el-dropdown__box">
                            <el-avatar :src="avatar" />
                            <el-icon>
                                <CaretBottom />
                            </el-icon>
                        </span>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item command="logout" :icon="SwitchButton"
                                    @click="logout">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </el-row>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <el-breadcrumb :separator-icon="ArrowRight" style="margin-bottom: 10px;">
                    <el-breadcrumb-item :to="{ path: '/home' }">返回首页</el-breadcrumb-item>
                    <el-breadcrumb-item>{{ pageNow }}</el-breadcrumb-item>
                </el-breadcrumb>
                <!-- 子组件在这导入 -->
                <router-view>

                </router-view>

            </el-main>
            <!-- 底部区域 -->
            <el-footer>邮政货件仓储系统 ©2024 Created by B20090727 郭宸</el-footer>
        </el-container>
    </el-container>
</template>

<script setup>
import {
    Management,
    Promotion,
    SwitchButton,
    CaretBottom,
    Histogram,
    List
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import { onMounted, ref } from 'vue'
import { ArrowRight } from '@element-plus/icons-vue'




const current = ref('')
onMounted(() => {
    getNowTime();//进入页面调用该方法获取当前时间
    clearInterval(myTimeDisplay);//销毁之前定时器
    var myTimeDisplay = setInterval(() => {
        getNowTime(); //每秒更新一次时间
    }, 1000);
})

const getNowTime = () => {
    var date = new Date()
    var year = date.getFullYear()
    var month = date.getMonth() + 1 < 10 ?
        '0' + (date.getMonth() + 1) : date.getMonth() + 1
    var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
    var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
    var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
    var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
    var time = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;

    current.value = time

}

import { useRouter } from 'vue-router'
const router = useRouter()
const backHome = () => {
    router.push('/home')
    changeSub('')
}

// 面包屑中展示当前页是什么

const pageNow = ref('')
const changeSub = (subpage) => {
    pageNow.value = subpage
}

//调用函数获取用户信息
import { userInfoService } from '@/api/user.js'
import useUserInfoStore from '@/stores/userInfo.js'
import { useTokenStore } from '@/stores/token';
const userInfoStore = useUserInfoStore()
const tokenStore = useTokenStore()
const getUserInfo = async () => {
    let result = await userInfoService()
    //存入pinia
    userInfoStore.setInfo(result.data)

}
getUserInfo()

//头像被点击之后调用
import { ElMessageBox, ElMessage } from 'element-plus';
const logout = () => {
    ElMessageBox.confirm(
        '你确认退出登录吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {

            //用户点击了确认
            ElMessage({
                type: 'success',
                message: '退出登录成功',
            })
            //清空token以及pinia的个人信息
            tokenStore.removeToken()
            userInfoStore.removeInfo()
            //返回登录页面
            router.push('/')
        })
        .catch(() => {
            //用户点击了取消
            ElMessage({
                type: 'info',
                message: '取消退出登录',
            })
        })
}

</script>


<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #232323;

        &__logo {
            height: 120px;
            background: url('@/assets/logo.png') no-repeat center / 120px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-col {
            margin-left: 30;
            font-size: 10;
        }


        .el-dropdown__box {
            display: flex;
            align-items: center;
            margin-left: 20px;


            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }


    .sidemain {
        position: absolute;
        left: 50px;
    }

    .sidesub {
        position: absolute;
        left: 100px;
    }

    .headright {
        width: 300px;
    }

}
</style>