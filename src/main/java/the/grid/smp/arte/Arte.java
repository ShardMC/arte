package the.grid.smp.arte;

import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import the.grid.smp.arte.command.ArteCommand;
import the.grid.smp.arte.config.ArteConfig;
import the.grid.smp.arte.listener.PlayerListener;
import the.grid.smp.arte.pack.manager.PackManager;
import the.grid.smp.arte.pack.manager.impl.AsyncPackManager;

public final class Arte extends JavaPlugin {

    private ArteConfig config;
    private PackManager packManager;

    @Override
    public void onEnable() {
        this.config = new ArteConfig(this);
        this.config.reload();

        this.command("arte", new ArteCommand(this));
        this.packManager = new AsyncPackManager(this);

        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        this.packManager.stop();
    }

    public ArteConfig config() {
        return config;
    }

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
