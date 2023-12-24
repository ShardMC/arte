package the.grid.smp.arte.headless;

import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.pack.manager.PackManager;
import the.grid.smp.arte.headless.config.HeadlessArteConfig;
import the.grid.smp.arte.headless.logger.HeadlessArteLogger;
import the.grid.smp.arte.headless.pack.HeadlessPackManager;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.logging.Logger;

public class ArteHeadless implements Arte {

    private final ArteLogger logger = new HeadlessArteLogger(Logger.getLogger("headless"));

    private final HeadlessArteConfig config = new HeadlessArteConfig(this);

    public static void main(String[] args) throws InterruptedException {
        //Thread.sleep(20_000L);
        new ArteHeadless();
    }

    public ArteHeadless() {
        new Thread(() -> new HeadlessPackManager(this)).start();
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