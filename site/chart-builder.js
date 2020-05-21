"use strict"

// noinspection ES6ModulesDependencies
// am4core.options.onlyShowOnViewport = true
// am4core.useTheme(am4themes_animated)

document.addEventListener("DOMContentLoaded", function(event) {
  const urlParams = new URLSearchParams(window.location.search)
  const tabId = urlParams.get("tab") || "int"
  openTab(tabId)

  window.addEventListener("popstate", (event) => {
    const urlParams = new URLSearchParams(document.location.search)
    const tabId = urlParams.get("tab") || "int"
    openTab(tabId)
  })
})

const createdTabs = new Map()
let currentTab = ""

function askTab(tabId) {
  const urlParams = new URLSearchParams(window.location.search)
  urlParams.set("tab", tabId)
  window.history.pushState("", "", `?${urlParams}`)
  openTab(tabId)
}

const operations = ["get", "put", "remove"]

function openTab(tabId) {
  if (currentTab === tabId) {
    return
  }

  currentTab = tabId

  for (const tab of document.getElementById("tabs").children) {
    tab.style.display = tab.id === tabId ? "block" : "none"
  }

  for (const tabLink of document.getElementsByClassName("tab")) {
    tabLink.className = tabLink.className.replace(" is-active", "")
  }
  document.getElementById(`${tabId}Link`).className += " is-active"

  if (!createdTabs.get(tabId)) {
    createdTabs.set(tabId, true)

    const data = chartData
    const container = document.getElementById(tabId)
    if (tabId === "int") {
      createChartBox("IntToInt", "Int to Int", createBox(container))
    }
    else if (tabId === "intToObject") {
      createChartBox("IntToObject", "Int to Object", createBox(container))
    }
    else if (tabId === "objectToInt") {
      createChartBox("ObjectToInt", "Object to Int", createBox(container))
    }
    else if (tabId === "object") {
      createChartBox("ObjectToObject", "Object to Object", createBox(container))
    }
    else if (tabId === "refToObject") {
      createChartBox("ReferenceToObject", "Ref to Object", createBox(container))
    }
    else if (tabId === "linkedMap") {
      createChartBox("LinkedMap", "LinkedMap", createBox(container))
    }
  }
}

function createChartBox(type, typeTitle, container) {
  const cpuGroup = createDiv()
  container.appendChild(cpuGroup)

  for (const operation of operations) {
    buildClusteredChart(type, operation, `${typeTitle} ${operationToChartTitle(operation)}`, chartData, cpuGroup, null)
  }
  // subGroup = createDiv(box)
  // for (const operation of operations) {
  //   buildMemoryChart(type, operation, "Memory", memoryChartData, subGroup)
  // }
  const memoryGroup = createDiv()
  container.appendChild(memoryGroup)

  for (const operation of operations) {
    buildClusteredChart(type, operation, "Memory", memoryChartData, memoryGroup, "#.#b")
  }
}

const operationToLabel = {
  "get": "get",
  "put": "put/update",
  "remove": "put/remove",
}

function operationToChartTitle(operation) {
  return ` "${operationToLabel[operation]}"${operation === "get" ? " (50% get failures)" : ""}`
}

function createBox(container) {
  // currently, only one box per tab, so, do not create box
  return container
  // const element = document.createElement("div")
  // element.className = "box"
  // container.appendChild(element)
  // return element
}

function createDiv() {
  const element = document.createElement("div")
  // noinspection SpellCheckingInspection
  element.className = "columns is-gapless"
  return element
}

function createColumn(parent) {
  const element = document.createElement("div")
  element.className = "column"
  parent.appendChild(element)
  return element
}

function buildChart(type, operation, titleText, data, container) {
  const element = document.createElement("div")
  element.className = "resultChart column"
  container.appendChild(element)

  const chart = createXYChart(element)
  chart.numberFormatter.numberFormat = "#.##"

  configureCursor(chart)
  configureTitle(chart, titleText)

  const sizeAxis = createSizeAxis(chart)
  sizeAxis.data = data.sizes.map(it => {
    return {size: it}
  })

  const valueAxis = chart.yAxes.push(new am4charts.ValueAxis())
  valueAxis.dataFields.value = operation
  valueAxis.logarithmic = true
  // valueAxis.strictMinMax = true

  for (const libEntry of data[type]) {
    const series = chart.series.push(new am4charts.StepLineSeries())
    series.dataFields.valueX = "size"
    series.dataFields.categoryX = "size"
    series.dataFields.valueY = operation
    series.name = libEntry.name
    series.data = libEntry.data
    series.segments.template.tooltipText = `Series: ${libEntry.name}`
    configureSegments(series, chart)
  }
}

const si = [
  {value: 1_000, symbol: "K"},
  {value: 1_000_000, symbol: "M"},
]
const rx = /\.0+$|(\.[0-9]*[1-9])0+$/

function formatSize(value) {
  if (value < 1000) {
    return value
  }
  if (value >= 1_000_000) {
    return (value / 1000000).toFixed(1).replace(/\.0$/, "") + "M"
  }
  if (value >= 1000) {
    return (value / 1000).toFixed(1).replace(/\.0$/, "") + "K"
  }
  return value
}