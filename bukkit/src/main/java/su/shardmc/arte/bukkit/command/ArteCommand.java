package su.shardmc.arte.bukkit.command;

import su.shardmc.arte.bukkit.ArtePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("NullableProblems")
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

                    for (Player player : this.arte.getServer().getOnlinePlayers()) {
                        this.arte.getPackManager().apply(player);
                    }
                });
            }).start();
        }

        if (args[0].equals("clean")) {
            new Thread(() -> {
                try {
                    this.arte.getPackManager().getZipper().clean();

                    scheduler.runTask(this.arte, () ->
                            sender.sendMessage("[Arte] Done!")
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
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
