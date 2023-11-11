<template>
  <div class="min-h-full">
    <TabMenu :model="tabs">
      <template #item="{ item, props }">
        <router-link v-if="item.path" v-slot="{ href, navigate }" :to="item.path" custom>
          <a :href="href" v-bind="props.action" @click="navigate">
            {{ item.label }}
          </a>
        </router-link>
      </template>
    </TabMenu>

    <main class="mx-auto sm:px-6 lg:px-8 py-6 lg:py-8">
      <router-view v-slot="{ Component, route }">
        <keep-alive :key="route.path" max="4">
          <component :is="Component"/>
        </keep-alive>
      </router-view>
    </main>
  </div>
</template>
<script setup lang="ts">
import TabMenu from "primevue/tabmenu"
import {tabs} from "@/benchmarkMenuModel"

</script>
