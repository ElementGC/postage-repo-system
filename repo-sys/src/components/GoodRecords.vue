<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <b style="font-size: larger;">商品记录</b>
                <div class="extra">
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="商品名">
                <el-input placeholder="请输入商品名" v-model="goodName" :prefix-icon="Search">
                </el-input>
            </el-form-item> <el-form-item>
                <el-button type="primary" @click="recordList">搜索</el-button>
                <el-button @click="goodName=''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 商品记录列表 -->
        <el-table :data="records" stripe style="width: 100%">
            <el-table-column label="序号" width="100" prop="id"></el-table-column>
            <el-table-column label="商品名"  prop="goodName"></el-table-column>
            <el-table-column label="商品条码" prop="goodCode"></el-table-column>
            <el-table-column label="操作" prop="statusName"></el-table-column>
            <el-table-column label="数量" prop="alterNum" width="100"></el-table-column>
            <el-table-column label="操作发生时间" prop="alterTime" width="200" sortable > </el-table-column>
            <template #empty>
                <el-empty description="没有数据" image-size="15%" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 20,50]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    </el-card>
</template>

<script setup>

import {
    Edit,
    Delete,
    Search
} from '@element-plus/icons-vue'
import { ref } from 'vue'


//用户搜索时输入的商品名
const goodName = ref('')

//商品列表数据模型
const records = ref([
    {
        "id": 0,
        "goodId":0,
        "goodName": '',
        "goodCode": 0,
        "type":0,
        "alterTime": "",
        "recentAlterTime": "",
    },
])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    recordList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    recordList()
}

//获取商品记录信息
import { goodRecordListService } from '@/api/good.js'

const recordList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        goodName: goodName.value ? goodName.value : null
    }
    let result = await goodRecordListService(params)

    records.value = result.data.items
    total.value = result.data.total
    for(let i = 0; i<records.value.length; i++ ){
        let record = records.value[i]
        record.statusName = record.type==1?'入库':'出库'
    }

}
recordList()

</script>


<style lang="scss" scoped>
.page-container {
    min-height: 95%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>