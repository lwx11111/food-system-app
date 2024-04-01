<!-- 雷达图 -->
<template>
  <el-card>
    <template #header> 订单状态雷达图 </template>
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
    default: "radarChart",
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
    x: "center",
    y: "bottom",
    data: ["数量", "总价"],
    textStyle: {
      color: "#999",
    },
  },
  radar: {
    // shape: 'circle',
    radius: "60%",
    indicator: [
      { name: "家用电器" },
      { name: "服装箱包" },
      { name: "运动户外" },
      { name: "手机数码" },

    ],
  },
  series: [
    {
      name: "Budget vs spending",
      type: "radar",
      itemStyle: {
        borderRadius: 6,
        color: function (params: any) {
          //自定义颜色
          const colorList = ["#409EFF", "#67C23A", "#E6A23C", "#F56C6C"];
          return colorList[params.dataIndex];
        },
      },
      data: [
        {
          value: [400, 400, 400, 400, 400, 400],
          name: "数量",
        },
        {
          value: [300, 300, 300, 300, 300, 300],
          name: "总价",
        },
      ],
    },
  ],
};

const chart = ref<any>("");

onMounted(() => {
    ApiShopOrder.getRadarData().then((res) => {
        console.log(res);
        if (res.code === 200){
            // 获取分类名
            let name = res.data.map((item: any) => {
                return {name: item.name};
            });

            // 获取数量
            let num = res.data.map((item: any) => item.num);
            // 获取销售量
            let total = res.data.map((item: any) => item.total);
            // 赋值
            options.radar.indicator = name;
            options.series[0].data[0].value = num;
            options.series[0].data[1].value = total;

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
