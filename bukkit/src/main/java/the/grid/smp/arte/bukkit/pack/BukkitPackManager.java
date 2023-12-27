package the.grid.smp.arte.bukkit.pack;

import org.bukkit.entity.Player;
import the.grid.smp.arte.bukkit.ArtePlugin;
import the.grid.smp.arte.common.pack.manager.PackManager;
import the.grid.smp.arte.common.pack.meta.BuiltPack;
import the.grid.smp.arte.common.rewrite.Zip;
import the.grid.smp.arte.common.util.lambda.PackZipperCreator;
import the.grid.smp.arte.common.web.WebServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class BukkitPackManager extends PackManager {

    public BukkitPackManager(ArtePlugin arte) {
        super(arte);
    }

    public void apply(Player player) {
        this.arte.logger().info("Applying pack to player " + player.getName());
        String prompt = this.arte.config().getPrompt();

        for (BuiltPack pack : this.zipper.getPacks()) {
            player.setResourcePack(pack.uuid(), pack.getAddress(this.server), pack.hash(), prompt, pack.force());
        }
    }
}
