<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <b style="font-size: larger;">统计分析</b>
                <div class="extra">
                </div>
            </div>

        </template>
        <!-- 搜索表单 -->
        <el-row>
            <!-- 第一列，操作数据 -->
            <el-col :span="6">
                <div style="height: 450px;">
                    <el-table :data="history" style="width: 100%; height: 380px " stripe border max-height="380px"
                        :cell-style="{ textAlign: 'center', fontSize: 'large', height: '50px' }"
                        :header-cell-style="{ 'text-align': 'center', fontSize: '1.5em', height: '50px' }"
                        @row-click="selectDemand" @row-dblclick="showSteerDialog">

                        <el-table-column prop="goodName" label="历史数据需求" />
                    </el-table>
                    <el-button type="primary" style="width: 100%;height: 15%;" plain @click="openUpdateDialog('添加历史需求数据')">
                        <span style="font-size: 1.6em;">
                            添加新的历史需求数据
                        </span>

                    </el-button>
                </div>
            </el-col>
            <!-- 第二列，展示数据 -->
            <el-col :span="5" :offset="1">
                <div style="height: 450px;">
                    <el-descriptions class="margin-top" :column="1" size="large" border>
                        <template #title>
                            <div class="show-title">
                                {{ showTitle }}
                            </div>
                        </template>
                        <el-descriptions-item width="50%">
                            <template #label>
                                <div class="cell-item">
                                    7月
                                </div>
                            </template>
                            <div class="cell-item">
                                {{ showModel.julN }}
                            </div>
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    8月
                                </div>
                            </template>
                            <div class="cell-item">
                                {{ showModel.augN }}
                            </div>
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    9月
                                </div>
                            </template>
                            <div class="cell-item">
                                {{ showModel.sepN }}
                            </div>
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    10月
                                </div>
                            </template>
                            <div class="cell-item">
                                {{ showModel.octN }}
                            </div>
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    11月
                                </div>
                            </template>
                            <div class="cell-item">
                                {{ showModel.novN }}
                            </div>
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    12月
                                </div>
                            </template>
                            <div class="cell-item">
                                {{ showModel.decN }}
                            </div>
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    预测结果
                                </div>
                            </template>
                            <div class="cell-item">
                                {{ showModel.preN }}
                            </div>
                        </el-descriptions-item>
                    </el-descriptions>
                </div>
            </el-col>
            <!-- 第三列，图标展示 -->
            <el-col :span="10" :offset="1">
                <div style="height: 450px;" id="showChart">
                    图表展示区
                </div>
            </el-col>

        </el-row>

    </el-card>

    <!-- 双击商品名，打开操作弹窗 -->
    <el-dialog v-model="steerDialogVisible" :title="steerDialogTitle" width="22%">
        <el-button type="primary" @click="openUpdateDialog('更新历史需求数据')">
            更新数据
        </el-button>
        <el-button type="primary" @click="deleteStatistics(); steerDialogVisible = false">
            删除数据
        </el-button>
        <el-button @click="steerDialogVisible = false">
            点错了
        </el-button>
    </el-dialog>

    <!-- 点击更新数据，打开更新数据弹窗 -->
    <el-dialog v-model="updateDialogVisible" :title="updateDialogTitle" width="25%" draggable>
        <div class="update-dialog-div">
            <el-row class="update-row">
                <el-col :span="8" :offset="1">
                    <div class="update-lable">
                        商品名
                    </div>
                </el-col>
                <el-col :span="13">
                    <el-input :disabled="goodNameDisable" v-model="steerModel.goodName" />
                </el-col>
            </el-row>
            <el-row class="update-row">
                <el-col :span="8" :offset="1">
                    <div class="update-lable">
                        7月数据
                    </div>
                </el-col>
                <el-col :span="13">
                    <el-input v-model="steerModel.julN" />
                </el-col>
            </el-row>
            <el-row class="update-row">
                <el-col :span="8" :offset="1">
                    <div class="update-lable">
                        8月数据
                    </div>
                </el-col>
                <el-col :span="13">
                    <el-input v-model="steerModel.augN" />
                </el-col>
            </el-row>
            <el-row class="update-row">
                <el-col :span="8" :offset="1">
                    <div class="update-lable">
                        9月数据
                    </div>
                </el-col>
                <el-col :span="13">
                    <el-input v-model="steerModel.sepN" />
                </el-col>
            </el-row>
            <el-row class="update-row">
                <el-col :span="8" :offset="1">
                    <div class="update-lable">
                        10月数据
                    </div>
                </el-col>
                <el-col :span="13">
                    <el-input v-model="steerModel.octN" />
                </el-col>
            </el-row>
            <el-row class="update-row">
                <el-col :span="8" :offset="1">
                    <div class="update-lable">
                        11月数据
                    </div>
                </el-col>
                <el-col :span="13">
                    <el-input v-model="steerModel.novN" />
                </el-col>
            </el-row>
            <el-row class="update-row">
                <el-col :span="8" :offset="1">
                    <div class="update-lable">
                        12月数据
                    </div>
                </el-col>
                <el-col :span="13">
                    <el-input v-model="steerModel.decN" />
                </el-col>
            </el-row>
        </div>
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary"
                    @click="updateDialogTitle == '添加历史需求数据' ? addStatistics() : updateStatistics()">确定</el-button>
                <el-button @click="updateDialogVisible = false">取消</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus';

//历史需求数据调用接口
import { statisticsListService, statisticsAddService, statisticsUpdateService, statisticsDeleteService, statisticsPredictService } from '@/api/analyze.js'

//接收历史需求数据的数据模型
const history = ref([
])
//获取历史需求数据
const getStatistics = async () => {
    let result = await statisticsListService();
    history.value = result.data;
}
getStatistics();

//展示需求信息的数据模型
const showModel = ref({
    id: null,
    goodName: null,
    julN: null,
    augN: null,
    sepN: null,
    octN: null,
    novN: null,
    decN: null,
    preN: null
})
const showTitle = ref('需求数据展示区');
//将左边的商品信息通过中间的表格展示
const selectDemand = (row) => {
    showTitle.value = row.goodName + '需求数据展示'
    showModel.value.id = row.id
    linearAnalysis()
    drawLine()
}

//对历史需求数据操作
//操作历史需求数据模型
const steerModel = ref({
    id: null,
    goodName: null,
    julN: null,
    augN: null,
    sepN: null,
    octN: null,
    novN: null,
    decN: null
})
const steerDialogVisible = ref(false)
const steerDialogTitle = ref('')
//双击商品名，唤起操作弹窗
const showSteerDialog = (row) => {
    steerDialogVisible.value = true
    steerDialogTitle.value = "您双击了" + row.goodName + ",是打算？"
    steerModel.value.id = row.id
    steerModel.value.goodName = row.goodName
    steerModel.value.julN = row.julN
    steerModel.value.augN = row.augN
    steerModel.value.sepN = row.sepN
    steerModel.value.octN = row.octN
    steerModel.value.novN = row.novN
    steerModel.value.decN = row.decN
}
//唤起更新弹窗
const updateDialogVisible = ref(false)
const updateDialogTitle = ref('')
const goodNameDisable = ref(true)
const openUpdateDialog = (title) => {
    updateDialogVisible.value = true
    steerDialogVisible.value = false
    updateDialogTitle.value = title
    if (title == "添加历史需求数据") {
        goodNameDisable.value = false
    } else goodNameDisable.value = true
}
//添加、更新历史需求数据操作
const clearUpdateDialog = () => {
    //清空操作数据模型
    steerModel.value.id = null
    steerModel.value.goodName = null
    steerModel.value.julN = null
    steerModel.value.augN = null
    steerModel.value.sepN = null
    steerModel.value.octN = null
    steerModel.value.novN = null
    steerModel.value.decN = null
    //关闭弹窗
    updateDialogVisible.value = false
    //展示界面一道清空
    showTitle.value = null
    showModel.value.julN = null
    showModel.value.augN = null
    showModel.value.sepN = null
    showModel.value.octN = null
    showModel.value.novN = null
    showModel.value.decN = null
    showModel.value.preN = null
    //刷新展示的echart图表
    drawLine()
    //刷新界面
    getStatistics();
}
const addStatistics = async () => {
    let result = await statisticsAddService(steerModel.value)
    ElMessageBox.alert(result.message)
    clearUpdateDialog()
}
const updateStatistics = async () => {
    let result = await statisticsUpdateService(steerModel.value)
    ElMessageBox.alert(result.message)
    clearUpdateDialog()
}
//删除历史需求数据操作
const deleteStatistics = () => {
    ElMessageBox.confirm(
        '你确认删除该历史需求信息吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {

            //调用接口
            let result = await statisticsDeleteService(steerModel.value.id)
            //用户点击了确认
            ElMessage({
                type: result.code == 0 ? 'success' : 'error',
                message: result.message,
            })
            //重新刷新列表
            clearUpdateDialog()
        })
        .catch(() => {
            //用户点击了取消
            ElMessage({
                type: 'info',
                message: '取消删除',
            })
        })
}

//线性回归预测
const linearAnalysis = async () => {
    let result = await statisticsPredictService(showModel.value.id)
    showModel.value = result.data
    drawLine()
}
//作图
//引入echarts
import * as echarts from 'echarts';
const drawLine = () => {
    var myChart = echarts.init(document.getElementById('showChart'));
    myChart.setOption({
        title: {
        },
        tooltip: {},
        xAxis: {
            data: ['7月', '8月', '9月', '10月', '11月', '12月', '预测']
        },
        yAxis: {},
        series: [
            {
                name: '销量',
                type: 'line',
                data: [
                    showModel.value.julN,
                    showModel.value.augN,
                    showModel.value.sepN,
                    showModel.value.octN,
                    showModel.value.novN,
                    showModel.value.decN,
                    showModel.value.preN,
                ]
            }
        ]
    });
}

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

.show-title {
    margin-top: 10px;
    font-size: 20px;
}

.cell-item {
    text-align: center;
    height: 30px;
}

.update-dialog-div {
    margin-top: -20px;
    padding-left: 20px;
}

.update-lable {
    font-size: 20px;
}

.update-row {
    margin-top: 20px;
}
</style>