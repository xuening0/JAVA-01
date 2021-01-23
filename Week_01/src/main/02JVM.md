-XX:+UseSerialGC 
    配置串行 young采用标记,复制算法, old采用标记,清除,整理算法, 采用单线程的垃圾处理器 会全线STW; 如果是多核处理器,不能利用多核优势
    -XX：+USeParNewGC 改进版本的 Serial GC，可以配合 CMS 使用
-XX:+UseParallelGC 并行GC
    young:标记,复制算法, old:标记,清除,整理算法, 也会STW,多个GC线程并行执行, 利用多核优势,STW时间短,吞吐量高
    -XX：ParallelGCThreads=N 来指定 GC 线程数， 其默认值为 CPU 核心数。
-XX:+UseConMarkSweepGC CMS(解决老年代垃圾回收时,系统长时间卡顿,即:没有明显线程暂停但会与应用线程抢CPU时间; 老年代并发回收时,会伴有多次youngGC)
    GC线程数:默认是Java线程的1/4
    young:使用并行STW标记,复制算法(ParallelGC), old:使用*并发*标记,清除算法
    old:
    1.不对老年代进行整理，而是使用空闲列表（free-lists）来管理内存空间的回收
    2.阶段 1: Initial Mark（初始标记）`STW`
      阶段 2: Concurrent Mark（并发标记）
      阶段 3: Concurrent Preclean（并发预清理）
      阶段 4: Final Remark（最终标记）`STW`
      阶段 5: Concurrent Sweep（并发清除）
      阶段 6: Concurrent Reset（并发重置）
-XX:+UseG1GC -XX:MaxGCPauseMillis=50 G1算法(将 STW 停顿的时间和分布，变成可预期且可配置的)
    没有young与old的明确划分,堆被划分成多(通常2048)个存放对象的小块, 每个小块可能是Eden,survivor,old区
    -XX:ConcGCThreads ：与Java应用一起执行的GC线程数量，默默认是Java线程的1/4
频繁fullGC原因: 一个是大对象直接分配到了老年代。一个是过早提升年轻带到老年代
调大G1 region大小，如果FGC减少了，也是这个大对象问题 
    
    

sb -u http://localhost:8088/api/hello -n 1000 -c 10

java -Xmx256m -Xms256m -XX:-UseAdaptiveSizePolicy -XX:+UseParallelGC -jar gateway-server-0.0.1-SNAPSHOT.jar
jstat -gc -t 6024 100 40
发生了一次youngGC
GC时间:5ms   Eden: 68085.0 ->3038.6k,
            s: 3075.1->2224.2k,
            old: 16774.9->16782.9k,
            Meta: 38014.6 ->38232.1k,
            CCS: 4782.2 ->4798.6k

























默认情况下，大小会受到自适应参数影响，我们先关掉此参数-XX:-UseAdaptiveSizePolicy。
然后试验如下：
java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseParallelGC -jar target/gateway-server-0.0.1-SNAPSHOT.jar
   MaxHeapSize              = 1073741824 (1024.0MB)
   NewSize                     = 357564416 (341.0MB)
   MaxNewSize               = 357564416 (341.0MB)
   OldSize                      = 716177408 (683.0MB)

java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseConcMarkSweepGC -jar target/gateway-server-0.0.1-SNAPSHOT.jar
   MaxHeapSize              = 1073741824 (1024.0MB)
   NewSize                     = 348913664 (332.75MB)
   MaxNewSize               = 348913664 (332.75MB)
   OldSize                      = 724828160 (691.25MB)
java -Xmx2g -Xms2g -XX:-UseAdaptiveSizePolicy -XX:+UseConcMarkSweepGC -jar target/gateway-server-0.0.1-SNAPSHOT.jar
   MaxHeapSize              = 2147483648 (2048.0MB)
   NewSize                     = 348913664 (332.75MB)
   MaxNewSize               = 348913664 (332.75MB)
   OldSize                      = 1798569984 (1715.25MB)
可以看到 ParallelGC下，young区大小为1024/3 = 341.3M，跟上述显示一致。
CMS情况下则为332.75M，不是1/3，并且在xmx为2048M时，还是332.75M，这说明最大young区大小与Xmx参数无关。
实际上，我的电脑上：64M * 并发GC线程数(4) * 13 / 10 =332.8M
这个式子是jvm代码写死的，只跟GC线程数有关系。
继续测试：
-XX:ParallelGCThreads=2 
   MaxHeapSize              = 2147483648 (2048.0MB)
   NewSize                     = 174456832 (166.375MB)
   MaxNewSize               = 174456832 (166.375MB)
   OldSize                      = 1973026816 (1881.625MB)
-XX:ParallelGCThreads=8
   MaxHeapSize              = 2147483648 (2048.0MB)
   NewSize                     = 697892864 (665.5625MB)
   MaxNewSize               = 697892864 (665.5625MB)
   OldSize                      = 1449590784 (1382.4375MB)
   
CMS GC的 默认GC进程数是怎么来的？
    区分young区的parnew gc线程数和old区的cms线程数，分别为以下两参数：
    -XX:ParallelGCThreads=m
    -XX:ConcGCThreads=n 
    其中ParallelGCThreads 参数的默认值是：
    CPU核心数 <= 8，则为 ParallelGCThreads=CPU核心数，比如我的那个旧电脑是4
    CPU核心数 > 8，则为 ParallelGCThreads = CPU核心数 * 5/8 + 3 向下取整
    16核的情况下，ParallelGCThreads = 13
    32核的情况下，ParallelGCThreads = 23
    64核的情况下，ParallelGCThreads = 43
    72核的情况下，ParallelGCThreads = 48
    
    ConcGCThreads的默认值则为：
    ConcGCThreads = (ParallelGCThreads + 3)/4 向下去整。
    ParallelGCThreads = 1~4时，ConcGCThreads = 1
    ParallelGCThreads = 5~8时，ConcGCThreads = 2
    ParallelGCThreads = 13~16时，ConcGCThreads = 4