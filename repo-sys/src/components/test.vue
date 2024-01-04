<template>
  <div id="myChart" :style="{ width: '300px', height: '300px' }">
    hello
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'hello',
  data() {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  mounted() {
    this.drawLine();
  },
  methods: {
    drawLine() {

      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('myChart'));
      // 绘制图表
      myChart.setOption({
        title: {
          text: 'ECharts 入门示例'
        },
        tooltip: {},
        xAxis: {
          data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
        },
        yAxis: {},
        series: [
          {
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
          }
        ]
      });
    }
  }
}
</script>