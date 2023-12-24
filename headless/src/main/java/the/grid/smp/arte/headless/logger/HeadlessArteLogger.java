package the.grid.smp.arte.headless.logger;

import the.grid.smp.arte.common.logger.ArteLogger;

import java.util.logging.Logger;

public class HeadlessArteLogger implements ArteLogger {

    private final Logger logger;

    public HeadlessArteLogger(Logger logger) {
        this.logger = logger;
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
