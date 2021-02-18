import com.sun.org.apache.xalan.internal.utils.FeatureManager;

import java.util.concurrent.*;

public class J_FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<String> future = executorService.submit(() -> "call() 执行结束");
        User user = new User();
        user.setAge(3);
        Future<User> futureUser = executorService.submit(new Task(user), user);
        try {
            System.out.println(future.get());
            System.out.println(futureUser.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1 + 2);
        Future<?> submit = executorService.submit(futureTask);

        System.out.println("futureTask 执行结束.. result: " + futureTask.get());
        System.out.println("futureTask 执行结束.. submit result: " + submit.get());
        System.out.println("main 执行结束..");
        executorService.shutdown();
    }

    static class Task implements Runnable {
        User r; //通过构造函数传入result

        Task(User r) {
            this.r = r;
        }
        @Override
        public void run() { //可以操作
            r.setAge(7);
            r.setName("task");
        }
    }
}
