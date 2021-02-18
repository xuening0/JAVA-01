import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class H_CountdownLatchTest {

    private static int threadCount = 10;
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            final int finalI = i;
            exec.execute(() -> {
                try {
                    test(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main 结束...");
    }

    private static void test(int i) throws InterruptedException {
        Thread.sleep(100);
        System.out.println(i + " 执行完成...");
    }
}
