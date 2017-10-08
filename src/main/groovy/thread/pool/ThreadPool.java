package thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sajedur on 10/30/2016.
 */
class ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(5);
        while (true) {
            for (int i = 0; i < 10; i++) {
                executor.execute(runnable);
            }
            Thread.sleep(2000 * 10);
        }
    }
}
