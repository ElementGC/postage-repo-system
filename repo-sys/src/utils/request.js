//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
//添加token
import { useTokenStore } from '@/stores/token.js';
import { ElMessageBox } from 'element-plus';
//导入路由器，在未登录时直接跳转回登录页面
import router  from '@/router'

//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:8888';
const baseURL = '/api';
const instance = axios.create({baseURL})



//添加响应拦截器
instance.interceptors.response.use(
    result=>{
        return result.data;
    },
    err=>{
        //判断响应状态码，401表示未登录，直接跳转到登录页面
        if(err.response.status == 401){
            ElMessageBox.alert('用户当前未登录，请登录后访问')
            router.push('/')
        }else{
        ElMessageBox.alert('服务异常');
        return Promise.reject(err);//异步的状态转化成失败的状态
        }
    }
)

//添加请求拦截器
instance.interceptors.request.use(
    (config)=>{
        //请求前的回调函数
        //直接添加token
        const usetoken = useTokenStore();
        //判断token是否存在
        if(usetoken.token){
            //同意添加请求头
            config.headers.Authorization = usetoken.token
        }
        return config

    },
    (err)=>{
        //请求错误的回调
    }
)

export default instance;