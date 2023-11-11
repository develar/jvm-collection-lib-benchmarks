<template>
  <!-- remove bottom padding of content card, chart doesn't have axis, a lot of space -->
  <Card :pt="{ content: { class: 'pb-0' } }">
    <template #subtitle>{{ props.title }}</template>
    <template #content>
      <div ref="chartContainer" class="h-[520px]"></div>
    </template>
  </Card>
</template>
<script setup lang="ts">
import { buildClusteredChart } from "./clustered-chart-builder"
import {onBeforeUnmount, onMounted, ref} from "vue"

import Card from 'primevue/card'

import {chartData} from "./data"
import {memoryChartData} from "./memory-data"

const props = defineProps({
  type: {type: String, required: true},
  operation: {type: String, required: true},
  title: {type: String, required: true},
  isMemory: {type: Boolean, required: true},
})

const chartContainer = ref(null)

let resizeObserver: ResizeObserver
onMounted(() => {
  //@ts-ignore
  const container = chartContainer.value!!
  const chart = buildClusteredChart(props.type, props.operation, props.isMemory ? memoryChartData : chartData, container, props.isMemory)

  resizeObserver = new ResizeObserver(() => {
    chart.resize()
  })
  resizeObserver.observe(container)
})
onBeforeUnmount(() => {
  const container = chartContainer.value
  // noinspection JSIncompatibleTypesComparison
  if (resizeObserver !== undefined && container != null) {
    resizeObserver.unobserve(container)
  }
})
</script>
