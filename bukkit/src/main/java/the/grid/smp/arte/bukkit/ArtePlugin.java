package the.grid.smp.arte.bukkit;

import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import the.grid.smp.arte.bukkit.command.ArteCommand;
import the.grid.smp.arte.bukkit.config.BukkitArteConfig;
import the.grid.smp.arte.bukkit.listener.PlayerListener;
import the.grid.smp.arte.bukkit.logger.BukkitArteLogger;
import the.grid.smp.arte.bukkit.pack.BukkitPackManager;
import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.logger.ArteLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        this.packManager = new BukkitPackManager(this);

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
    public InputStream getResourceStream(String path) {
        return this.getResource(path);
    }

    @Override
    public File getResourceFile(String path) throws IOException {
        URL url = this.getClassLoader().getResource(path);

        if (url == null)
            throw new IOException("Couldn't find the default config! The build may be corrupt! Path: " + path);

        return new File(url.getFile());
    }

    private void command(String name, TabExecutor executor) {
        PluginCommand command = this.getCommand(name);

        if (command == null)
            return;

        command.setTabCompleter(executor);
        command.setExecutor(executor);
    }
}
