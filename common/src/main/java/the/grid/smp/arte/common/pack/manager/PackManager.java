package the.grid.smp.arte.common.pack.manager;

import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.pack.zipper.PackZipper;
import the.grid.smp.arte.common.util.lambda.PackZipperCreator;
import the.grid.smp.arte.common.web.WebServer;
import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.data.FilterList;
import the.grid.smp.arte.common.data.PackMode;

import java.io.IOException;
import java.nio.file.Path;

public class PackManager {

    protected final Arte arte;
    protected final WebServer server;

    protected final Path root;
    protected final Path output;

    protected PackZipper zipper;

    public PackManager(Arte arte) {
        this.arte = arte;
        this.server = new WebServer(this.arte.config().getAddress());
        this.arte.logger().info("Initialized web-server!");

        this.root = this.arte.getDataFolder()
                .toPath().resolve("resourcepack");

        this.output = this.arte.getDataFolder()
                .toPath().resolve("generated");

        if (this.arte.config().repackOnStart()) {
            this.reload();
            return;
        }

        this.load();
    }

    public void load() {
        this.reload(PackMode.CACHED);
    }

    public void reload() {
        this.reload(this.arte.config().getMode());
    }

    private void reload(PackMode mode) {
        this.reload(mode.creator());
    }

    protected void reload(PackZipperCreator zipper) {
        try {
            long total = System.currentTimeMillis();
            ArteConfig config = this.arte.config();

            new Thread(() -> {
                this.server.restart(config.getPort());
                this.arte.logger().info("Restarted web-server! (Finished in " + (System.currentTimeMillis() - total) + "ms)");
            }).start();

            this.zipper = zipper.create(this.arte, this.root, this.output);

            FilterList filter = new FilterList(config.getNamespaces(), config.isWhitelist());
            this.zipper.zip(filter, config.scramble(), pack -> {
                try {
                    this.server.host(pack);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            this.arte.logger().info("Finished reloading pack manager in " + (System.currentTimeMillis() - total) + "ms!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        if (this.arte.config().repackOnStart())
            return;

        try {
            if (this.zipper != null)
                this.zipper.clean();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PackZipper getZipper() {
        return zipper;
    }
}
