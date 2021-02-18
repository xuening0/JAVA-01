import java.util.concurrent.TimeUnit;

public class C_SynchronizedTest {

    public static synchronized void exec(String name) {
        System.out.println(name +" :调用exec方法...");
        Thread.currentThread().interrupt();
        System.out.println(name +" isInterrupted: "+Thread.currentThread().isInterrupted());
    }
    public static void main(String[] args) {
        Thread sub_thread = new Thread(() -> {
            exec(Thread.currentThread().getName());
        }, "sub_thread");
        sub_thread.start();

        while (sub_thread.isAlive()){

        }
       /*while (!sub_thread.isInterrupted()){

       }*/
        exec(Thread.currentThread().getName());
    }
}
