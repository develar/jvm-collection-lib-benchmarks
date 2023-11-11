<template>
  <div class="grid grid-cols-3 sm:gap-2 w-full">
    <BarChart v-for="operation in operations"
              :key="operation.id"
              :type="props.type"
              :is-memory="false"
              :operation="operation.id" :title="operation.label">
    </BarChart>
  </div>
  <Divider>Memory</Divider>
  <div class="grid grid-cols-3 sm:gap-2 w-full">
    <BarChart v-for="operation in operations"
              :key="operation.id"
              :type="props.type"
              :is-memory="true"
              :operation="operation.id" :title="operation.label">
    </BarChart>
  </div>
</template>
<script setup lang="ts">
import BarChart from "@/BarChart.vue"
import Divider from "primevue/divider"

const props = defineProps({
  type: {type: String, required: true},
})

const operations = [
  {id: "get", label: '"get" (50% get failures)'},
  {id: "put", label: "put/update"},
  {id: "remove", label: "put/remove"},
]

</script>