"use strict"

function buildMemoryChart(type, operation, titleText, data, container) {
  const element = document.createElement("div")
  element.className = "resultChart column"
  container.appendChild(element)

  const chart = createXYChart(element)
  chart.numberFormatter.numberFormat = "#.#b"
  chart.data = data[type]

  configureCursor(chart)
  configureTitle(chart, titleText)

  const sizeAxis = createSizeAxis(chart)

  const valueAxis = chart.yAxes.push(new am4charts.ValueAxis())
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

function buildClusteredChart(type, operation, titleText, data, container, numberFormat) {
  const element = document.createElement("div")
  element.className = "resultChart column"
  container.appendChild(element)

  const chart = am4core.create(element, am4charts.XYChart)
  chart.colors.step = 3
  chart.data = data[type]
  if (numberFormat != null) {
    chart.numberFormatter.numberFormat = numberFormat
  }
  configureTitle(chart, titleText)

  const categoryAxis = chart.yAxes.push(new am4charts.CategoryAxis())
  categoryAxis.dataFields.category = "size"
  categoryAxis.renderer.inversed = true
  categoryAxis.renderer.grid.template.location = 0
  categoryAxis.renderer.cellStartLocation = 0.1
  categoryAxis.renderer.cellEndLocation = 0.9

  const valueAxis = chart.xAxes.push(new am4charts.ValueAxis())
  valueAxis.renderer.opposite = true
  valueAxis.logarithmic = true

  const valueSuffix = numberFormat == null ? " ms" : ""

  for (const libraryName of data.series) {
    if (libraryName === "trove" || libraryName === "koloboke" || libraryName === "trove-jb") {
      continue
    }

    const series = chart.series.push(new am4charts.ColumnSeries())
    series.dataFields.valueX = `${libraryName}_${operation}`
    series.dataFields.categoryY = "size"
    series.name = libraryName
    series.columns.template.tooltipText = `{name}: [bold]{valueX}[/]${valueSuffix}`
    series.columns.template.height = am4core.percent(100)
    series.sequencedInterpolation = true

    const categoryLabel = series.bullets.push(new am4charts.LabelBullet())
    categoryLabel.interactionsEnabled = false
    categoryLabel.label.text = "{name}"
    categoryLabel.label.horizontalCenter = "right"
    categoryLabel.label.dx = -10
    categoryLabel.label.fill = am4core.color("#fff")
    categoryLabel.label.hideOversized = false
    categoryLabel.label.truncate = false
    // categoryLabel.label.tooltipText = `{name}: [bold]{valueX}[/]${valueSuffix}`
    categoryLabel.label.inter = `{name}: [bold]{valueX}[/]${valueSuffix}`
  }
}