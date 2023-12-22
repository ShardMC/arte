package the.grid.smp.arte.common.util;

import the.grid.smp.arte.common.logger.ArteLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private final ArteLogger logger;
    private final List<Callable<Void>> threads = new ArrayList<>();

    public ThreadPool(ArteLogger logger) {
        this.logger = logger;
    }

    public void add(Callable<Void> runnable) {
        this.threads.add(runnable);
    }

    public void add(Runnable runnable) {
        this.add(() -> {
            runnable.run();
            return null;
        });
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        try {
            executor.invokeAll(this.threads);
            executor.shutdown();
        } catch(InterruptedException e) {
            this.logger.throwing(e);
        }
    }
}
