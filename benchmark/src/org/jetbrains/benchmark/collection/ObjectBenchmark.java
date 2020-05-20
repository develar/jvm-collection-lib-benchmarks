package org.jetbrains.benchmark.collection;

import org.openjdk.jmh.infra.Blackhole;

public interface ObjectBenchmark<T extends BaseBenchmarkState> {
  Object get(T state, Blackhole blackhole);

  //Object put(BaseBenchmarkState.ObjectPutOrRemoveBenchmarkState state, Blackhole blackhole);
}
