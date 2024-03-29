<!--  线 + 柱混合图 -->
<template>
  <el-card>
    <template #header>
      <div class="title">
        业绩柱状图
        <el-tooltip effect="dark" content="点击试试下载" placement="bottom">
          <i-ep-download class="download" @click="downloadEchart" />
        </el-tooltip>
      </div>
    </template>

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
    default: "barChart",
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
  tooltip: {
    trigger: "axis",
    axisPointer: {
      type: "cross",
      crossStyle: {
        color: "#999",
      },
    },
  },
  legend: {
    x: "center",
    y: "bottom",
    data: ["收入", "销售量"],
    textStyle: {
      color: "#999",
    },
  },
  xAxis: [
    {
      type: "category",
      data: ["浙江", "北京", "上海", "广东", "深圳"],
      axisPointer: {
        type: "shadow",
      },
    },
  ],
  yAxis: [
    {
      type: "value",
      min: 0,
      max: 300,
      interval: 50,
      axisLabel: {
        formatter: "{value} 元",
      },
    },
    {
      type: "value",
      min: 0,
      max: 30,
      interval: 5,
      axisLabel: {
        formatter: "{value} 单",
      },
    },
  ],
  series: [
    {
      name: "收入",
      type: "bar",
      data: [7000, 7100, 7200, 7300, 7400],
      barWidth: 20,
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: "#83bff6" },
          { offset: 0.5, color: "#188df0" },
          { offset: 1, color: "#188df0" },
        ]),
      },
    },
    {
      name: "销售量",
      type: "bar",
      data: [20, 10, 8400, 8600, 8800],
      yAxisIndex:1,
      barWidth: 20,
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: "#25d73c" },
          { offset: 0.5, color: "#1bc23d" },
          { offset: 1, color: "#179e61" },
        ]),
      },
    },
  ],
};

const downloadEchart = () => {
  // 获取画布图表地址信息
  const img = new Image();
  img.src = chart.value.getDataURL({
    type: "png",
    pixelRatio: 1,
    backgroundColor: "#fff",
  });
  // 当图片加载完成后，生成 URL 并下载
  img.onload = () => {
    const canvas = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;
    const ctx = canvas.getContext("2d");
    if (ctx) {
      ctx.drawImage(img, 0, 0, img.width, img.height);
      const link = document.createElement("a");
      link.download = `业绩柱状图.png`;
      link.href = canvas.toDataURL("image/png", 0.9);
      document.body.appendChild(link);
      link.click();
      link.remove();
    }
  };
};

const chart = ref<any>("");
onMounted(() => {
    ApiShopOrder.getDataNearlySixMonths().then((res) => {
      console.log(res);
      if (res.code === 200){
          // 获取月份
          let monthValue = res.data.map((item: any) => item.monthValue);
          // 获取收入
          let total = res.data.map((item: any) => item.total);
          // 获取销售量
          let num = res.data.map((item: any) => item.num);
          // 赋值
          options.xAxis[0].data = monthValue;
          options.series[0].data = total;
          options.series[1].data = num;

          // 图表初始化
          chart.value = markRaw(
              echarts.init(document.getElementById(props.id) as HTMLDivElement)
          );

          chart.value.setOption(options);

          // 大小自适应
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
<style lang="scss" scoped>
.title {
  display: flex;
  justify-content: space-between;

  .download {
    cursor: pointer;

    &:hover {
      color: #409eff;
    }
  }
}
</style>
