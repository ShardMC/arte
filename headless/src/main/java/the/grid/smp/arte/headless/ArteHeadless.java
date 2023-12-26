package the.grid.smp.arte.headless;

import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.pack.manager.PackManager;
import the.grid.smp.arte.common.util.Util;
import the.grid.smp.arte.common.zip.Zip;
import the.grid.smp.arte.common.zip.ZipDirectoryTask;
import the.grid.smp.arte.headless.config.HeadlessArteConfig;
import the.grid.smp.arte.headless.logger.HeadlessArteLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class ArteHeadless implements Arte {

    private final ArteLogger logger = new HeadlessArteLogger(Logger.getLogger("headless"));

    private final HeadlessArteConfig config = new HeadlessArteConfig(this);

    public static void main(String[] args) throws InterruptedException {
        //Thread.sleep(20_000L);
        new ArteHeadless();
    }

    public ArteHeadless() {
        /*new Thread(() -> new HeadlessPackManager(this)).start();*/

        Path root = this.getDataFolder()
                .toPath().resolve("resourcepack");

        Path output = this.getDataFolder()
                .toPath().resolve("generated");

        /*long fork = 0;
        long naive = 0;

        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            zipFork(root, output);
            fork += System.currentTimeMillis() - start;
            start = System.currentTimeMillis();

            zipNative(root, output);
            naive += System.currentTimeMillis() - start;
        }

        System.out.println("Fork: " + fork);
        System.out.println("Native: " + naive);*/

        zipNative(root, output);
        zipFork(root, output);
    }

    private static final ForkJoinPool pool = ForkJoinPool.commonPool();

    private static void zipFork(Path root, Path output) {
        long start = System.currentTimeMillis();

        try (Zip zip = new Zip(root, output.resolve("fork.zip"), false)) {
            ZipDirectoryTask task = new ZipDirectoryTask(zip, root);
            pool.execute(task);

            task.join();
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finished in " + (System.currentTimeMillis() - start) + "ms");
    }

    private static void zipNative(Path root, Path output) {
        try (Zip zip = new Zip(root, output.resolve("native.zip"), false)) {

        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArteLogger logger() {
        return logger;
    }

    @Override
    public ArteConfig config() {
        return config;
    }

    @Override
    public PackManager getPackManager() {
        return null;
    }

    @Override
    public File getDataFolder() {
        return Path.of("headless-input").toFile();
    }

    @Override
    public InputStream getResourceStream(String path) {
        return null;
    }

    @Override
    public File getResourceFile(String path) {
        return null;
    }
}