package the.grid.smp.arte.pack;

import org.bukkit.entity.Player;
import the.grid.smp.arte.Arte;
import the.grid.smp.arte.config.ArteConfig;
import the.grid.smp.arte.config.data.FilterList;
import the.grid.smp.arte.pack.meta.BuiltPack;
import the.grid.smp.arte.pack.zipper.PackZipper;
import the.grid.smp.arte.web.WebServer;

import java.io.IOException;
import java.nio.file.Path;

public class PackManager {

    private final Arte arte;
    private final WebServer server;

    private final Path root;
    private final Path output;

    private PackZipper zipper;

    public PackManager(Arte arte) {
        this.arte = arte;
        this.server = new WebServer();
        this.arte.getLogger().info("Initialized web-server!");

        this.root = this.arte.getDataFolder()
                .toPath().resolve("resourcepack");

        this.output = this.arte.getDataFolder()
                .toPath().resolve("generated");

        if (this.arte.config().repackOnStart()) {
            new Thread(this::reload).start();
        }
    }

    public void reload() {
        try {
            long total = System.currentTimeMillis();
            ArteConfig config = this.arte.config();

            new Thread(() -> {
                this.server.restart(config.getPort());
                this.arte.getLogger().info("Restarted web-server! (Finished in " + (System.currentTimeMillis() - total) + "ms)");
            }).start();

            this.zipper = config.getMode().creator().create(
                    this.arte, this.root, this.output
            );

            FilterList filter = new FilterList(config.getNamespaces(), config.isWhitelist());
            this.zipper.zip(filter, config.shouldScramble(), pack -> {
                try {
                    this.server.host(pack);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            this.arte.getLogger().info("Finished reloading pack manager in " + (System.currentTimeMillis() - total) + "ms!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void apply(Player player) {
        this.arte.getLogger().info("Applying pack to player " + player.getName());
        String prompt = this.arte.config().getPrompt();

        for (BuiltPack pack : this.zipper.getPacks()) {
            this.arte.getLogger().info("Applying pack @'" + server.getAddress(pack) + "'");
            pack.apply(player, this.server, prompt);
        }
    }

    public PackZipper getZipper() {
        return zipper;
    }
}
