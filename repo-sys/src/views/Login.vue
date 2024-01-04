<template>
    <el-row class="login-page">
        <!-- 左边是图标，右边是界面 -->
        <el-col :span="16" class="bg"></el-col>
        <el-col :span="6" :offset="1" class="form">
            <!-- 使用card装填 -->
            <el-card style="border-radius: 15px;">
                <!-- 注册表单 -->
                <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registData"
                    :rules="infoRules">
                    <el-form-item>
                        <div class="flex">
                            <div></div>
                            <h1>注册</h1>
                        </div>
                    </el-form-item>
                    <el-form-item prop="username">
                        <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registData.username"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码"
                            v-model="registData.password"></el-input>
                    </el-form-item>
                    <el-form-item prop="rePassword">
                        <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码"
                            v-model="registData.rePassword"></el-input>
                    </el-form-item>
                    <!-- 注册按钮 -->
                    <el-form-item>
                        <el-button class="button" type="primary" @click="register" auto-insert-space>
                            注册
                        </el-button>
                    </el-form-item>
                    <el-form-item class="flex">
                        <el-link type="primary" :underline="false" @click="isRegister = false;clearInfo()">
                            ← 直接登录
                        </el-link>
                    </el-form-item>
                </el-form>
                <!-- 登录表单 -->
                <el-form ref="form" size="large" autocomplete="off" v-else :rules="infoRules" :model="registData">
                    <el-form-item>
                        <div class="flex">
                            <h1>登录</h1>
                        </div>
                    </el-form-item>
                    <el-form-item prop="username">
                        <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registData.username"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registData.password"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <div class="flex">
                            <el-checkbox>记住我</el-checkbox>
                            <el-link type="primary" :underline="false">忘记密码？</el-link>
                        </div>
                    </el-form-item>
                    <!-- 登录按钮 -->
                    <el-form-item>
                        <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
                    </el-form-item>
                    <el-form-item>
                        <div class="flex">
                            <div></div>
                            <el-link type="info" :underline="false" @click="isRegister = true;clearInfo()">
                                注册 →
                            </el-link>
                        </div>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessageBox } from 'element-plus'

//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
//定义数据模型
const registData = ref({
    username: '',
    password: '',
    rePassword: ''
})
//定义表单校验规则
//自定义校验规则：两次密码一致
const checkRPW = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次输入密码'))
    } else if (value !== registData.value.password) {
        callback(new Error('两次输入的密码必须保持一致'))
    } else callback()
}

const infoRules = {
    username: [
        { required: true, message: "请输入用户名", trigger: "blur" },
        { min: 5, max: 16, message: '长度为5-16位字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: "请输入密码", trigger: "blur" },
        { min: 5, max: 16, message: '长度为5-16位字符', trigger: 'blur' }
    ],
    rePassword: [
        { min: 5, max: 16, message: '长度应为5-16位字符', trigger: 'blur' },
        { validator: checkRPW, trigger: 'blur' }
    ],

}

//调用后台接口，完成注册信息传递
import { userRegisterService } from '@/api/user.js'
import { userLoginService } from '@/api/user.js'
const register = async () => {
    // //测试，查看获取数据
    // console.log(registData.value)
    //调用
    let result = await userRegisterService(registData.value)
    if (result.code === 0) {
        //接口文档中的响应结果，code=0 表示成功
        alert(result.message)
    } else {
        console.log(result)
        alert("注册失败," + result.message)
    }
}

//调用route，登录成功之后跳转到系统首页
import {useRouter} from 'vue-router'
const router = useRouter()
//调用store，登录成功后获取token
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore()

//登录部分基本可以直接调用上面的内容，补充一个登录的方法
const login = async()=>{
    let result = await userLoginService(registData.value)
    if (result.code === 0) {
        //接口文档中的响应结果，code=0 表示成功
        ElMessageBox.alert('登陆成功，欢迎进入系统')
        //先存储token再跳转
        tokenStore.setToken(result.data)
        //登录完成后自动跳转首页
        router.push('/home')
    } else {
        console.log(result)
        ElMessageBox.alert(result.message, '登录失败')
    }

}

//切换页面时清空框中数据
const clearInfo =()=>{
    registData.value={
        username:'', //切换时保留输入的用户名
        password:'',
        rePassword:''
    }
}
</script>

<!--  样式-->
<style lang="scss" scoped>
.login-page {
    height: 100vh;
    background-color: #fff(173, 197, 205, 0.623);

    .bg {
        background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
            url('@/assets/login_bg.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>