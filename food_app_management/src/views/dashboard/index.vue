<template >
  <div style="background: #F0F0F0	" class="dashboard-container">
    <!-- github角标 -->


    <el-card shadow="never">
      <el-row justify="space-between">
        <el-col :span="18" :xs="24">
          <div class="flex h-full items-center">
            <img
              class="w-20 h-20 mr-5 rounded-full"
              src="../../assets/images/little-red-hood小红帽.png"
			  style="width: 50px;height: 50px;"
            />
            <div style="margin-left: -50px;">
              <p>偷偷向银河要了一把碎星，只等你闭上眼睛撒入你的梦中，晚安🌛！</p>
              <p class="text-sm text-gray">
                今日天气晴朗，气温在15℃至25℃之间，东南风。
              </p>
            </div>
          </div>
        </el-col>

        <el-col :span="6" :xs="24">
          <div class="flex h-full items-center justify-around">
            <el-statistic :value="99">
              <template #title>
                <div class="flex items-center ">
                  <img src="../../assets/images/消息.png" style="height: 30px ;width: 30px;"/>
                  <span class="text-[16px] ml-1">消息</span>
                </div>
              </template>
            </el-statistic>

            <el-statistic :value="50">
              <template #title>
                <div class="flex items-center ">
                 <img src="../../assets/images/文件夹.png" style="height: 30px ;width: 30px;"/>
                  <span class="text-[16px] ml-1">待办</span>
                </div>
              </template>
              <template #suffix>/100</template>
            </el-statistic>

            <el-statistic :value="10">
              <template #title>
                <div class="flex items-center ">
                  <img src="../../assets/images/消息.png" style="height: 30px ;width: 30px;"/>
                  <span class="text-[16px] ml-1">项目</span>
                </div>
              </template>
            </el-statistic>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <!-- 数据卡片 -->
    <el-row :gutter="10" style="margin-top: 15px">
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never">
          <template #header>
            <div class="flex items-center justify-between">
              <span class="text-[var(--el-text-color-secondary)]">访客数</span>
              <el-tag type="success" style="margin-left: 110px;">日</el-tag>
            </div>
          </template>

          <div class="flex items-center justify-between mt-5">
<!--            <div class="text-lg text-right">-->
<!--             {{data.visitMonthCount}}-->
<!--            </div>-->
            <img src="../../assets/images/好友认证.png" style="height: 40px ;width: 40px; margin-left: 160px;" />
          </div>

          <div
            class="flex items-center justify-between mt-5 text-sm text-[var(--el-text-color-secondary)]"
          >
            <span> 总访客数 </span>
            <span> {{data.visitCount}} </span>
          </div>
        </el-card>
      </el-col>

      <!--消息数-->
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never">
          <template #header>
            <div class="flex items-center justify-between">
              <span class="text-[var(--el-text-color-secondary)]">收藏数</span>
              <el-tag type="success" style="margin-left: 110px;">日</el-tag>
            </div>
          </template>

          <div class="flex items-center justify-between mt-5">
<!--            <div class="text-lg text-right">-->
<!--              {{data.collectionMonthCount}}-->
<!--            </div>-->
             <img src="../../assets/images/收藏.png" style="height: 40px ;width: 40px; margin-left: 160px;" />
          </div>

          <div
            class="flex items-center justify-between mt-5 text-sm text-[var(--el-text-color-secondary)]"
          >
            <span> 总收藏数 </span>
            <span> {{data.collectionCount}} </span>
          </div>
        </el-card>
      </el-col>

      <!--销售额-->
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never">
          <template #header>
            <div class="flex items-center justify-between">
              <span class="text-[var(--el-text-color-secondary)]">销售额</span>
              <el-tag style="margin-left: 110px;">月</el-tag>
            </div>
          </template>

          <div class="flex items-center justify-between mt-5">
<!--            <div class="text-lg text-right">-->
<!--             {{data.salesMonthCount}}-->
<!--            </div>-->
             <img src="../../assets/images/余额.png" style="height: 40px ;width: 40px; margin-left: 160px;" />
          </div>

          <div
            class="flex items-center justify-between mt-5 text-sm text-[var(--el-text-color-secondary)]"
          >
            <span> 总销售额 </span>
            <span> {{data.salesCount}} </span>
          </div>
        </el-card>
      </el-col>

      <!--订单量-->
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never">
          <template #header>
            <div class="flex items-center justify-between">
              <span class="text-[var(--el-text-color-secondary)]">订单量</span>
              <el-tag type="danger" style="margin-left: 110px;">月</el-tag>
            </div>
          </template>

          <div class="flex items-center justify-between mt-5">
<!--            <div class="text-lg text-right">-->
<!--              {{data.orderMonthCount}}-->
<!--            </div>-->
            <img src="../../assets/images/商品.png" style="height: 40px ;width: 40px; margin-left: 160px;" />
          </div>

          <div
            class="flex items-center justify-between mt-5 text-sm text-[var(--el-text-color-secondary)]"
          >
            <span> 总订单量 </span>
            <span> {{data.orderCount}} </span>
          </div>
        </el-card>
      </el-col>
    </el-row>

      <el-row>
          <el-col :span="12">
              <BarChart
                      id="barChart"
                      height="400px"
                      width="100%"
                      class="bg-[var(--el-bg-color-overlay)]"
              />
          </el-col>
          <el-col :span="12">
              <PieChart
                      id="pieChart"
                      height="400px"
                      width="100%"
                      class="bg-[var(--el-bg-color-overlay)]"
              />
          </el-col>
      </el-row>

    <!-- Echarts 图表 -->
<!--    <el-row :gutter="10" style="margin-top: 10px">-->
<!--      <el-col :sm="24" :lg="8" class="mb-2">-->

<!--      </el-col>-->

<!--      <el-col :xs="24" :sm="12" :lg="8" class="mb-2">-->

<!--      </el-col>-->

<!--      <el-col :xs="24" :sm="12" :lg="8" class="mb-2">-->
<!--        <RadarChart-->
<!--          id="radarChart"-->
<!--          height="400px"-->
<!--          width="100%"-->
<!--          class="bg-[var(&#45;&#45;el-bg-color-overlay)]"-->
<!--        />-->
<!--      </el-col>-->
<!--    </el-row>-->
  </div>
</template>

<script setup lang="ts">
import {reactive, ref, defineProps, toRefs, onMounted, onActivated, markRaw} from 'vue'
import BarChart from "@/views/dashboard/components/BarChart.vue";
import PieChart from "@/views/dashboard/components/PieChart.vue";
import RadarChart from "@/views/dashboard/components/RadarChart.vue";
import ApiOrder from '@/api/Shop/api_shoporder.js'
import ApiMenuCollection from '@/api/Menu/api_menucollection.js'

// Data
const data = reactive({
    orderCount: 0,
    orderMonthCount: 0,
    salesCount: 0,
    salesMonthCount: 0,
    collectionCount: 0,
    collectionMonthCount: 0,
    visitCount: 0,
    visitMonthCount: 0,
})

// Mounted
onMounted(() => {
    const params = {

    }
    ApiOrder.selpage4shoporder(params).then(res => {
        console.log(res)
        data.orderCount = res.data.records.length;
        data.orderMonthCount = res.data.records.length;
        for (let i = 0; i < res.data.records.length; i++) {
            data.salesCount += res.data.records[i].price;
            data.salesMonthCount += res.data.records[i].price;
        }
    })

    ApiMenuCollection.selpage4menucollection(params).then(res => {
        console.log(res)
        data.collectionCount = res.data.records.length;
        data.collectionMonthCount = res.data.records.length;
    })

    data.visitCount = 5;
    data.visitMonthCount = 5;

})

// Methods

</script>

<style lang="scss" scoped>
.dashboard-container {
  position: relative;
  padding: 24px;

  .user-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }

  .github-corner {
    position: absolute;
    top: 0;
    right: 0;
    z-index: 1;
    border: 0;
  }

  .data-box {
    display: flex;
    justify-content: space-between;
    padding: 20px;
    font-weight: bold;
    color: var(--el-text-color-regular);
    background: var(--el-bg-color-overlay);
    border-color: var(--el-border-color);
    box-shadow: var(--el-box-shadow-dark);
  }

  .svg-icon {
    fill: currentcolor !important;
  }
  .flex {
    display: flex;
    flex-direction: row; /* 水平方向排列 */
    justify-content: space-around; /* 在容器中均匀分布子元素 */
    align-items: center; /* 在交叉轴上居中对齐子元素 */
  }

}
</style>
