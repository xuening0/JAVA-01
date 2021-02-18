import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class F_ReentrantReadWriteLockTest {

    private static int count;

    public static void main(String[] args) {
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        Thread thread = new Thread(() -> addOne(rwLock), "thread1");
        Thread thread2 = new Thread(() -> addOne(rwLock), "thread2");

        thread.start();
        thread2.start();

    }

    private static void addOne(ReentrantReadWriteLock rwLock){
        try {
            rwLock.writeLock().lock();
            count += 1;
            System.out.println(Thread.currentThread().getName() + " count: " + count);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

}
