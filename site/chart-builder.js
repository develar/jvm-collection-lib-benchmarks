"use strict"

// noinspection ES6ModulesDependencies
am4core.options.onlyShowOnViewport = true
am4core.useTheme(am4themes_animated)

fetch("data.json")
  .then(response => response.json())
  .then(data => {
    let g = createGroupElement()
    buildChart("IntToInt", "get", "Int to Int \"get\" (50% get failures)", data, g)
    buildChart("IntToInt", "put", "Int to Int \"put\"", data, g)
    buildChart("IntToInt", "remove", "Int to Int \"remove\"", data, g)

    g = createGroupElement()
    buildChart("IntToObject", "get", "Int to Object \"get\" (50% get failures)", data, g)
    buildChart("IntToObject", "put", "Int to Object \"put\"", data, g)
    buildChart("IntToObject", "remove", "Int to Object \"remove\"", data, g)

    g = createGroupElement()
    buildChart("ObjectToInt", "get", "Object to Int \"get\" (50% get failures)", data, g)
    buildChart("ObjectToInt", "put", "Object to Int \"put\"", data, g)
    buildChart("ObjectToInt", "remove", "Object to Int \"remove\"", data, g)

    g = createGroupElement()
    buildChart("ObjectToObject", "get", "Object to Object \"get\" (50% get failures)", data, g)
    buildChart("ObjectToObject", "put", "Object to Object \"put\"", data, g)
    buildChart("ObjectToObject", "remove", "Object to Object \"remove\"", data, g)

    g = createGroupElement()
    buildChart("ReferenceToObject", "get", "Ref to Object \"get\" (50% get failures)", data, g)
    buildChart("ReferenceToObject", "put", "Ref to Object \"put\"", data, g)
    buildChart("ReferenceToObject", "remove", "Ref to Object \"remove\"", data, g)
  })
  .catch(error => console.error(error))

function createGroupElement() {
  const element = document.createElement("div")
  // noinspection SpellCheckingInspection
  element.className = "columns is-gapless box"
  document.body.appendChild(element)
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

  const chart = am4core.create(element, am4charts.XYChart)
  chart.colors.step = 4

  const title = chart.titles.create()
  title.html = `<h2 class="title is-6">${titleText}</h2>`
  title.marginBottom = 5

  const sizeAxis = chart.xAxes.push(new am4charts.ValueAxis())
  sizeAxis.dataFields.value = "size"
  sizeAxis.logarithmic = true
  // sizeAxis.strictMinMax = true
  sizeAxis.renderer.grid.template.disabled = true
  sizeAxis.renderer.labels.template.disabled = true

  const valueAxis = chart.yAxes.push(new am4charts.ValueAxis())
  valueAxis.dataFields.value = operation
  valueAxis.renderer.minGridDistance = 62

  if (data == null) {
    throw new Error(`data is not provided (type=${type}, operation=${operation})`)
  }

  for (const libEntry of data[type]) {
    // ignore such variants
    if (libEntry.name === "Koloboke mixing keys' hashes" || libEntry.name === "Koloboke not null key") {
      continue
    }

    const series = chart.series.push(new am4charts.LineSeries())
    series.dataFields.valueX = "size"
    series.dataFields.valueY = operation
    series.strokeWidth = 3
    series.name = libEntry.name
    series.data = libEntry.data

    configureSegments(series, chart)
  }

  configureLegend(chart)

  // https://www.amcharts.com/docs/v4/tutorials/custom-valueaxis-grid-using-ranges/
  function createGrid(value, label) {
    const range = sizeAxis.axisRanges.create()
    range.value = value
    range.size = value
    range.label.text = label
  }

  // createGrid(0)
  createGrid(10000, "10K");
  createGrid(100_000, "100K");
  createGrid(1_000_000, "1M");
  createGrid(10_000_000, "10M");
  createGrid(100_000_000, "100M");
}

function configureSegments(series, chart) {
  const segment = series.segments.template
  segment.interactionsEnabled = true

  const hoverState = segment.states.create("hover")
  hoverState.properties.strokeWidth = 5

  const dimmed = segment.states.create("dimmed")
  dimmed.properties.strokeOpacity = 0.5

  segment.events.on("over", function (event) {
    processOver(chart, event.target.parent.parent.parent)
  })

  segment.events.on("out", function (event) {
    processOut(chart, event.target.parent.parent.parent)
  })
}

function configureLegend(chart) {
  chart.legend = new am4charts.Legend()
  chart.legend.position = "right"
  chart.legend.itemContainers.template.events.on("over", event => {
    processOver(chart, event.target.dataItem.dataContext)
  })
  chart.legend.itemContainers.template.events.on("out", event => {
    processOut(chart, event.target.dataItem.dataContext)
  })
}

function processOver(chart, hoveredSeries) {
  hoveredSeries.toFront()

  hoveredSeries.segments.each(segment => {
    segment.setState("hover")
  })

  chart.series.each(series => {
    if (series !== hoveredSeries) {
      series.segments.each(segment => {
        segment.setState("dimmed")
      })
      series.bulletsContainer.setState("dimmed")
    }
  })
}

function processOut(chart, hoveredSeries) {
  chart.series.each(series => {
    series.segments.each(segment => {
      segment.setState("default")
    })
    series.bulletsContainer.setState("default")
  })
}