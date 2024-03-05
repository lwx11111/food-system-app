<template>
    <div>
        <!-- 查询条件 -->
        <el-card style="margin: 10px;">
            <template #header>
                <div class="innerHeader">
                    菜品管理
                </div>
            </template>
            <div style="display: flex;"
                 class="card-search">
                <el-form :inline="true"
                         :model="data.formList"
                         size="default"
                         label-width="100px">
                    <el-form-item label="物品名">
                        <el-input placeholder="请输入物品名"
                                  v-model="data.formList.name"
                                  style="width: 200px"
                                  @keyup.enter.native="getData">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="物品分类">
                        <el-select v-model="data.formList.categoryId"
                                   placeholder="请选择"
                                   style="width: 200px"
                                   @keyup.enter.native="getData">
                            <el-option v-for="(item, index) in data.shopItemCategory"
                                       :label="item.name"
                                       :value="item.id" />
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
            <template #footer>
                <div style="float:right; margin-bottom: 5px">
                    <el-button
                        type="primary"
                        @click="queryData()"
                        icon="Search"
                        :loading="data.isSearch">
                        查询
                    </el-button>
                    <el-button
                        @click="resetData()"
                        icon="Close">
                        清空
                    </el-button>
                </div>
            </template>
        </el-card>

        <el-card style="margin: 10px; border: 1px solid gold">
            <!-- 操作按钮区 -->
            <div style="margin:10px 0;">
                <el-button
                        type="success"
                        icon="DocumentAdd"
                        @click="addData()">
                  新增
                </el-button>
                <el-button
                        type="danger"
                        icon="DocumentDelete"
                        @click="deleteDataMany()">
                  删除
                </el-button>
            </div>

            <!-- 表格呈现 -->
            <el-table
                  :data="data.tableData"
                  :height="data.screenHeight - data.otherHeight"
                  tooltip-effect="dark"
                  style="width:100%"
                  :row-class-name="tableRowClassName"
                  size="default"
                  border
                  @selection-change="selectionChanged">
                <el-table-column
                        type="selection"
                        width="60">
                </el-table-column>
                 <el-table-column
                        prop="id"
                        label="id"
                        width="180"
                        align="center">
                </el-table-column>
                <el-table-column label="picture">
                    <template #default="scope">
                        <div style="display: flex; align-items: center">
                            <el-image :src="scope.row.picture"/>
                        </div>
                    </template>
                </el-table-column>
                 <el-table-column
                        prop="name"
                        label="物品名"
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column :formatter="categoryFormat"
                        prop="categoryId"
                        label="物品分类"
                        width="180"
                        align="center">
                </el-table-column>
                 <el-table-column
                        prop="price"
                        label="物品价格"
                        width="180"
                        align="center">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                        align="center"
                        width="400">
                    <template #default="scope">
                        <el-link
                                style="margin-right: 20px"
                                @click="toUpdate(scope)"
                                type="primary"
                                size="small"
                                :underline="false">
                          更新
                        </el-link>
                        <el-link
                                style="margin-right: 20px"
                                @click="toDetail(scope)"
                                type="primary"
                                size="small"
                                :underline="false">
                          查看详情
                        </el-link>
                        <el-link
                                @click="toDelete(scope)"
                                type="primary"
                                size="small"
                                :underline="false">
                          删除
                        </el-link>
                    </template>
                </el-table-column>
              </el-table>

            <el-pagination
                      v-model:current-page="data.pageConfig.currentPage"
                      v-model:page-size="data.pageConfig.pageSize"
                      class="customPage mt-10"
                      background
                      :page-sizes="[10, 20, 50]"
                      :current-page="data.pageConfig.currentPage"
                      :page-size="data.pageConfig.pageSize"
                      :total="data.pageConfig.total"
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      layout="sizes,total, ->, prev, pager, next">
              </el-pagination>
        </el-card>
        <ItemDialog ref="itemDialog" :type="data.type"></ItemDialog>
        <Upload ref="uploadExcelRef" @callback="uploadExcelCallback"></Upload>
    </div>
</template>
<script lang="ts" setup>
    import Api from '@/api/ShopItem/api_shopitem.js'
    import ShopItemCategoryApi from '@/api/ShopItem/api_shopitemcategory.js'
    import ItemDialog from './Item.vue'
    import { reactive, ref, defineProps, toRefs, onMounted} from 'vue'
    import Upload from "@/utils/oss/upload.vue";
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'
    import {ElMessage, ElMessageBox} from "element-plus";

    const store = useStore();
    const router = useRouter()

    // Data
    const data = reactive({
        context: { componentParent: this },// context: 父对象
        screenHeight: window.innerHeight,// screenHeight:控制高度自适应-页面高度
        otherHeight: 310,// otherHeight:控制高度自适应-表格外的高度
        isSearch: false, // isSearch:控制搜索状态
        detailUrl: '/name/shopitem/item', // detailUrl:详情页面地址
        selectedRows: {}, // selectedRows:选中行对象
        // formList:搜索条件对象 分页控制对象
        formList: {
            name: '',
            picture: '',
            description: '',
            categoryId: '',
            price: ''
        },
        // tableData:表格数据
        tableData: [],
        // OperatorLogParam:用于记录日志
        OperatorLogParam: {
            operateContent: '',
            operateFeatures: '',
            operateState: '',
            operateType: ''
        },
        activeName: '1',
        // 分页配置
        pageConfig: {
            currentPage: 1,
            pageSize: 10,
            total: 1000
        },
        // dialog
        type: '',
        // 物品分类信息
        shopItemCategory: []
    })
    // 解构抛出 直接使用
    // const { type} = toRefs(data)

    // Mounted
    onMounted(() => {
        getData();
        getShopItemCategory();
    })

    // Methods
    const tableRowClassName = ({row, rowIndex}) => {
        if (rowIndex % 2 === 0) {
            return 'warning-row'
        } else if (rowIndex % 1 === 0) {
            return 'success-row'
        }
        return ''
    }
    /**
     * 修改枚举值到具体含义
     * @param row
     * @param column
     */
    const categoryFormat = (row, column) => {
        for (let i = 0; i < data.shopItemCategory.length; i++) {
            if (row.categoryId === data.shopItemCategory[i].id) {
                return data.shopItemCategory[i].name
            }
        }
    }

    const getShopItemCategory = () => {
        const data1 = {};
        ShopItemCategoryApi.selpage4shopitemcategory(data1).then(res => {
            console.log(res)
            for (let i = 0; i < res.data.records.length; i++) {
                // 删除全部分类
               if (res.data.records[i].id === '0') {
                   res.data.records.splice(i, 1)
               }
            }
            data.shopItemCategory = res.data.records;
        })
    }
    const getData = () => {
        // 查询方法
        // 查询参数
        const params = {
            name : data.formList.name,
            picture : data.formList.picture,
            description : data.formList.description,
            categoryId : data.formList.categoryId,
            price : data.formList.price,
            pageIndex : data.pageConfig.currentPage,
            pageSize : data.pageConfig.pageSize
        }
        // 后台请求
        Api.selpage4shopitem(params).then(res=> {
            if (res.code === 200){
                data.tableData = res.data.records
                data.pageConfig.total = res.data.total
                data.isSearch = false
            }

            // 日志记录
            // data.OperatorLogParam.operateContent = JSON.stringify(params)
            // data.OperatorLogParam.operateFeatures = '查询列表'
            // data.OperatorLogParam.operateType = LogType.Query
            // data.OperatorLogParam.operateState = '成功'
            // OperatorLog.setOperationLog(this.OperatorLogParam)

        })
    }
    // 添加记录
    const itemDialog = ref();
    const addData = () => {
        data.type = "add";
        itemDialog.value.init(null,data.type);
    }
    // 下载模板
    const downloadExcelTemplate = () => {
        const params = {}
        Api.downloadExcelTemplate(params).then(data => {
            console.log(data)
            const blob = new Blob([data], { type: 'application/vnd.ms-excel' })
            const blobUrl = URL.createObjectURL(blob)
            const a = document.createElement('a')
            a.href = blobUrl
            a.download = '店铺物品表（只有官方一家店）.xls'
            a.click()
            window.URL.revokeObjectURL(blobUrl)
        })
    }

    // 导入数据
    const uploadExcelRef = ref();
    const uploadExcel = () => {
        // const uploadExcelUrl = Api.uploadExcelUrl();
        uploadExcelRef.value.init(this.SHOP_SERVER + '/shopitem/uploadExcel');
    }

    const deleteDataMany = () => {
        // 删除多条记录
        const datas = data.selectedRows
        const dataids = []
        if (!datas || !datas.length || datas.length === 0) {
            ElMessage({
                message: '请选择数据',
                type: 'warning',
            })
        } else {
            for (const i of datas) {
                dataids.push(i.id)
            }
            ElMessageBox.confirm(
                    '确认删除？',
                    '警告',
                    {
                        confirmButtonText: '确认删除',
                        cancelButtonText: '取消',
                        type: 'warning',
                    }
            ).then(() => {
                Api.dels4shopitem(dataids).then(res => {
                    if (res.code === 200){
                        ElMessage({
                            type: 'success',
                            message: '删除成功',
                        })
                        getData()
                    } else {
                        ElMessage({
                            type: 'warning',
                            message: '删除失败',
                        })
                    }

                    // 日志记录
                    // this.OperatorLogParam.operateContent = JSON.stringify(dataids)
                    // this.OperatorLogParam.operateFeatures = '删除记录'
                    // this.OperatorLogParam.operateType = LogType.Query
                    // this.OperatorLogParam.operateState = '成功'
                    // OperatorLog.setOperationLog(this.OperatorLogParam)
                })
            }).catch(() => {
                ElMessage({
                    type: 'info',
                    message: '已取消删除',
                })
            })
        }
    }

    const queryData = () => {
        data.isSearch = true
        data.pageConfig.currentPage = 1
        getData()
    }

    const resetData = () => {
        // 重置事件
        data.isSearch = false
        for (const key in data.formList) {
            data.formList[key] = ''
        }
    }

    const excelData = () => {
        const params = {}
        Api.excelData4shopitem(params).then(data => {
            const blob = new Blob([data], { type: 'application/vnd.ms-excel' })
            const blobUrl = URL.createObjectURL(blob)
            const a = document.createElement('a')
            a.href = blobUrl
            a.download = '店铺物品表（只有官方一家店）.xls'
            a.click()
            window.URL.revokeObjectURL(blobUrl)
        })
    }
    /**
     导入后的回调方法
     @param action: put/delete， put=上传，delete=删除
     @param status: true/false，true=成功，false=失败
     @param groupId: 附件组ID
     @param response: 响应内容
     **/
    const uploadExcelCallback = (action, status, groupId, response) =>{
        console.log("Success")
        // if (action === 'put' && status) {
        //     this.$notify({
        //         type: 'success',
        //         title: '导入成功',
        //         message: response.message,
        //         duration: 5000
        //     })
        // } else {
        //     this.$notify({
        //         type: 'error',
        //         title: '导入失败',
        //         message: response.message,
        //         duration: 5000
        //     })
        // }
    }
    const selectionChanged = (val: number) => {
        // 选中行变化事件
        data.selectedRows = val
    }

    const toUpdate = (scope) => {
        data.type = "update"
        itemDialog.value.init(scope.row.id, data.type);
    }
    const toDetail = (scope) => {
        data.type = "detail"
        itemDialog.value.init(scope.row.id, data.type);
    }

    const toDelete = (scope) => {
        ElMessageBox.confirm(
                '确认删除？',
                '警告',
                {
                    confirmButtonText: '确认删除',
                    cancelButtonText: '取消',
                    type: 'warning',
                }
        ).then(() => {
            console.log(scope.row.id)
            Api.del4shopitem(scope.row.id).then(res => {
                console.log(res)
                if (res.code === 200){
                    ElMessage({
                        type: 'success',
                        message: '删除成功',
                    })
                    getData()
                } else {
                    ElMessage({
                        type: 'warning',
                        message: '删除失败',
                    })
                }
            })

            // 日志记录
            // this.OperatorLogParam.operateContent = JSON.stringify(dataids)
            // this.OperatorLogParam.operateFeatures = '删除记录'
            // this.OperatorLogParam.operateType = LogType.Query
            // this.OperatorLogParam.operateState = '成功'
            // OperatorLog.setOperationLog(this.OperatorLogParam)
        }).catch(() => {
            ElMessage({
                type: 'info',
                message: '已取消删除',
            })
        })
    }

    const handleCurrentChange = (val: number) => {
        // 页码切换事件
        data.pageConfig.currentPage = val
        getData()
    }

    const handleSizeChange = (val: number) => {
        // 页面条数变化事件
        data.pageConfig.pageSize = val
        getData()
    }

    const activated = () => {
        getData()
    }

</script>
<style lang="css" scoped>
    .el-table >>> .warning-row {
        background: oldlace;
    }
    .el-table >>> .success-row {
        background: #f0f9eb;
    }
    /* 单页面样式 */
    >>>.el-table .cell {
        white-space: nowrap
    }
</style>
