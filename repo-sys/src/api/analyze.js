// 导入request.js 请求工具
import request from '@/utils/request.js'
//获取历史需求数据
export const statisticsListService=()=>{
    return request.get('/statistics')
}
//添加历史需求数据
export const statisticsAddService=(Data)=>{
    return request.post('/statistics',Data)
}
//更新历史需求数据
export const statisticsUpdateService=(Data)=>{
    return request.put('/statistics',Data)
}
//删除历史需求数据
export const statisticsDeleteService=(id)=>{
    return request.delete('/statistics?id='+id)
}
//获取线性回归预测结果
export const statisticsPredictService=(id)=>{
    return request.get('/statistics/predict?id='+id)
}