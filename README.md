HashMap performance tests from java-performance.info for [Large HashMap overview](http://java-performance.info/hashmap-overview-jdk-fastutil-goldman-sachs-hppc-koloboke-trove-january-2015/#comments).

The original article was updated in 2015. Provided benchmarks results executed 9 May 2020.

See [benchmark result charts](https://collection-libs-comparison.develar.org/).

```
export JAVA_HOME=~/Downloads/jbr/Contents/Home

mvn package
java -jar benchmark/target/benchmarks.jar -bm ss -i 20 -wi 10 -f 1 -foe true -p mapSize=100,1K,10K,100K,1M -tu ms -to 100m -rf json -e '.+\.IntToObjectBenchmark.object.+'
```

JVM: JDK 11.0.7, OpenJDK 64-Bit Server VM, 11.0.7+10-b909 ([JetBrains Runtime](https://confluence.jetbrains.com/display/JBR/JetBrains+Runtime)).
Machine:
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

The full test set takes around 36 hours to complete. 