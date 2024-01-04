// 导入request.js 请求工具
import request from '@/utils/request.js'

// 提供注册接口函数
export const userRegisterService = (registData)=>{
    //此时的 registData 仍然是 json 格式的，需要进行参数转换
    //借助 URLSearchParams 工具完成传递
    const params = new URLSearchParams()
    for(let key in registData){
        params.append(key,registData[key])
    }
    return request.post('user/register',params);
}

//提供登录接口函数--基本照着注册的抄
export const userLoginService = (loginData)=>{
    const params = new URLSearchParams()
    for(let key in loginData){
        params.append(key,loginData[key])
    }
    return request.post('user/login',params) 
}
//获取用户详细信息
export const userInfoService = ()=>{
    return request.get('/user/info')
}