HashMap performance tests from java-performance.info for [Large HashMap overview](http://java-performance.info/hashmap-overview-jdk-fastutil-goldman-sachs-hppc-koloboke-trove-january-2015/#comments).

Provided benchmarks results executed 9 May 2020.

See [benchmark result charts](https://collection-libs-comparison.develar.org/).

## Running Benchmark
The full test set takes around 7 hours to complete. 
```
export JAVA_HOME=~/Downloads/jbr/Contents/Home
mvn package

java -jar benchmark/target/benchmarks.jar -bm ss -wi 40 -i 40 -f 1 -foe true -rf json \
  -p mapSize=100,1K,10K,100K,1M,10M -tu ms
```

## Memory Usage Measurement
Memory usage measured using [JOL](https://openjdk.java.net/projects/code-tools/jol/).

```
export JAVA_HOME=~/Downloads/jbr/Contents/Home
mvn package

# sudo is required for JOL for ptrace_scope
# specify full path to java as under sudo different environment is used
sudo ~/Downloads/jbr/Contents/Home/bin/java -Djdk.attach.allowAttachSelf=true -Djdk.module.illegalAccess.silent=true -jar memory-benchmark/target/memory-benchmark.jar
```

## Benchmark Results Environment
Presented data were computed on: 

* JVM: JDK 11.0.7, OpenJDK 64-Bit Server VM, 11.0.7+10-b912 ([JetBrains Runtime](https://confluence.jetbrains.com/display/JBR/JetBrains+Runtime)).
* Machine:
    ```
    Model Name                           MacBook Pro   
    Model Identifier                     MacBookPro15,1
    Processor Name:	6-Core Intel Core i7               
    Processor Speed                      2,6 GHz       
    Number of Processors                 1             
    Total Number of Cores                6             
    L2 Cache (per Core)                  256 KB        
    L3 Cache                             9 MB          
    Hyper-Threading Technology:	Enabled                
    Memory:	32 GB
    ```