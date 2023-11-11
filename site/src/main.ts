import "./main.css"
import "primevue/resources/themes/lara-light-indigo/theme.css"
import {createApp} from "vue"
import {createRouter, createWebHistory, useRouter} from "vue-router"
import App from "@/App.vue"
import BenchmarkPage from "@/BenchmarkPage.vue"
import {tabs} from "@/benchmarkMenuModel"

const benchmarkRoutes = tabs.map(tab => {
  return {
    path: tab.path,
    component: BenchmarkPage,
    props: {
      type: tab.type
    },
  }
})
const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      redirect: tabs[0].path,
    }].concat(benchmarkRoutes as any),
})

const app = createApp(App)
app.use(router)
app.mount("#app")
