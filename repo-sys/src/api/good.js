import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

//获取商品分类列表
export const goodTypeListService = () => {
    //在请求拦截器里面已经添加过统一的token 就不需要这些了
    /*
    const tokenStore = useTokenStore();
    console.log(tokenStore.token)
    //不需要使用.value就可以直接使用
    return request.get('/type',{headers:{'Authorization':tokenStore.token}})
    */
    return request.get('/type')
}


//添加文章分类
export const goodTypeAddService = (typeData) => {
    return request.post('/type', typeData)
}

//更新文章分类
export const goodTypeUpdateService = (typeData) => {
    return request.put('/type', typeData)
}

//删除分类
export const goodTypeDeleteService = (id) => {
    return request.delete('/type?id=' + id)
}

//获取商品信息
export const goodListService = (params)=>{

    return request.get('/good',{params:params})
}

//获取商品出库记录信息
//获取商品记录信息
export const goodRecordListService = (params)=>{

    return request.get('/good/record',{params:params})
}

//添加商品
export const goodAddService = (goodData) =>{
    return request.post('/good',goodData)

}

//更新商品
export const goodUpdateService = (goodData) => {
    return request.put('/good/update', goodData)
}

//删除商品
export const goodDeleteService = (id) => {
    return request.delete('/good?id=' + id)
}

//商品出入库
export const goodAlterService = (alterData) => {
    return request.put('/good/alter', alterData)
}

//获取规则
export const ruleListService = ()=>{
    return request.get('/good/rule')
}

//新增规则
export const ruleAddService = (ruleData)=>{
    return request.post('/good/rule',ruleData)

}

//更新规则
export const ruleUpdateService = (ruleData)=>{
    return request.put('/good/rule', ruleData)
}

//删除规则
export const ruleDeleteService = (id) => {
    return request.delete('/good/rule?id=' + id)
}