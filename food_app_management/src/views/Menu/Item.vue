<template>
    <el-dialog v-model="data.showDialog"
               destroy-on-close
               width="90%"
               :title="data.operateTitle">
        <el-card style="border: 1px solid gold;"
                 class="box-card"
                 shadow="never">
            <el-form
                    :model="data.item"
                    :rules="data.rules"
                    ref="itemForm"
                    label-width="100px">
                <el-row>
                    <el-col :span="6">
                        <el-form-item
                                label="菜谱名"
                                prop="name">
                            <el-input
                                    v-model="data.item.name"
                                    :disabled="data.disabled">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item
                                label="描述"
                                prop="description">
                            <el-input type="textarea"
                                    v-model="data.item.description"
                                    :disabled="data.disabled">
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <div v-for="(item,index) in data.ingredientsList">
                    <el-card style="margin-bottom: 10px">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="原料名">
                                    <el-input v-model="item.name"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="原料数量">
                                    <el-input v-model="item.amount"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="原料图片">
                                    <MinioUpload :file-list="item.img"
                                                 ref="uploadRef"
                                                 @uploadCallback="uploadCallbackPicture"
                                                 :limit="1">
                                    </MinioUpload>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="18"></el-col>
                            <el-col :span="3">
                                <el-button type="danger"
                                           @click="deleteIngredientItem(index)">
                                    删除物品
                                </el-button>
                            </el-col>
                            <el-col :span="3">
                                <el-button type="primary"
                                           @click="addIngredientItem(index)">
                                    添加新物品
                                </el-button>
                            </el-col>
                        </el-row>
                    </el-card>
                </div>

                <div v-for="(item,index) in data.stepsList">
                    <el-card style="margin-bottom: 10px">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="步骤描述">
                                    <el-input type="textarea" v-model="item.description"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="步骤图片">
                                    <MinioUpload :file-list="item.img"
                                                 ref="uploadRef"
                                                 @uploadCallback="uploadCallbackPicture"
                                                 :limit="1">
                                    </MinioUpload>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="18"></el-col>
                            <el-col :span="3">
                                <el-button type="danger"
                                           @click="deleteStepsItem(index)">
                                    删除物品
                                </el-button>
                            </el-col>
                            <el-col :span="3">
                                <el-button type="primary"
                                           @click="addStepsItem(index)">
                                    添加新物品
                                </el-button>
                            </el-col>
                        </el-row>
                    </el-card>
                </div>

                <el-row>
                    <el-col :span="6">
                        <el-form-item
                            label="分类"
                            prop="categoryId">
                            <el-select v-model="data.item.categoryId"
                                       :disabled="data.disabled">
                                <el-option v-for="(item,index) in data.categoryData"
                                           :key="index"
                                           :label="item.name"
                                           :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="6">
                        <el-form-item
                                label="菜谱首页图片"
                                prop="image">
                            <MinioUpload :file-list="data.item.image"
                                         ref="uploadRef"
                                         @uploadCallback="uploadCallbackPicture"
                                         :limit="1">
                            </MinioUpload>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item>
                    <el-button
                            v-show="data.showBtn"
                            type="primary"
                            @click="submitForm('itemForm')">
                      保存
                    </el-button>
                    <el-button
                            v-show="data.showBtn"
                            @click="resetForm()">
                      重置
                    </el-button>
                    <el-button @click="back()">
                      返回
                    </el-button>
                </el-form-item>
            </el-form>
        </el-card>
    </el-dialog>
</template>
<script lang="ts" setup>
    import MinioUpload from "@/views/common/MinioUpload.vue";
    import Api from '@/api/Menu/api_menu.js'
    import ApiMenuCategory from '@/api/Menu/api_menucategory.js'
    import { reactive, ref, onMounted, toRefs } from 'vue'
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import {ElMessage, ElMessageBox} from "element-plus";
    import StringUtil from '@/utils/stringUtil.js'

    const store = useStore();
    const router = useRouter()

    // Data
    const data = reactive({
        categoryData: [
            {
                id:'',
                name:''
            }
        ],
        // 原料
        ingredientsList:[
            {
                name:'',
                img:'',
                amount:'',
            }
        ],
        stepsList: [
            {
                description: '',
                img:'',
            }
        ],
        operateTitle: '新增',
        backUrl: '/name/menu/index',
        type: '',
        showBtn: true,
        disabled: false,
        id: 0,
        item: {},
        params: {
            id: '',
            name: '',
            description: '',
            Ingredients: '',
            steps: '',
            likes: '',
            image: '',
            categoryId: ''
        },
        OperatorLogParam: {
          operateContent: '',
          operateFeatures: '',
          operateState: '',
          operateType: ''
        },
        showDialog: false,
        rules: {
          name: [
              { required: true, message: '菜谱名不能为空', trigger: 'blur' }
          ],
          description: [
              { required: true, message: '描述不能为空', trigger: 'blur' }
          ],
            categoryId: [
                { required: true, message: '分类不能为空', trigger: 'blur' }
            ],
          // Ingredients: [
          //     { required: true, message: '原料JSON不能为空', trigger: 'blur' }
          // ],
          // steps: [
          //     { required: true, message: '步骤JSON不能为空', trigger: 'blur' }
          // ],
          // likes: [
          //     { required: true, message: '点赞数不能为空', trigger: 'blur' }
          // ],
          // image: [
          //     { required: true, message: '菜谱首页图片不能为空', trigger: 'blur' }
          // ],
          // type: [
          //     { required: true, message: '分类 0素菜 1荤菜不能为空', trigger: 'blur' }
          // ]
        },
    })

    // Props
    const props = defineProps({
        //子组件接收父组件传递过来的值
        type: {
            type: String,
            default: '新增'
        },
    })

    // Mounted
    onMounted(() => {

    })

    // Methods
    const getMenuCategoryData = () => {
        const params = {};
        ApiMenuCategory.selpage4menucategory(params).then(res => {
            console.log(res)
            if (res.code === 200){
                data.categoryData = res.data.records;
            }
        });
    }

    const uploadCallbackPicture = (response, url) => {
        console.log(url)
        data.ingredientsList[0].img = url
    }

    const addIngredientItem = (index) => {
        for (let i = 0; i < data.ingredientsList.length; i++){
            if (StringUtil.isEmpty(data.ingredientsList[i].name)){
                ElMessage({
                    message: '原料名不能为空',
                    type: 'warning',
                })
                return;
            }
            // if (StringUtil.isEmpty(data.ingredientsList[i].img)){
            //     ElMessage({
            //         message: '原料图片不能为空',
            //         type: 'warning',
            //     })
            //     return;
            // }
            if (StringUtil.isEmpty(data.ingredientsList[i].amount)){
                ElMessage({
                    message: '原料数量不能为空',
                    type: 'warning',
                })
                return;
            }
        }
        const item = {
            name:'',
            img:'',
            amount:'',
        }
        data.ingredientsList.push(item)
    }

    const deleteIngredientItem = (index) => {
        console.log(index)
        if (data.ingredientsList.length === 1){
            ElMessage({
                message: '至少保留一个原料',
                type: 'warning',
            })
            return;
        }
        data.ingredientsList.splice(index,1);
    }

    const addStepsItem = (index) => {
        for (let i = 0; i < data.stepsList.length; i++){
            if (StringUtil.isEmpty(data.stepsList[i].description)){
                ElMessage({
                    message: '原料名不能为空',
                    type: 'warning',
                })
                return;
            }
            // if (StringUtil.isEmpty(data.stepsList[i].img)){
            //     ElMessage({
            //         message: '原料图片不能为空',
            //         type: 'warning',
            //     })
            //     return;
            // }
        }
        const item = {
            description: '',
            img:'',
        }
        data.stepsList.push(item)
    }

    const deleteStepsItem = (index) => {
        console.log(index)
        if (data.stepsList.length === 1){
            ElMessage({
                message: '至少保留一个步骤',
                type: 'warning',
            })
            return;
        }
        data.stepsList.splice(index,1);
    }

    const init = (id, type) => {
        // 界面初始化接收参数
        data.type = type;
        switch (data.type) {
            case 'add':
                data.operateTitle = '新增'
                data.showBtn = true
                data.disabled = false
                break
            case 'detail':
                data.operateTitle = '详情'
                data.showBtn = false
                data.disabled = true
                break
            case 'update':
                data.operateTitle = '修改'
                data.showBtn = true
                data.disabled = false
                break
        }

        getMenuCategoryData();

        // 获取数据
        if (data.type === 'detail' || data.type === 'update') {
            // ID校验
            if (id != null && id != '') {
                data.id = id
            } else {
                ElMessage({
                  message: '数据ID丢失，请重试',
                  type: 'warning',
                })
                return;
            }
            // 发送请求
            Api.sel4menu(data.id).then(res => {
                console.log(res)
                if (res.code === 200){
                    data.item = res.data;
                    // 界面显示
                    data.showDialog = true;
                } else {
                    ElMessage({
                      message: '获取数据失败，请重试',
                      type: 'warning',
                    })
                    return;
                }
            })
        } else {
            // 界面显示
            data.showDialog = true;
        }

        //菜单界面生成时日志记录
        // const islog = Vue.prototype.$config.ISLOG;
        // if (true==islog){
        //     data.OperatorLogParam.operateFeatures = '详情表单'
        //     data.OperatorLogParam.operateType = LogType.Query
        //     data.OperatorLogParam.operateState = '成功'
        //     OperatorLog.setOperationLog(data.OperatorLogParam)
        // }

    }
    const back = () => {
        // 返回操作
        data.showDialog = false;
        location.reload()
        // router.push("/logs/account-change-pass-log");
    }

    // 表单ref
    const itemForm = ref();
    const submitForm = (formName) => {
        // 表单验证
        itemForm.value.validate((valid, fields) => {
            if (valid) {
                saveOrUpdate();
            } else {
                ElMessage({
                  message: '请校验',
                  type: 'warning',
                })
            }
        })
    }

    const resetForm = () => {
        // 重置按钮
        itemForm.value.resetFields();
    }

    const saveOrUpdate = () => {
        // 保存或更新操作
        if (data.type === 'update') {
            Api.update4menu(data.id, data.item).then(res => {
                if (res.code === 200){
                    ElMessage({
                      message: '修改成功',
                      type: 'success',
                    })
                    back()
                } else {
                    ElMessage({
                      message: '修改失败',
                      type: 'warning',
                    })
                }
            })
        } else if (data.type === 'add') {
            Api.add4menu(data.item).then(res => {
                console.log(res)
                if (res.code === 200){
                    ElMessage({
                      message: '保存成功',
                      type: 'success',
                    })
                    back();
                } else {
                    ElMessage({
                      message: '保存失败',
                      type: 'warning',
                    })
                }
            })
        }
    }

    //暴露state和play方法
    defineExpose({
        init,
    });
</script>
