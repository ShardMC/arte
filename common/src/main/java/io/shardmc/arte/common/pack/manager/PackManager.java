package io.shardmc.arte.common.pack.manager;

import io.shardmc.arte.common.Arte;
import io.shardmc.arte.common.config.ArteConfig;
import io.shardmc.arte.common.config.data.FilterList;
import io.shardmc.arte.common.config.data.PackMode;
import io.shardmc.arte.common.pack.zipper.PackZipper;
import io.shardmc.arte.common.util.lambda.PackZipperCreator;
import io.shardmc.arte.common.web.WebServer;

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
        this.server = new WebServer(this.arte);

        this.root = this.arte.getDataFolder()
                .toPath().resolve("resourcepack");

        this.output = this.arte.getDataFolder()
                .toPath().resolve("generated");

        ArteConfig config = this.arte.config();
        this.reload(config.shouldUseCache()
                ? PackMode.CACHED : config.getMode());
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

            this.server.restart(config.getPort());
            this.arte.logger().info("Restarted web-server! (Finished in " + (System.currentTimeMillis() - total) + "ms)");

            this.zipper = zipper.create(this.arte, this.root, this.output);

            FilterList filter = new FilterList(config.getNamespaces(), config.isWhitelist());

            this.zipper.zip(filter, config.shouldScramble(), pack -> {
                try {
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
