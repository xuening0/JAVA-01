import java.util.concurrent.TimeUnit;

public class A_SleepTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadA threadA = new ThreadA();
        threadA.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("main 执行结束");
    }
}
