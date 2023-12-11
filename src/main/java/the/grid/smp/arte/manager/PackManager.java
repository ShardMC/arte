package the.grid.smp.arte.manager;

import org.bukkit.entity.Player;
import the.grid.smp.arte.Arte;
import the.grid.smp.arte.web.WebServer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PackManager {

    private final Arte arte;
    private final WebServer server;

    private final List<ResourcePack> packs = new ArrayList<>();
    private final GlobalServerPack globalPack;

    public PackManager(Arte arte) {
        this.arte = arte;
        this.server = new WebServer();
        this.arte.getLogger().info("Initialized web-server!");

        Path resourcepack = arte.getDataFolder()
                .toPath().resolve("resourcepack");

        Path generated = arte.getDataFolder()
                .toPath().resolve("generated");

        this.globalPack = new GlobalServerPack(this.arte.getLogger(), resourcepack, generated);
        this.arte.getServer().getScheduler().runTaskAsynchronously(this.arte, this::reload);
    }

    public void reload() {
        new Thread(() -> {
            try {
                this.packs.clear();
                long start = System.currentTimeMillis();

                this.server.restart(this.arte.config().getPort());
                this.globalPack.rezip(this.arte.config().getGroups(), this.arte.config().shouldScramble());

                Set<String> namespaces = this.arte.config().getNamespaces();
                boolean isWhitelist = this.arte.config().isWhitelist();

                this.globalPack.collect(namespaces, isWhitelist, pack -> {
                    this.packs.add(pack);
                    this.server.host(pack);
                    return FileVisitResult.CONTINUE;
                });

                this.arte.getLogger().info("Finished re-zipping! (Finished in " + (System.currentTimeMillis() - start) + "ms)");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void apply(Player player) {
        this.arte.getLogger().info("Applying pack to player " + player.getName());
        String prompt = this.arte.config().getPrompt();

        for (ResourcePack pack : this.packs) {
            pack.apply(player, this.server, prompt);
        }
    }
}
