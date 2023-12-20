package the.grid.smp.arte.bukkit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import the.grid.smp.arte.bukkit.ArtePlugin;
import the.grid.smp.arte.bukkit.pack.BukkitPackManager;

import java.io.IOException;
import java.util.List;

public class ArteCommand implements TabExecutor {

    private final ArtePlugin arte;

    public ArteCommand(ArtePlugin arte) {
        this.arte = arte;
        PluginCommand command = arte.getCommand("arte");

        if (command == null)
            return;

        command.setTabCompleter(this);
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        BukkitScheduler scheduler = this.arte.getServer().getScheduler();

        if (!sender.hasPermission("arte.command"))
            return true;

        if (args[0].equals("reload")) {
            this.arte.config().reload();

            new Thread(() -> {
                this.arte.getPackManager().reload();

                scheduler.runTask(this.arte, () -> {
                    sender.sendMessage("[Arte] Done!");

                    if (!(this.arte.getPackManager() instanceof BukkitPackManager bukkit))
                        return;

                    for (Player player : this.arte.getServer().getOnlinePlayers()) {
                        bukkit.apply(player);
                    }
                });
            }).start();
        }

        if (args[0].equals("clean")) {
            new Thread(() -> {
                try {
                    this.arte.getPackManager().getZipper().clear();

                    scheduler.runTask(this.arte, () ->
                            sender.sendMessage("[Arte] Done!")
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("arte.command"))
            return List.of();

        if (args.length == 1)
            return List.of("reload", "clean");

        return List.of();
    }
}
