import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class test {
    public static void main(String[] args) {
        /*ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<Integer> f1 = executor.submit(()->getPriceByS1());
        Future<Integer> f2 = executor.submit(()->getPriceByS2());
        Future<Integer> f3 = executor.submit(()->getPriceByS3());

        executor.execute(()-> {
            try {
                save(f1.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executor.execute(()-> {
            try {
                save(f2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executor.execute(()-> {
            try {
                save(f3.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });*/
        System.out.println(tet清单());
    }

    private static AtomicReference<Integer> tet清单() {

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 创建CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);
        // 异步向电商S1询价
        cs.submit(() -> getPriceByS1());
        // 异步向电商S2询价
        cs.submit(() -> getPriceByS2());
        // 异步向电商S3询价
        cs.submit(() -> getPriceByS3());
        // 将询价结果异步保存到数据库
        // 并计算最低报价
        AtomicReference<Integer> m = new AtomicReference<>(Integer.MAX_VALUE);
        for (int i = 0; i < 3; i++) {
//            executor.execute(() -> {
                Integer r = null;
                try {
                    r = cs.take().get();
                } catch (Exception e) {
                }
                save(r);
                m.set(Integer.min(m.get(), r));
//            });
        }
        return m;
    }

    private static Integer getPriceByS1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private static Integer getPriceByS2() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2;
    }

    private static Integer getPriceByS3() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 3;
    }

    private static void save(Integer i) {
        System.out.println("save " + i);
    }
}
