import java.util.concurrent.TimeUnit;

public class B_JoinTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadA threadA = new ThreadA();
        threadA.start();
        threadA.join();
        System.out.println("main 执行结束");
    }
}
