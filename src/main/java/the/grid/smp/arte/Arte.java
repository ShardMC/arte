package the.grid.smp.arte;

import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import the.grid.smp.arte.command.ArteCommand;
import the.grid.smp.arte.config.ArteConfig;
import the.grid.smp.arte.listener.PlayerListener;
import the.grid.smp.arte.manager.PackManager;

public final class Arte extends JavaPlugin {

    private static Arte instance;
    private ArteConfig config;
    private PackManager packManager;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.config = new ArteConfig(this);
        this.config.reload();

        this.command("arte", new ArteCommand(this));
        this.packManager = new PackManager(this);

        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public ArteConfig config() {
        return config;
    }

    public PackManager getPackManager() {
        return packManager;
    }

    public static Arte getInstance() {
        return instance;
    }

    private void command(String name, TabExecutor executor) {
        PluginCommand command = this.getCommand(name);

        if (command == null)
            return;

        command.setTabCompleter(executor);
        command.setExecutor(executor);
    }
}
