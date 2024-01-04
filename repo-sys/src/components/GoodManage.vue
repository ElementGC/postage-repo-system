<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <b style="font-size: larger;">商品管理</b>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible = true; title = '添加商品';">添加商品</el-button>
                    <el-button type="primary" @click="ruleVisible = true">设置规则</el-button>
                    <el-button type="primary" @click="alterVisiable = true">商品出、入库</el-button>

                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="商品分类：">
                <el-select placeholder="请选择" v-model="typeId">
                    <el-option v-for="c in types" :key="c.id" :label="c.typeName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="商品名">
                <el-input placeholder="请输入商品名" v-model="goodName" :prefix-icon="Search">
                </el-input>
            </el-form-item> <el-form-item>
                <el-button type="primary" @click="goodList">搜索</el-button>
                <el-button @click="typeId = '', goodName = ''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 商品列表 -->
        <el-table :data="goods" stripe style="width: 100%" :row-style="rowState">
            <el-table-column label="商品名" width="150" prop="goodName" sortable></el-table-column>
            <el-table-column label="商品条码" width="150" prop="goodCode"></el-table-column>
            <el-table-column label="商品分类" prop="typeName"></el-table-column>
            <el-table-column label="商品库存" widt="100" prop="number"></el-table-column>
            <el-table-column label="库存单位" prop="unit"></el-table-column>
            <el-table-column label="最近出、入库时间" prop="recentAlterTime"> </el-table-column>
            <el-table-column label="操作" width="150">
                <template #default="{ row }">
                    <el-button :icon="Notebook" circle plain type="primary" @click="showDetail(row)" title="查看"></el-button>
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog('更新商品', row)"
                        title="编辑"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteGood(row)" title="删除"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" image-size="15%" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    </el-card>

    <!-- 商品详细信息弹窗 -->
    <el-dialog v-model="detailVisibe" title="商品详情" width="60%" draggable style="margin-left: 25%;">
        <div class="detail-dialog-div">
            <el-row>
                <el-col :span="3">商品名:</el-col>
                <el-col :span="6">
                    <el-input disabled :placeholder="infoModel.goodName" />
                </el-col>
                <el-col :span="3" :offset="2">商品条码:</el-col>
                <el-col :span="6">
                    <el-input disabled :placeholder="infoModel.goodCode" />
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="3">商品描述:</el-col>
                <el-col :span="17">
                    <el-input :rows="3" type="textarea" disabled :placeholder="infoModel.description"></el-input>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="3">商品类型：</el-col>
                <el-col :span="6">
                    <el-input disabled :placeholder="infoModel.type" />
                </el-col>
                <el-col :span="3" :offset="2">商品数量：</el-col>
                <el-col :span="1">
                    <el-input disabled :placeholder="infoModel.number" />
                </el-col>
                <el-col :span="2" :offset="1">单位:</el-col>
                <el-col :span="2">
                    <el-input disabled :placeholder="infoModel.unit" />
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="3">创建时间：</el-col>
                <el-col :span="6">
                    <el-input disabled :placeholder="infoModel.createTime" />
                </el-col>
                <el-col :span="3" :offset="2">更新时间：</el-col>
                <el-col :span="6">
                    <el-input disabled :placeholder="infoModel.alterTime" />
                </el-col>
            </el-row>
        </div>
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" @click="detailVisibe = false">确定</el-button>
            </span>
        </template>
    </el-dialog>


    <!-- 添加商品、更新商品弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%" draggable>
        <el-form :model="goodModel" :rules="rules" label-width="100px" style="padding-right: 30px">
            <el-form-item label="商品名称" prop="goodName">
                <el-input v-model="goodModel.goodName" minlength="1" maxlength="10"></el-input>
            </el-form-item>
            <el-form-item label="商品条码" prop="goodCode">
                <el-input v-model="goodModel.goodCode" minlength="1" maxlength="15"></el-input>
            </el-form-item>
            <el-form-item label="商品描述">
                <el-input v-model="goodModel.description" :rows="2" type="textarea" />
            </el-form-item>
            <el-form-item label="单位">
                <el-input v-model="goodModel.unit" />
            </el-form-item>
            <el-form-item label="商品分类" prop="goodType">
                <el-select placeholder="请选择" v-model="goodModel.typeId">
                    <el-option v-for="c in types" :key="c.id" :label="c.typeName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="clearDialog">取消</el-button>
                <el-button type="primary" :disabled="buttondisabled" @click="title == '添加商品' ? addGood() : updateGood()">
                    确认 </el-button>
            </span>
        </template>
    </el-dialog>

    <!-- 商品出入库弹窗 -->
    <el-dialog v-model="alterVisiable" title="商品出入库" width="30%" draggable>
        <el-form :model="alterModel" :rules="rules" label-width="100px" style="padding-left: 40px">
            <el-form-item label="商品名称" prop="goodName">
                <el-input v-model="alterModel.goodName" minlength="1" maxlength="10" style="width: 80%;"></el-input>
            </el-form-item>
            <el-form-item label="操作类型" prop="state">
                <el-select placeholder="请选择" v-model="alterModel.type" style="width: 80%;">
                    <el-option label="出库" value="-1"></el-option>
                    <el-option label="入库" value="1"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="操作数量" prop="alterNum" style="width: 85%;">
                <el-input v-model="alterModel.alterNum"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="clearDialog">取消</el-button>
                <el-button type="primary" :disabled="buttondisabled" @click="alterGood">
                    确认 </el-button>
            </span>
        </template>
    </el-dialog>

    <!-- 规则设置弹窗 -->
    <el-dialog v-model="ruleVisible" title="规则设置" draggable style="margin-left: 30%;">
        <div class="rule-dialog-div">
            <el-table :data="diyRules" :span-method="objectSpanMethod" border stripe>
                <el-table-column prop="stateName" label="规则类型" />
                <el-table-column prop="relatedObj" label="关联对象" />
                <el-table-column prop="minNum" label="最小预警值" />
                <el-table-column prop="maxNum" label="最大预警值" />
                <el-table-column label="操作" width="120" style="padding:0px;">
                    <template #default="{ row }">
                        <el-button :icon="Edit" circle plain type="primary" title="编辑"
                            @click="openRuleDialog('编辑规则', row)"></el-button>
                        <el-button :icon="Delete" circle plain type="danger" title="删除"
                            @click="deleteRule(row)"></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" @click="openRuleDialog('新建分类规则', row); diyRuleModel.state = 1">新建分类规则</el-button>
                <el-button type="primary" @click="openRuleDialog('新建商品规则', row); diyRuleModel.state = 0">新建商品规则</el-button>
                <el-button @click="ruleVisible = false">
                    返回 </el-button>
            </span>
        </template>
    </el-dialog>

    <!-- 新建规则、编辑规则弹窗 -->
    <el-dialog v-model="diyRuleDialogVisible" :title="diyRuleDialogTitle" draggable width="30%">
        <el-form :model="diyRuleModel" label-width="100px" style="padding-right: 30px">
            <el-form-item label="关联对象" prop="goodName">
                <el-input v-model="diyRuleModel.relatedGoodName" minlength="1" maxlength="10" placeholder="请输入规则关联商品名"
                    v-if="diyRuleModel.state == 0"></el-input>
                <el-select placeholder="请选择规则关联分类" v-model="diyRuleModel.relatedTypeId" v-if="diyRuleModel.state == 1">
                    <el-option v-for="c in types" :key="c.id" :label="c.typeName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="最大预警值">
                <el-input v-model="diyRuleModel.maxNum" maxlength="12" placeholder="若为空，则默认为99999" />
            </el-form-item>
            <el-form-item label="最小预警值">
                <el-input v-model="diyRuleModel.minNum" maxlength="12" placeholder="若为空，则默认为0" />
            </el-form-item>

        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="clearDiyRuleDialog">取消</el-button>
                <el-button type="primary" :disabled="buttondisabled"
                    @click="diyRuleDialogTitle == '编辑规则' ? updateRule() : addRule()">
                    确认 </el-button>
            </span>
        </template>

    </el-dialog>
</template>

<script setup>
import {
    Edit,
    Delete,
    Search,
    Notebook
} from '@element-plus/icons-vue'

import { ref } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus';


//商品分类数据模型
const types = ref([
    {
        "id": 3,
        "typeName": "美食",
        "typeAlias": "my",
        "createTime": "2023-09-02 12:06:59",
        "updateTime": "2023-09-02 12:06:59"
    },
    {
        "id": 4,
        "typeName": "娱乐",
        "typeAlias": "yl",
        "createTime": "2023-09-02 12:08:16",
        "updateTime": "2023-09-02 12:08:16"
    },
    {
        "id": 5,
        "typeName": "军事",
        "typeAlias": "js",
        "createTime": "2023-09-02 12:08:33",
        "updateTime": "2023-09-02 12:08:33"
    }
])

//用户搜索时选中的分类id
const typeId = ref('')

//用户搜索时输入的商品名
const goodName = ref('')

//商品列表数据模型
const goods = ref([
    {
        "id": 0,
        "goodName": '',
        "goodCode": 0,
        "description": '',
        "number": 0,
        "unit": "",
        "typeId": 1,
        "typeName": "",
        "firstInTime": "",
        "recentAlterTime": "",
    },
])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数

//不满足规则的商品名数组
const disRule = ref([])
//获取商品信息
const goodList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        typeId: typeId.value ? typeId.value : null,
        goodName: goodName.value ? goodName.value : null
    }
    let result = await goodListService(params)

    goods.value = result.data.items
    total.value = result.data.total
    //商品加载完成后，开始获取自定义规则
    ruleList()
}
goodList()



//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    goodList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    goodList()
}

//页面一开始加载就获取分类数据
import { goodTypeListService, goodListService, goodAddService, goodUpdateService, goodDeleteService, goodAlterService } from '@/api/good.js'
const goodTypeList = async () => {
    let result = await goodTypeListService();

    types.value = result.data
}
goodTypeList()

//获取商品详细信息
const detailVisibe = ref(false)
const infoModel = ref({
    goodName: '',
    goodCode: '',
    description: '',
    type: '',
    number: '',
    unit: '',
    createTime: '',
    alterTime: ''
})
const showDetail = (row) => {
    console.log(row)
    detailVisibe.value = true
    infoModel.value.goodName = row.goodName
    infoModel.value.goodCode = row.goodCode
    infoModel.value.description = row.description == null ? '商品暂无详细描述' : row.description
    infoModel.value.type = row.typeName
    infoModel.value.number = row.number
    infoModel.value.unit = row.unit
    infoModel.value.createTime = row.firstInTime
    infoModel.value.alterTime = row.recentAlterTime
}

//添加、更新商品信息
//添加、更新商品弹窗显示
const dialogVisible = ref(false)
const title = ref('')

//添加、更新分商品数据模型
const goodModel = ref({
    goodName: '',
    goodCode: '',
    description: '',
    unit: '',
    typeId: ''
})

//更新商品时的弹窗信息
const showDialog = (thetitle, row) => {
    dialogVisible.value = true;
    title.value = thetitle;
    goodModel.value.goodName = row.goodName;
    goodModel.value.goodCode = row.goodCode;
    goodModel.value.description = row.description;
    goodModel.value.unit = row.unit;
    goodModel.value.typeId = row.typeId;

    //临时加一个 id 属性
    goodModel.value.id = row.id;
    console.log(goodModel.value)

}

//添加更新商品表单校验
const rules = {
    goodName: [
        { required: true, message: '请输入商品名称', trigger: 'blur' },
    ],
    goodCode: [
        { required: true, message: '请输入商品条码', trigger: 'blur' },
    ],
    goodType: [
        { required: true, message: '请选择商品分类', trigger: 'blur' }
    ],
    alterNum: [
        { required: true, message: '数量不能为空', trigger: 'blur' }
    ],
    state: [
        { required: true, message: '请选择商品分类', trigger: 'blur' }

    ]

}
//点击取消则清空模型数据
const clearDialog = () => {
    goodModel.value.goodName = ''
    goodModel.value.goodCode = ''
    goodModel.value.description = ''
    goodModel.value.unit = ''
    goodModel.value.typeId = ''
    dialogVisible.value = false
}

//删除商品
const deleteGood = (row) => {
    ElMessageBox.confirm(
        '你确认删除该商品信息吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //用户点击了确认

            //调用接口
            let result = await goodDeleteService(row.id)
            ElMessage({
                type: result.code == 0 ? 'success' : 'error',
                message: result.message,
            })

            //重新刷新列表
            goodList()
        })
        .catch(() => {
            //用户点击了取消
            ElMessage({
                type: 'info',
                message: '取消删除',
            })
        })
}

//添加、更新接口调用 (删除的在上面)
const addGood = async () => {
    let result = await goodAddService(goodModel.value)
    ElMessageBox.alert(result.message)
    //清空模型数据
    clearDialog();
    goodList();
}

const updateGood = async () => {
    let result = await goodUpdateService(goodModel.value)

    ElMessageBox.alert(result.message)
    //刷新页面
    dialogVisible.value = false;
    //清空模型数据
    clearDialog();
    goodList();
}

//商品出入库
const alterVisiable = ref(false)

//出入库数据模型
const alterModel = ref({
    goodName: '',
    alterNum: '',
    type: ''
})

//点击清空窗口数据
const clearAlterDialog = () => {
    alterModel.value.goodName = ''
    alterModel.value.alterNum = ''
    alterModel.value.state = ''
    alterVisiable.value = false
}

//出入库接口调用
const alterGood = async () => {
    let result = await goodAlterService(alterModel.value)
    if (result.code == 0) {
        ElMessage({
            type: 'success',
            message: '操作完成'
        })
    } else ElMessageBox.alert(result.message)
    //清空模型数据
    clearAlterDialog();
    goodList();
}

//对着相关接口导入
import { ruleListService, ruleAddService, ruleUpdateService, ruleDeleteService } from '@/api/good.js'
//规则设置
const ruleVisible = ref(false)
//接受的规则模型
const diyRules = ref([
    {
        "id": 18,
        "minNum": 10,
        "maxNum": null,
        "relatedGoodId": null,
        "relatedTypeId": 1,
        "relatedGoodName": null,
        "relatedTypeName": "未分类",
        "createTime": "2024-01-02 23:13:35",
        "updateTime": "2024-01-03 08:18:04",
        "state": 1
    }

])
//获取规则
const ruleList = async () => {
    let result = await ruleListService();
    diyRules.value = result.data
    //添加规则种类，统一关联对象
    for (let i = 0; i < diyRules.value.length; i++) {

        diyRules.value[i].stateName = diyRules.value[i].state == 1 ? '分类规则' : '商品规则'
        diyRules.value[i].relatedObj = diyRules.value[i].state == 1 ? diyRules.value[i].relatedTypeName : diyRules.value[i].relatedGoodName
    }

    //将不满足规则校验的商品id添加进入 disRule 数组
    valideGood()
}
//匹配不符合规则的商品
const valideGood = () => {
    //先清空已有的不符合规则的商品，再逐个添加
    disRule.value.length = 0
    for (let i = 0; i < diyRules.value.length; i++) {
        let r = diyRules.value[i]
        switch (r.state) {
            case 0:
                //是商品规则，寻找指定的商品id 并进行比对
                for (let j = 0; j < goods.value.length; j++) {
                    let g = goods.value[j]
                    if (r.relatedGoodId == g.id) {
                        if (g.number > r.maxNum || g.number < r.minNum)
                            disRule.value.push(g.id);
                        break;
                    }
                }
                break
            case 1:
                //是分类规则，寻找指定的分类id 并进行比对
                for (let j = 0; j < goods.value.length; j++) {
                    let g = goods.value[j]
                    if (r.relatedTypeId == g.typeId) {
                        if (g.number > r.maxNum || g.number < r.minNum)
                            disRule.value.push(g.id);
                    }
                }
                break;
            default: ;
        }
    }
}

//更改行信息（用于标注出不满足规则的商品）
const rowState = ({ row }) => {
    let style = {}
    for (let i = 0; i < disRule.value.length; i++) {
        if (row.id == disRule.value[i]) {
            style = { color: 'red' }
        }
    }
    return style
}

//新建规则、修改规则
//自定义规则模型
const diyRuleModel = ref({
    state: 0,
    relatedGoodName: '',
    relatedTypeId: null,
    maxNum: null,
    minNum: null
})
const diyRuleDialogVisible = ref(false);
const diyRuleDialogTitle = ref('')
const openRuleDialog = (title, row) => {
    diyRuleDialogVisible.value = true
    diyRuleDialogTitle.value = title
    if (row != null) {
        console.log(row)
        diyRuleModel.value.state = row.state;
        diyRuleModel.value.relatedGoodName = row.relatedGoodName;
        diyRuleModel.value.relatedTypeId = row.relatedTypeId;
        diyRuleModel.value.maxNum = row.maxNum;
        diyRuleModel.value.minNum = row.minNum;
        diyRuleModel.value.id = row.id;
    }
}
//点击取消清空模型数据
const clearDiyRuleDialog = () => {
    diyRuleModel.value.state = 0;
    diyRuleModel.value.relatedGoodName = null;
    diyRuleModel.value.relatedTypeId = null;
    diyRuleModel.value.maxNum = null;
    diyRuleModel.value.minNum = null;
    diyRuleDialogVisible.value = false
}

//新增、更新、删除接口
const addRule = async () => {
    let result = await ruleAddService(diyRuleModel.value)
    ElMessageBox.alert(result.message)
    //清空模型数据并关闭窗口
    clearDiyRuleDialog();
    diyRuleDialogVisible.value = false
    goodList();
}
const updateRule = async () => {
    let result = await ruleUpdateService(diyRuleModel.value)
    ElMessageBox.alert(result.message)
    //刷新页面
    dialogVisible.value = false;
    //清空模型数据并关闭窗口
    clearDiyRuleDialog();
    diyRuleDialogVisible.value = false
    goodList();
}
//删除商品
const deleteRule = (row) => {
    console.log(row.id)
    ElMessageBox.confirm(
        '你确认删除该规则吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //用户点击了确认

            //调用接口

            let result = await ruleDeleteService(row.id)
            ElMessage({
                type: result.code == 0 ? 'success' : 'error',
                message: result.message,
            })
            diyRuleDialogVisible.value = false
            //重新刷新列表
            goodList()
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

.detail-dialog-div {
    height: 60vh; //如果高度过高，可用max-height
    overflow: auto;
    padding-left: 10%;
    margin-top: -5%;
    font-size: larger;

    .el-row {
        margin-top: 50px;
    }
}

::-webkit-scrollbar {
    display: none;
    /* Chrome Safari */
}

.rule-dialog-div {
    margin-top: -3%;
}
</style>