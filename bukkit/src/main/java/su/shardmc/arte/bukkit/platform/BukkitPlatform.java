package su.shardmc.arte.bukkit.platform;

import su.shardmc.arte.bukkit.ArtePlugin;
import su.shardmc.arte.bukkit.platform.impl.PaperPlatform;
import su.shardmc.arte.bukkit.platform.impl.SpigotPlatform;
import su.shardmc.arte.common.platform.Platform;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public abstract class BukkitPlatform implements Platform<ArtePlugin> {

    protected ArtePlugin arte;

    public static BukkitPlatform create(ArtePlugin arte) {
        BukkitPlatform result = BukkitPlatform.isPaper() ? new PaperPlatform() : new SpigotPlatform();
        result.init(arte);

        return result;
    }

    private static boolean isPaper() {
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            return true;
        } catch (ClassNotFoundException ignored) { }

        return false;
    }

    @Override
    public void init(ArtePlugin arte) {
        this.arte = arte;
    }

    @Override
    public File getConfigFile() {
        return new File(this.getDataFolder(), "config.yml");
    }

    @Override
    public File getDataFolder() {
        return this.arte.getDataFolder();
    }

    @Override
    public URL getResourceUrl(String path) throws IOException {
        URL url = this.arte.getLoader().getResource(path);

        if (url == null)
            throw new IOException("Couldn't find the default config! The build may be corrupt! Path: " + path);

        return url;
    }

    @Override
    public void applyPack(Object playerObject, UUID id, String address, String hash, boolean force, Object textObject) {
        if (!(playerObject instanceof Player player))
            return;

        if (!(textObject instanceof String text))
            return;

        this.applyPack(player, id, address, hash, force, text);
    }

    public abstract void applyPack(Player player, UUID id, String address, String hash, boolean force, String text);
}
