package the.grid.smp.arte.bukkit.logger;

import the.grid.smp.arte.common.logger.ArteLogger;

import java.util.logging.Logger;

public class BukkitArteLogger implements ArteLogger {
    private final Logger logger;

    public BukkitArteLogger(Logger logger) {
        this.logger = logger;
    }

    public BukkitArteLogger(String name) {
        this(Logger.getLogger(name));
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
        StackTraceElement element = throwable.getStackTrace()[0];
        this.logger.throwing(element.getClassName(), element.getMethodName(), throwable);
    }
}
