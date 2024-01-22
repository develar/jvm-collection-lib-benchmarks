<script setup lang="ts">
import {tabs} from "./components/benchmarkMenuModel"
import type {HeaderLink} from "#ui-pro/types/index.js"

const links: Array<HeaderLink> = tabs.map(tab => {
  return {
    label: tab.label,
    to: tab.path,

    props: {
      type: tab.type
    },
  }
})

useHead({
  title: "Benchmarks for Collections Libraries: A Comparative Analysis",
  meta: [
    {name: "viewport", content: "width=device-width, initial-scale=1"}
  ],
  link: [
    {rel: "icon", type: "image/svg+xml", href: "/favicon.svg"},
  ],
  htmlAttrs: {
    lang: "en"
  }
})

</script>

<template>
  <UHeader :links="links">
    <template #logo>
      <span></span>
     </template>
    <template #right>
      <UColorModeButton />

      <UButton to="https://github.com/develar/jvm-collection-lib-benchmarks" target="_blank" icon="i-simple-icons-github" aria-label="GitHub" color="gray" variant="ghost" />
    </template>
  </UHeader>

  <UMain class="p-2 lg:p-4">
    <NuxtPage />
  </UMain>
</template>
