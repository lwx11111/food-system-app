<template>
    <div>
        <el-carousel :interval="4000" type="card" height="200px">
            <el-carousel-item v-for="item in 6" :key="item">
                <el-image :src=data.imageSrc[item] fit="cover" style="width: 100%; height: 100%"></el-image>
            </el-carousel-item>
        </el-carousel>
    </div>

    <div style="margin-top: 10px">
        <el-table
            :data="data.tableData"
            tooltip-effect="dark"
            style="width:100%"
            size="default"
            border>
            <el-table-column
                type="selection"
                width="60">
            </el-table-column>
            <el-table-column
                prop="id"
                label="序号"
                width="130"
                align="center">
            </el-table-column>
            <el-table-column
                prop="shopItemName"
                label="菜品名"
                width="130"
                align="center">
            </el-table-column>
            <el-table-column
                prop="amount"
                label="购买量"
                width="180"
                align="center">
            </el-table-column>
            <el-table-column
                prop="price"
                label="总营业额"
                width="180"
                align="center">
            </el-table-column>
        </el-table>
    </div>

    <div  style="margin-top: 10px">
        <el-carousel indicator-position="outside">
            <el-carousel-item v-for="item in 4" :key="item">
                <el-image :src=data.imageSrc[item] fit="cover" style="width: 100%; height: 100%"></el-image>
            </el-carousel-item>
        </el-carousel>
    </div>

    <div  style="margin-top: 10px">
        <el-card class="box-card">
            <h3>好好吃后台管理系统功能简介</h3>
            <div class="demo-collapse">
                <el-collapse  accordion>
                    <el-collapse-item title="菜谱管理" name="1">
                        <div>
                            Consistent with real life: in line with the process and logic of real
                            life, and comply with languages and habits that the users are used to;
                        </div>
                        <div>
                            Consistent within interface: all elements should be consistent, such
                            as: design style, icons and texts, position of elements, etc.
                        </div>
                    </el-collapse-item>
                    <el-collapse-item title="菜品管理" name="2">
                        <div>
                            Operation feedback: enable the users to clearly perceive their
                            operations by style updates and interactive effects;
                        </div>
                        <div>
                            Visual feedback: reflect current state by updating or rearranging
                            elements of the page.
                        </div>
                    </el-collapse-item>
                    <el-collapse-item title="商城管理" name="3">
                        <div>
                            Simplify the process: keep operating process simple and intuitive;
                        </div>
                        <div>
                            Definite and clear: enunciate your intentions clearly so that the
                            users can quickly understand and make decisions;
                        </div>
                        <div>
                            Easy to identify: the interface should be straightforward, which helps
                            the users to identify and frees them from memorizing and recalling.
                        </div>
                    </el-collapse-item>
                    <el-collapse-item title="社区管理" name="4">
                        <div>
                            Decision making: giving advices about operations is acceptable, but do
                            not make decisions for the users;
                        </div>
                        <div>
                            Controlled consequences: users should be granted the freedom to
                            operate, including canceling, aborting or terminating current
                            operation.
                        </div>
                    </el-collapse-item>
                    <el-collapse-item title="健康管理" name="5">
                        <div>
                            Decision making: giving advices about operations is acceptable, but do
                            not make decisions for the users;
                        </div>
                        <div>
                            Controlled consequences: users should be granted the freedom to
                            operate, including canceling, aborting or terminating current
                            operation.
                        </div>
                    </el-collapse-item>
                </el-collapse>
            </div>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
    import ApiShopOrder from '@/api/Shop/api_shoporder.js'
    import { reactive, onMounted} from 'vue'
    import { useStore } from "vuex";
    import { useRouter } from 'vue-router'

    const store = useStore();
    const router = useRouter()

    // Data
    const data = reactive({
        // tableData:表格数据
        tableData : [
            {
                id:'1',
                shopItemName: '牛肉',
                amount: '100',
                price: '1907',
            },
        ],
        // 从1开始
        imageSrc:[
            "",
            "/src/assets/homePage/food1.jpg",
            "/src/assets/homePage/food2.jpg",
            "/src/assets/homePage/food3.jpg",
            "/src/assets/homePage/food4.jpg",
            "/src/assets/homePage/food5.jpg",
            "/src/assets/homePage/food6.jpg",
        ]
    })

    // Mounted
    onMounted(() => {
        ApiShopOrder.getStatisticalData().then(res => {
            console.log(res)
            if (res.code === 200){
                data.tableData = res.data;
            }
        })
    })

    // Methods
    const getData = () => {

    }

</script>

<style scoped>
.el-carousel__item h3 {
    color: #475669;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
    text-align: center;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
}
.el-carousel__item h3 {
    display: flex;
    color: #475669;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
}

</style>
