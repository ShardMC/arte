package io.shardmc.arte.bukkit.logger;

import org.bukkit.plugin.Plugin;
import io.shardmc.arte.common.logger.ArteLogger;

import java.util.logging.Logger;

public class BukkitArteLogger implements ArteLogger {

    private final Logger logger;

    public BukkitArteLogger(Logger logger) {
        this.logger = logger;
    }

    public BukkitArteLogger(Plugin plugin) {
        this(plugin.getLogger());
    }

    @Override
    public void info(String... message) {
        this.logger.info(String.join(" ", message));
    }

    @Override
    public void warning(String... message) {
        this.logger.warning(String.join(" ", message));
    }

    @Override
    public void error(String... message) {
        this.logger.severe(String.join(" ", message));
    }

    @Override
    public void throwing(Throwable throwable, String... message) {
        this.error(message);
        throwable.printStackTrace();
    }
}
