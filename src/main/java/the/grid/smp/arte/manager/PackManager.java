package the.grid.smp.arte.manager;

import org.bukkit.entity.Player;
import the.grid.smp.arte.Arte;
import the.grid.smp.arte.web.WebServer;
import the.grid.smp.arte.util.Util;
import the.grid.smp.arte.zip.Zip;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PackManager {

    private final Path resourcepack;
    private final Path generated;

    private final Arte arte;
    private final WebServer server;

    private final List<ResourcePack> packs = new ArrayList<>();

    public PackManager(Arte arte) {
        this.arte = arte;
        this.server = new WebServer();
        this.arte.getLogger().info("Initialized web-server!");

        this.resourcepack = arte.getDataFolder()
                .toPath().resolve("resourcepack");

        this.generated = arte.getDataFolder()
                .toPath().resolve("generated");

        this.arte.getServer().getScheduler().runTaskAsynchronously(this.arte, this::reload);
    }

    public void reload() {
        new Thread(() -> {
            try {
                this.packs.clear();
                Files.createDirectories(this.resourcepack);
                Files.createDirectories(this.generated);

                this.server.restart(this.arte.config().getPort());
                this.rezip();

                Util.walk(this.generated, (file, attrs) -> {
                    ResourcePack pack = new ResourcePack(file, this.arte.config().force());
                    this.packs.add(pack);

                    this.server.host(pack);
                    return FileVisitResult.CONTINUE;
                });

                this.arte.getLogger().info("Finished re-zipping!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void rezip() throws IOException {
        Path assets = this.resourcepack.resolve("assets");
        Files.createDirectories(assets);

        try (Stream<Path> stream = Files.list(assets)) {
            stream.forEach(this::tryRezip);
        }
    }

    private void tryRezip(Path namespace) {
        try {
            this.rezip(namespace);
        } catch (IOException e) {
            this.arte.getLogger().severe("Failed to re-zip namespace " + namespace);
            e.printStackTrace();
        }
    }

    private void rezip(Path namespace) throws IOException {
        this.arte.getLogger().info("Zipping " + Util.getName(namespace));

        try (Zip zip = this.makeZip(namespace)) {
            zip.add(namespace);

            zip.add(this.resourcepack.resolve("pack.mcmeta"));
            zip.add(this.resourcepack.resolve("pack.png"));
        }
    }

    private Zip makeZip(Path namespace) throws FileNotFoundException {
        Path name = this.generated.resolve(namespace.getFileName().toString() + ".zip");
        return new Zip(this.resourcepack, name, this.arte.config().shouldScramble());
    }

    public void apply(Player player) {
        this.arte.getLogger().info("Applying pack to player " + player.getName());
        String prompt = this.arte.config().getPrompt();

        for (ResourcePack pack : this.packs) {
            pack.apply(player, this.server, prompt);
        }
    }
}
