import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class G_StampedLockTest {

    private static int count;

    public static void main(String[] args) {
        StampedLock stampedLock = new StampedLock();
        for (int i = 0; i < 4; i++) {
            new Thread(() -> addOne(stampedLock), "thread " + i).start();
        }

    }

    private static void addOne(StampedLock stampedLock) {

        long stamp = stampedLock.tryOptimisticRead();
//        long wl = stampedLock.tryConvertToWriteLock(stamp);

        //判断执行读操作期间,
        //是否存在写操作，如果存在，
        //则sl.validate返回false
        if (!stampedLock.validate(stamp)) {
            //升级为悲观读锁
            long readLock = stampedLock.readLock();
            try {
//                count += 2;
                System.out.println(Thread.currentThread().getName() + " count: " + count);
            } finally {
                stampedLock.unlockRead(readLock);
            }
        } else {
            long sl = stampedLock.writeLock();
            try {
                count += 1;
                System.out.println("没有修改 "+Thread.currentThread().getName() + " count: " + count);
            } finally {
                stampedLock.unlock(sl);
            }
        }
    }

}
