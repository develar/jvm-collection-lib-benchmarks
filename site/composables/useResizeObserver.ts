import { onBeforeUnmount, onMounted, type Ref } from "vue"
import { useThrottleFn } from "@vueuse/shared"

export function useResizeObserver(elementRef: Ref<HTMLElement | null>, callback: () => void) {
  const throttledCallback = useThrottleFn(() => {
    callback()
  }, 100)
  let resizeObserver: ResizeObserver | null = null
  onMounted(() => {
    const container = elementRef.value
    if (container != null) {
      resizeObserver = new ResizeObserver(throttledCallback)
      resizeObserver.observe(container)
    }
  })
  onBeforeUnmount(() => {
    resizeObserver?.disconnect()
    resizeObserver = null
  })
}