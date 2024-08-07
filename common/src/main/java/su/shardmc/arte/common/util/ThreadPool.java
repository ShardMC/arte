package su.shardmc.arte.common.util;

import su.shardmc.arte.common.logger.ArteLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private final ArteLogger logger;
    private final ExecutorService executor;

    public ThreadPool(ArteLogger logger) {
        this(logger, Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    public ThreadPool(ArteLogger logger, ExecutorService executor) {
        this.logger = logger;
        this.executor = executor;
    }

    public void add(Runnable runnable) {
        this.executor.execute(runnable);
    }

    public void start() {
        try {
            this.executor.shutdown();
            this.executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);

            this.executor.shutdownNow();
        } catch(InterruptedException e) {
            this.logger.throwing(e);
        }
    }
}
