package the.grid.smp.arte.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private final List<Callable<Void>> threads = new ArrayList<>();

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
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            executor.invokeAll(this.threads);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
