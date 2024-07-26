package su.shardmc.arte.fabric.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.shardmc.arte.common.logger.ArteLogger;

public class FabricArteLogger implements ArteLogger {

    private final Logger logger;

    public FabricArteLogger(Logger logger) {
        this.logger = logger;
    }

    public FabricArteLogger(String name) {
        this(LoggerFactory.getLogger(name));
    }

    @Override
    public void info(String... message) {
        this.logger.info(String.join(" ", message));
    }

    @Override
    public void warning(String... message) {
        this.logger.warn(String.join(" ", message));
    }

    @Override
    public void error(String... message) {
        this.logger.error(String.join(" ", message));
    }

    @Override
    public void throwing(Throwable throwable, String... message) {
        this.logger.error(String.join(" ", message), throwable);
    }
}
