import {init} from "echarts"
import prettyBytes from "pretty-bytes"
import prettyMilliseconds from 'pretty-ms'
const numberFormat = new Intl.NumberFormat(undefined, {maximumSignificantDigits: 3})

export function buildClusteredChart(type: string, operation: string, chartData: any, container: HTMLElement, isMemory: Boolean) {
  const chartDom = container
  // chartDom.className = "resultChart column"
  // container.appendChild(chartDom)

  function findData(size: string, name: string) {
    //@ts-ignore
    for (const sizeData of chartData[type]) {
      if (sizeData.size === size) {
        return sizeData[name + "_" + operation]
      }
    }

    throw new Error(`no size for ${size}`)
  }

  function hasData(name: string) {
    //@ts-ignore
    for (const sizeData of chartData[type]) {
      if (name + "_" + operation in sizeData) {
        return true
      }
    }

    return false
  }

  const series = []
  const xAxis = []
  const yAxis = []
  const grid = []
  const h = Math.floor(100 / chartData.sizes.length)
  const gap = 3
  //@ts-ignore
  for (const size of chartData.sizes.toReversed()) {
    grid.push({
      left: "0%",
      // space for bar label (right)
      right: "6%",
      bottom: `${h * grid.length}%`,
      height: (h - gap) + "%",
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

    let libraryNames = chartData.series.filter((it: string) => hasData(it))
    if (type !== "ObjectToObject" && type !== "LinkedMap") {
      libraryNames = libraryNames.filter((it: string) => it !== "kotlin")
    }

    series.push(...libraryNames.map((name: string) => {
      return {
        data: [findData(size, name)],
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

  const chart = init(chartDom)
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
      type: "scroll",
      // disable by default
      selected: {
        "trove-jb": false,
        "trove": false,
        "koloboke": false,
        "hppc": false,
        "ec": false,
      },
    },
    grid: grid,
    xAxis: xAxis,
    yAxis: yAxis,
    series: series,
  })

  return chart
}