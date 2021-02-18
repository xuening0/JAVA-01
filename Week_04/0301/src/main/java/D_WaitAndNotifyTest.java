import java.util.concurrent.TimeUnit;

public class D_WaitAndNotifyTest {
    private static int i = 0;
    public synchronized void incr() {
        i++;
        System.out.println(Thread.currentThread().getName()+ " 线程执行incr()");
        notifyAll();
    }

    public static synchronized void main(String[] args) throws InterruptedException {
        D_WaitAndNotifyTest mainClass = new D_WaitAndNotifyTest();

        Thread subThread = new Thread(mainClass::incr, "subThread");
        subThread.start();

        synchronized (mainClass) {
            mainClass.wait();
        }
        System.out.println("执行结果i= " + i);
    }
}
