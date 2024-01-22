import {init} from "echarts/core"
import prettyBytes from "pretty-bytes"
import prettyMilliseconds from "pretty-ms"
import type {ChartData} from "./benchmarkMenuModel"
import type { EChartsType } from "echarts/core"

export function buildClusteredChart(type: string,
                                    operation: string,
                                    chartData: ChartData,
                                    container: HTMLElement,
                                    isMemory: Boolean,
                                    isDark: Boolean): EChartsType | null {
  const chartDom = container

  const groupData = chartData.groups[type]
  if (groupData == null) {
    console.error(`no data (type=${type}, operation=${operation})`)
    return null
  }

  const series = []
  const xAxis = []
  const yAxis = []
  const grid = []
  const h = Math.floor(92 / chartData.sizes.length)
  for (const size of chartData.sizes) {
    grid.push({
      left: "0%",
      // space for bar label (right)
      right: "10%",
      top: `${(h * grid.length) + 10}%`,
      height: h + "%",
      containLabel: true,
    })
    xAxis.push({
      type: "value",
      show: false,
      gridIndex: grid.length - 1,
    })
    yAxis.push({
      type: "category",
      // pad - to align label
      data: [size.padEnd(4)],
      gridIndex: grid.length - 1
    })

    series.push(...Object.entries(groupData[size][operation]).map(([name, value]) => {
      return {
        data: [value],
        xAxisIndex: xAxis.length - 1,
        yAxisIndex: yAxis.length - 1,
        type: "bar",
        name: name,
        emphasis: {
          focus: "series",
        },
        blur: {
          itemStyle: {
            opacity: 0.3,
          }
        },
        label: {
          show: true,
          position: "right",
          formatter: "{a}"
        },
      }
    }))
  }

  const chart = init(chartDom, isDark ? "dark" : null)
  const valueFormatter = isMemory
    ? (value: number | string) => prettyBytes(value as number, {binary: true})
    : (value: number | string) => prettyMilliseconds(value as number, {formatSubMilliseconds: true})
  chart.setOption({
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow"
      },
      valueFormatter: valueFormatter
    },
    legend: {
      // disable by default
      selected: {
        "hppc": false,
        "ec": false,
        "ec-0.5": false,
        "hppc-0.5": false,
      },
    },
    grid: grid,
    xAxis: xAxis,
    yAxis: yAxis,
    series: series,
  })

  return chart
}