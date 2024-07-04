package io.shardmc.arte.bukkit;

import io.shardmc.arte.bukkit.config.BukkitArteConfig;
import io.shardmc.arte.bukkit.logger.BukkitArteLogger;
import io.shardmc.arte.bukkit.pack.BukkitPackManager;
import io.shardmc.arte.bukkit.platform.BukkitPlatform;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import io.shardmc.arte.bukkit.command.ArteCommand;
import io.shardmc.arte.bukkit.listener.PlayerListener;
import io.shardmc.arte.common.Arte;
import io.shardmc.arte.common.config.ArteConfig;
import io.shardmc.arte.common.logger.ArteLogger;

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
