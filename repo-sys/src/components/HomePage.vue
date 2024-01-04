<template>
    <div class="homePage">
        <el-row :gutter="0">
            <el-col :span="8" :offset="2">
                <el-card :body-style="{ padding: '20px' }" shadow="hover" @click="change('/good/manage')">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <div style="text-align: center;">
                        <span>商品管理</span>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="8" :offset="3">
                <el-card :body-style="{ padding: '20px' }" shadow="hover" @click="change('/good/record')">
                    <el-icon>
                        <List />
                    </el-icon>
                    <div style="text-align: center;">
                        <span>出入库记录</span>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="0">
            <el-col :span="8" :offset="2">
                <el-card :body-style="{ padding: '20px' }" shadow="hover" @click="change('/good/analyze')">
                    <el-icon>
                        <Histogram />
                    </el-icon>
                    <div style="text-align: center;">
                        <span>统计分析</span>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="8" :offset="3">
                <el-card :body-style="{ padding: '20px' }" shadow="hover" @click="change('/')">
                    <el-icon>
                        <SwitchButton />
                    </el-icon>
                    <div style="text-align: center;">
                        <span>退出登录</span>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script setup>
//调用route，登录成功之后跳转到对应的界面
import {useRouter} from 'vue-router'
const router = useRouter()
const change = (location) =>{
    router.push(location)
}

</script>

<style lang="scss" scoped>
.el-card{
    width: 300px;
    height: 200px;
}

.el-icon {
    font-size: 150px;
    margin-left: 20%;
    color: lightgrey;
}
.el-row{
    margin-top: 50px;
}
</style>
  