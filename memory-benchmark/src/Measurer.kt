package org.jetbrains.benchmark.collection

import it.unimi.dsi.fastutil.objects.Object2LongArrayMap
import org.openjdk.jmh.infra.Blackhole
import org.openjdk.jol.info.GraphLayout

interface Measurer {
  fun measure(size: String, operations: Object2LongArrayMap<String>, blackhole: Blackhole)
}

internal fun addOperation(operation: String, operations: Object2LongArrayMap<String>, map: Any) {
  val totalSize = GraphLayout.parseInstance(map).totalSize()
  //val totalSize = 1L
  if (operations.put(operation, totalSize) > 0) {
    throw IllegalStateException("operation is already added")
  }
}

internal fun setup(state: BaseBenchmarkState, size: String) {
  state.mapSize = size
  state.loadFactor = 0.75f
  state.oneFailureOutOf = 2
  state.setup()
}