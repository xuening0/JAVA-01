import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class E_SemaphoreTest {

    private static final Semaphore semaphore = new Semaphore(0);
    private static int count;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(E_SemaphoreTest::addOne, "thread1");
//        Thread thread2 = new Thread(E_SemaphoreTest::addOne, "thread2");
        thread.start();
//        thread2.start();
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        semaphore.acquire();
        System.out.println("count: "+ getCount());
    }

    private static void addOne(){
        try {
            Thread.sleep(500);
            count += 1;
            System.out.println(Thread.currentThread().getName() + " count: " + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private static int getCount() {
        return count;
    }
}
