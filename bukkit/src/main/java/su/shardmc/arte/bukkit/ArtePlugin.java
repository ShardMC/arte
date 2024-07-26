package su.shardmc.arte.bukkit;

import su.shardmc.arte.bukkit.config.BukkitArteConfig;
import su.shardmc.arte.bukkit.logger.BukkitArteLogger;
import su.shardmc.arte.bukkit.pack.BukkitPackManager;
import su.shardmc.arte.bukkit.platform.BukkitPlatform;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import su.shardmc.arte.bukkit.command.ArteCommand;
import su.shardmc.arte.bukkit.listener.PlayerListener;
import su.shardmc.arte.common.Arte;
import su.shardmc.arte.common.config.ArteConfig;
import su.shardmc.arte.common.logger.ArteLogger;

public final class ArtePlugin extends JavaPlugin implements Arte {

    private BukkitPlatform platform;

    private ArteLogger logger;
    private ArteConfig config;

    private BukkitPackManager packManager;

    @Override
    public void onEnable() {
        this.platform = BukkitPlatform.create(this);

        this.logger = new BukkitArteLogger(this);
        this.config = new BukkitArteConfig(this);

        this.packManager = new BukkitPackManager(this);

        this.command("arte", new ArteCommand(this));
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        this.config.save();
    }

    @Override
    public ArteLogger logger() {
        return this.logger;
    }

    @Override
    public ArteConfig config() {
        return this.config;
    }

    @Override
    public BukkitPackManager getPackManager() {
        return this.packManager;
    }

    @Override
    public BukkitPlatform platform() {
        return this.platform;
    }

    public ClassLoader getLoader() {
        return super.getClassLoader();
    }

    private void command(String name, TabExecutor executor) {
        PluginCommand command = this.getCommand(name);

        if (command == null)
            return;

        command.setTabCompleter(executor);
        command.setExecutor(executor);
    }
}
