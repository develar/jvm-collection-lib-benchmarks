<template>
  <div v-if="group != null" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 sm:gap-2 w-full">
    <BarChart v-for="operation in operations"
              :key="operation.id"
              :type="group"
              :is-memory="false"
              :operation="operation.id" :title="operation.label">
    </BarChart>
  </div>
  <template v-if="group != null && group !== 'String'">
    <UDivider label="Memory" class="pt-4 pb-2"/>
    <div class="grid grid-cols-3 sm:gap-2 w-full">
      <BarChart v-for="operation in operations"
                :key="operation.id"
                :type="group"
                :is-memory="true"
                :operation="operation.id" :title="operation.label">
      </BarChart>
    </div>
  </template>
</template>
<script setup lang="ts">
import { chartData } from "~/components/data"
import { computed } from "vue"
import { tabs } from "~/components/benchmarkMenuModel"

const route = useRoute()

const group = computed(() => {
  const typeUrl = route.params.type
  const result = tabs.find(it => it.path.slice(1) === typeUrl)
  if (result == null) {
    throw createError({ statusCode: 404, statusMessage: "Page Not Found", fatal: true })
  }
  return result.type
})

const operations = computed(() => {
  const operationNames = new Set<string>()

  //@ts-ignore
  const v = group?.value == null ? null : chartData.groups[group.value]
  if (v == null) {
    throw createError({ statusCode: 404, statusMessage: "Page Not Found", fatal: true })
  }

  for (const data of Object.values(v)) {
    for (const operation of Object.getOwnPropertyNames(data)) {
      operationNames.add(operation)
    }
  }

  return Array.from(operationNames).map(id => {
    if (id.startsWith("get")) {
      return {id: id, label: `${id} (50% get failures)`}
    }
    else if (id.startsWith("put")) {
      return {id: id, label: `${id} (put/update)`}
    }
    else {
      return {id: id, label: `${id}/remove`}
    }
  })
})

</script>