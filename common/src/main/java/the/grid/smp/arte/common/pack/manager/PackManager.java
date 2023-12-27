package the.grid.smp.arte.common.pack.manager;

import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.data.FilterList;
import the.grid.smp.arte.common.data.PackMode;
import the.grid.smp.arte.common.pack.zipper.PackZipper;
import the.grid.smp.arte.common.util.lambda.PackZipperCreator;
import the.grid.smp.arte.common.web.WebServer;

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

        this.reload();
    }

    public void reload() {
        this.reload(this.arte.config().getMode());
    }

    protected void reload(PackMode mode) {
        this.reload(mode.creator());
    }

    protected void reload(PackZipperCreator zipper) {
        this.arte.logger().info("Initializing pack manager reload!");

        try {
            long total = System.currentTimeMillis();
            ArteConfig config = this.arte.config();

            new Thread(() -> {
                this.server.restart(config.getPort());
                this.arte.logger().info("Restarted web-server! (Finished in " + (System.currentTimeMillis() - total) + "ms)");
            }).start();

            this.zipper = zipper.create(this.arte, this.root, this.output);

            FilterList filter = new FilterList(config.getNamespaces(), config.isWhitelist());
            this.zipper.zip(filter, config.shouldScramble(), pack -> {
                try {
                    this.arte.logger().info("Processing pack part", pack.getName(), "...");
                    this.server.host(pack);
                } catch (IOException e) {
                    this.arte.logger().throwing(e, "Failed to host pack", pack.toString(), "...");
                }
            });

            this.arte.logger().info("Finished reloading pack manager in " + (System.currentTimeMillis() - total) + "ms!");
        } catch (IOException e) {
            this.arte.logger().throwing(e, "Failed to reload pack manager!");
        }
    }

    public PackZipper getZipper() {
        return zipper;
    }
}
