import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class I_CyclicBarrierTest {


    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {System.out.println("回调函数执行..");});

        Thread thread = new Thread(() -> addOne(cyclicBarrier), "thread1");
        Thread thread2 = new Thread(() -> addOne(cyclicBarrier), "thread2");

        thread.start();
        thread2.start();
        System.out.println("main end... ");
    }
    private static void addOne(CyclicBarrier cyclicBarrier){
       try{
            System.out.println(Thread.currentThread().getName() + " exec addOne ");
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
