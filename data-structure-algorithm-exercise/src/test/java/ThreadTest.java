
/*
 * 创建人：baimiao
 * 创建时间：2024/2/1 16:24
 *
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread t = Thread.currentThread();
                    System.out.println(t.getState());
                    System.out.println(t.isInterrupted());
                    System.out.println(executor.isShutdown());
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });

        executor.shutdown();
    }
}
