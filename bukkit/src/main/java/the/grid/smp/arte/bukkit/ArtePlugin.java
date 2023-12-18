package the.grid.smp.arte.bukkit;

import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import the.grid.smp.arte.bukkit.command.ArteCommand;
import the.grid.smp.arte.bukkit.config.BukkitArteConfig;
import the.grid.smp.arte.bukkit.listener.PlayerListener;
import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.pack.manager.PackManager;
import the.grid.smp.arte.common.pack.manager.impl.AsyncPackManager;

public final class ArtePlugin extends JavaPlugin implements Arte {

    private BukkitArteConfig config;
    private PackManager packManager;

    @Override
    public void onEnable() {
        this.config = new BukkitArteConfig(this);
        this.config.reload();

        this.command("arte", new ArteCommand(this));
        this.packManager = new AsyncPackManager(this);

        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        this.packManager.stop();
    }

    @Override
    public BukkitArteConfig config() {
        return config;
    }

    @Override
    public PackManager getPackManager() {
        return packManager;
    }

    private void command(String name, TabExecutor executor) {
        PluginCommand command = this.getCommand(name);

        if (command == null)
            return;

        command.setTabCompleter(executor);
        command.setExecutor(executor);
    }
}
