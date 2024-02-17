package io.shardmc.arte.bukkit;

import io.shardmc.arte.bukkit.config.BukkitArteConfig;
import io.shardmc.arte.bukkit.logger.BukkitArteLogger;
import io.shardmc.arte.bukkit.pack.base.BukkitPackManager;
import io.shardmc.arte.bukkit.pack.PaperPackManager;
import io.shardmc.arte.bukkit.pack.SpigotPackManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import io.shardmc.arte.bukkit.command.ArteCommand;
import io.shardmc.arte.bukkit.listener.PlayerListener;
import io.shardmc.arte.common.Arte;
import io.shardmc.arte.common.config.ArteConfig;
import io.shardmc.arte.common.logger.ArteLogger;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public final class ArtePlugin extends JavaPlugin implements Arte {

    private ArteLogger logger;
    private ArteConfig config;

    private BukkitPackManager packManager;

    @Override
    public void onEnable() {
        this.logger = new BukkitArteLogger(this);
        this.config = new BukkitArteConfig(this);

        this.command("arte", new ArteCommand(this));

        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            this.packManager = new PaperPackManager(this);
        } catch (ClassNotFoundException e) {
            this.packManager = new SpigotPackManager(this);
        }

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
    public File getConfigFile() {
        return new File(this.getDataFolder(), "config.yml");
    }

    @Override
    public URL getResourceUrl(String path) throws IOException {
        URL url = this.getClassLoader().getResource(path);

        if (url == null)
            throw new IOException("Couldn't find the default config! The build may be corrupt! Path: " + path);

        return url;
    }

    private void command(String name, TabExecutor executor) {
        PluginCommand command = this.getCommand(name);

        if (command == null)
            return;

        command.setTabCompleter(executor);
        command.setExecutor(executor);
    }
}
