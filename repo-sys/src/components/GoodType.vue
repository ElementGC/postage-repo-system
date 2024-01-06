<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <b style="font-size: larger;">{{pageName}}</b>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible = true; title = '添加分类';">添加分类</el-button>
                </div>
            </div>
        </template>
        <el-table :data="type" stripe style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="分类名称" prop="typeName"></el-table-column>
            <el-table-column label="分类别名" prop="typeAlias"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog('更新分类', row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteType(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
    </el-card>

    <!-- 添加\更新分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%" draggable>
        <el-form :model="typeModel" :rules="rules" label-width="100px" style="padding-right: 30px">
            <el-form-item label="分类名称" prop="typeName">
                <el-input v-model="typeModel.typeName" minlength="1" maxlength="10"
                    @change="exam(typeModel.typeName)"></el-input>
            </el-form-item>
            <el-form-item label="分类别名" prop="typeAlias">
                <el-input v-model="typeModel.typeAlias" minlength="1" maxlength="15"
                    @change="exam(typeModel.typeName)"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="clearDialog">取消</el-button>
                <el-button type="primary" :disabled="buttondisabled"
                    @click="title == '添加分类' ? addType() : updateType()">
                    确认 </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
const type = ref([
    {
        id: 0,
        typeName: '',
        typeAlias: '',
        
    }
])

const pageName = '商品分类'
import { usePageNameStore } from '@/stores/pageName.js';
const page = usePageNameStore()
page.setPage(pageName)

// 声明异步函数
import { goodTypeListService, goodTypeAddService, goodTypeUpdateService, goodTypeDeleteService } from '@/api/good.js'
const goodTypeList = async () => {
    let result = await goodTypeListService();
    type.value = result.data;
}
goodTypeList();

//添加分类校验规则
//控制添加分类弹窗
const dialogVisible = ref(false)
const buttondisabled = ref(false)
const ruleExam = ref('')

//添加、更新分类数据模型
const typeModel = ref({
    typeName: '',
    typeAlias: ''
})
//添加分类表单校验
const rules = {
    typeName: [
        { required: true, message: '请输入分类名称', trigger: 'blur' },
    ],
    typeAlias: [
        { required: true, message: '请输入分类别名', trigger: 'blur' },
    ]
}

// 未完工，添加判断规则是否通过，通过则将 buttondisabled 设为 false
const exam = (data) => {
    console.log(typeModel.value)

}

//点击取消时清空提交的内容
const clearDialog = ()=>{
    dialogVisible.value = false;
    // 实现更新之后让文本框为空
    typeModel.value.typeName = ''
    typeModel.value.typeAlias = ''
}

//提交添加的信息
import { ElMessageBox, ElMessage } from 'element-plus';
const addType = async () => {
    let result = await goodTypeAddService(typeModel.value)
    ElMessageBox.alert(result.message)
    //刷新页面
    dialogVisible.value = false;
    // 实现更新之后让文本框为空
    typeModel.value.typeName = ''
    typeModel.value.typeAlias = ''
    goodTypeList();
}

//弹窗标题
const title = ref('')
const showDialog = (thetitle, row) => {
    dialogVisible.value = true;
    title.value = thetitle;
    typeModel.value.typeName = row.typeName;
    typeModel.value.typeAlias = row.typeAlias;
    //临时加一个 id 属性
    typeModel.value.id = row.id;
}

//更新分类
const updateType = async () => {
    let result = await goodTypeUpdateService(typeModel.value)
    ElMessageBox.alert(result.message)
    //刷新页面
    dialogVisible.value = false;
    // 实现更新之后让文本框为空
    typeModel.value.typeName = ''
    typeModel.value.typeAlias = ''
    goodTypeList();
}

//删除分类
//删除分类  给删除按钮绑定事件
const deleteType = (row) => {
    ElMessageBox.confirm(
        '你确认删除该分类信息吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {

            //调用接口
            let result = await goodTypeDeleteService(row.id)
            //用户点击了确认
            ElMessage({
                type: result.code==0?'success':'error',
                message: result.message,
            })
            //重新刷新列表
            goodTypeList()
        })
        .catch(() => {
            //用户点击了取消
            ElMessage({
                type: 'info',
                message: '取消删除',
            })
        })
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

</style>