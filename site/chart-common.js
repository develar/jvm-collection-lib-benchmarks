function configureCursor(chart) {
  const cursor = new am4charts.XYCursor()
  cursor.behavior = "zoomXY"
  cursor.lineX.disabled = true
  cursor.lineY.disabled = true
  cursor.fullWidthLineX = true
  chart.cursor = cursor
}

function configureTitle(chart, titleText) {
  const title = chart.titles.create()
  title.html = `<h2 class="title is-6">${titleText}</h2>`
  title.marginBottom = 5
}

function createSizeAxis(chart) {
  const sizeAxis = chart.xAxes.push(new am4charts.CategoryAxis())
  sizeAxis.dataFields.category = "size"
  sizeAxis.renderer.grid.template.location = 0
  sizeAxis.renderer.minGridDistance = 30
  return sizeAxis
}

function configureSegments(series, chart) {
  const segment = series.segments.template
  segment.interactionsEnabled = true

  const hoverState = segment.states.create("highlighted")
  hoverState.properties.strokeWidth = 4

  const dimmed = segment.states.create("dimmed")
  dimmed.properties.strokeOpacity = 0.7

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
  const template = chart.legend.itemContainers.template
  template.events.on("over", event => {
    processOver(chart, event.target.dataItem.dataContext)
  })
  template.events.on("out", event => {
    processOut(chart, event.target.dataItem.dataContext)
  })
}

function processOver(chart, hoveredSeries) {
  hoveredSeries.toFront()

  hoveredSeries.segments.each(segment => {
    segment.setState("highlighted")
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