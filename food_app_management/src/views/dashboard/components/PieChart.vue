<!-- 饼图 -->
<template>
  <el-card>
    <template #header> 订单物品分类 </template>
    <div :id="id" :class="className" :style="{ height, width }"></div>
  </el-card>
</template>

<script setup lang="ts">
import * as echarts from "echarts";
import {reactive, ref, defineProps, toRefs, onMounted, onActivated, markRaw} from 'vue'
import ApiShopOrder from "@/api/Shop/api_shoporder";

const props = defineProps({
  id: {
    type: String,
    default: "pieChart",
  },
  className: {
    type: String,
    default: "",
  },
  width: {
    type: String,
    default: "200px",
    required: true,
  },
  height: {
    type: String,
    default: "200px",
    required: true,
  },
});
const options = {
  grid: {
    left: "2%",
    right: "2%",
    bottom: "10%",
    containLabel: true,
  },
  legend: {
    top: "bottom",
    textStyle: {
      color: "#999",
    },
  },
  series: [
    {
      name: "Nightingale Chart",
      type: "pie",
      radius: [50, 130],
      center: ["50%", "50%"],
      roseType: "area",
      itemStyle: {
        borderRadius: 1,
        color: function (params: any) {
          //自定义颜色
          const colorList = ["#409EFF", "#67C23A", "#E6A23C", "#F56C6C","#909399"];
          return colorList[params.dataIndex];
        },
      },
      data: [
        { value: 26, name: "家用电器" },
        { value: 27, name: "户外运动" },
        { value: 24, name: "汽车用品" },
        { value: 23, name: "手机数码" },
      ],
    },
  ],
};

const chart = ref<any>("");

onMounted(() => {
    ApiShopOrder.getHotItemData().then((res) => {
        console.log(res);
        if (res.code === 200){
            options.series[0].data = res.data;

            chart.value = markRaw(
                echarts.init(document.getElementById(props.id) as HTMLDivElement)
            );

            chart.value.setOption(options);

            window.addEventListener("resize", () => {
                chart.value.resize();
            });
        }

    });

});

onActivated(() => {
  if (chart.value) {
    chart.value.resize();
  }
});
</script>
