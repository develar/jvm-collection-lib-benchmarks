<template>
  <UCard>
    <template #header>{{ props.title }}</template>
    <div ref="chartContainer" :style="{height: size}"></div>
  </UCard>
</template>
<script setup lang="ts">
import {buildClusteredChart} from "./clustered-chart-builder"
import {computed, onMounted, ref} from "vue"

import {chartData} from "./data"
import {chartData as memoryChartData} from "./memory-data"
import type {ChartData} from "./benchmarkMenuModel"
import { type EChartsType, use } from "echarts/core"
import { CanvasRenderer } from "echarts/renderers"
import { GridComponent, LegendComponent, ToolboxComponent, TooltipComponent } from "echarts/components"
import { BarChart } from "echarts/charts"

use([TooltipComponent, ToolboxComponent, LegendComponent, GridComponent, BarChart, CanvasRenderer])

const props = defineProps({
  type: {type: String, required: true},
  operation: {type: String, required: true},
  title: {type: String, required: true},
  isMemory: {type: Boolean, required: true},
})

const colorMode = useColorMode()

const chartContainer = ref(null)
const size = computed(() => {
  //@ts-ignore
  const data = (props.isMemory ? memoryChartData : chartData) as ChartData
  let result = 0
  if (props.type in data.groups) {
    for (const operationToData of Object.values(data.groups[props.type])) {
      result += 14 * Object.getOwnPropertyNames(operationToData[props.operation]).length
    }
  }
  // console.log(result)
  // 100 for legend
  return (result + 100) + "px"
})

let chart: EChartsType | null

useResizeObserver(chartContainer, () => {
  chart?.resize()
})

const isDark = computed(() => colorMode.value === "dark")
watch(isDark, () => {
  createChart()
})

function createChart() {
  chart?.dispose()
  const container = chartContainer.value
  if (container != null) {
    //@ts-ignore
    const data = (props.isMemory ? memoryChartData : chartData) as ChartData
    chart = buildClusteredChart(props.type, props.operation, data, container, props.isMemory, isDark.value)
  }
}

onMounted(() => {
  createChart()
})

</script>
