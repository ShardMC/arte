package the.grid.smp.arte.common.util;

import the.grid.smp.arte.common.logger.ArteLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private final ArteLogger logger;
    private final ExecutorService executor = Executors.newFixedThreadPool(16);

    public ThreadPool(ArteLogger logger) {
        this.logger = logger;
    }

    public void add(Runnable runnable) {
        this.executor.execute(runnable);
    }

    public void start() {
        try {
            this.executor.shutdown();
            this.executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch(InterruptedException e) {
            this.logger.throwing(e);
        }
    }
}
