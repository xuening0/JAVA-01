学习笔记
 OjectMonitor
ObjectWaiter对象存在于WaitSet、EntryList、cxq等集合中，或者正在这些集合中移动

```markdown
wait()方法要做的事情就理清了：
包装成ObjectWaiter对象，状态为TS_WAIT；
ObjectWaiter对象被放入_WaitSet中；
当前线程挂起；
```
```text
把线程C在synchronized BLOCK的时候的逻辑理清楚了，小结如下：
偏向锁逻辑，未命中；
如果是无锁状态，就通过CAS去竞争锁，此处由于锁已经被线程B持有，所以不是无锁状态；
不是无锁状态，而且锁不是线程C持有，执行锁膨胀，构造OjectMonitor对象；**如果是当前线程C持有的，就return，然后就能执行同步代码块中的代码了**
竞争锁，竞争失败就将线程加入_cxq队列的首位；
开始无限循环，竞争锁成功就退出循环，竞争失败线程挂起，等待被唤醒后继续竞争；

线程B执行notify时候做的事情：
执行过wait的线程都在队列_WaitSet中，此处从_WaitSet中取出第一个；
根据Policy的不同，将这个线程放入_EntryList或者_cxq队列中的起始或末尾位置；
Policy == 0：放入_EntryList队列的排头位置；
Policy == 1：放入_EntryList队列的末尾位置；
Policy == 2：_EntryList队列为空就放入_EntryList，否则放入_cxq队列的排头位置；
Policy == 3：放入_cxq队列中，末尾位置；

线程B释放了锁之后，执行的操作如下：
偏向锁逻辑，此处未命中；
根据QMode的不同，将ObjectWaiter从_cxq或者_EntryList中取出后唤醒；
唤醒的元素会继续执行挂起前的代码，按照我们之前的分析，线程唤醒后，就会通过CAS去竞争锁，此时由于线程B已经释放了锁，那么此时应该能竞争成功；
QMode = 2，并且_cxq非空：取_cxq队列排头位置的ObjectWaiter对象，调用ExitEpilog方法，该方法会唤醒ObjectWaiter对象的线程，此处会立即返回，后面的代码不会执行了；
QMode = 3，并且_cxq非空：把_cxq队列首元素放入_EntryList的尾部；
QMode = 4，并且_cxq非空：把_cxq队列首元素放入_EntryList的头部；
QMode = 0，不做什么，继续往下看；
只有QMode=2的时候会提前返回，等于0、3、4的时候都会继续往下执行：
如果_EntryList的首元素非空，就取出来调用ExitEpilog方法，该方法会唤醒ObjectWaiter对象的线程，然后立即返回；
如果_EntryList的首元素为空，就取_cxq的首元素，放入_EntryList，然后再从_EntryList中取出来执行ExitEpilog方法，然后立即返回；
```
```text
汇总：
线程A在wait() 后被加入了_WaitSet队列中；
线程C被线程B启动后竞争锁失败，被加入到_cxq队列的首位；
线程B在notify()时，从_WaitSet中取出第一个，根据Policy的不同，将这个线程放入_EntryList或者_cxq队列中的起始或末尾位置；
根据QMode的不同，将ObjectWaiter从_cxq或者_EntryList中取出后唤醒；
```
实际 Policy=2 QMode=0

Java的wait()、notify()学习三部曲之一：JVM源码分析 https://blog.csdn.net/boling_cavalry/article/details/77793224
Java的wait()、notify()学习三部曲之二：修改JVM源码看参数 https://blog.csdn.net/boling_cavalry/article/details/77897108
Java的wait()、notify()学习三部曲之三：修改JVM源码控制抢锁顺序 https://blog.csdn.net/boling_cavalry/article/details/77995069


静态方法Thread.interrupted()获取线程中断状态后，会重置中断状态为false；
非静态方法Thread.currentThread().isInterrupted()获取线程中断状态后，不会重置中断状态。

final Lock lock = new ReentrantLock(); // 可重入
final Condition notFull  = lock.newCondition();
notFull.await(); 调用await方法后，将当前线程加入Condition等待队列中。当前线程释放锁。否则别的线程就无法拿到锁而发生死锁。自旋(while)挂起，不断检测节点是否在同步队列中了，如果是则尝试获取锁，否则挂起。当线程被signal方法唤醒，被唤醒的线程将从await()方法中的while循环中退出来，然后调用acquireQueued()方法竞争同步状态。
ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
读写锁互斥, 可重入, 支持降级(写锁降级成读锁)
StampedLock StampedLock = new StampedLock();
写锁/悲观读锁 互斥,乐观读(可以有线程获取读锁); 不支持重入, 不支持条件变量, 如果线程阻塞在 StampedLock 的 readLock() 或者 writeLock() 上时，此时调用该阻塞线程的 interrupt() 方法，会导致 CPU 飙升
如果需要支持中断功能，一定使用可中断的悲观读锁 readLockInterruptibly() 和写锁 writeLockInterruptibly()
锁降级stampedLock.tryConvertToWriteLock(stamp);, 锁升级 long wl = stampedLock.tryConvertToWriteLock(stamp); //会自动释放stamp的锁, 需要手动释放,重新获取的锁wl
```java
public class StampedLockMain {
    public static void main(String[] args) {
        final StampedLock sl = new StampedLock();
          // 乐观读
        long stamp = sl.tryOptimisticRead();
          // 读入方法局部变量
        // ......
        // 校验stamp
        if (!sl.validate(stamp)) {
            // 升级为悲观读锁
            stamp = sl.readLock();
            try {
                // 读入方法局部变量
               //  .....
            } finally {
                //释放悲观读锁
                sl.unlockRead(stamp);
            }
        }
          //使用方法局部变量执行业务操作
          //......
    }
}
```
















