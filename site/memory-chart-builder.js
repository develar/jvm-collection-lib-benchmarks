"use strict"

function buildMemoryChart(type, operation, titleText, data, container) {
  const element = document.createElement("div")
  element.className = "resultChart column"
  container.appendChild(element)

  const chart = createXYChart(element)
  chart.numberFormatter.numberFormat = "#.b"
  chart.data = data[type]

  configureCursor(chart)
  configureTitle(chart, titleText)

  const sizeAxis = createSizeAxis(chart)

  const valueAxis = chart.yAxes.push(new am4charts.ValueAxis())
  valueAxis.dataFields.value = operation
  valueAxis.logarithmic = true

  for (const libraryName of data.series) {
    const series = chart.series.push(new am4charts.StepLineSeries())
    series.dataFields.valueX = "size"
    series.dataFields.categoryX = "size"
    series.dataFields.valueY = `${libraryName}_${operation}`
    series.name = libraryName
    series.segments.template.tooltipText = `Series: ${libraryName}`
    configureSegments(series, chart)
  }
}
