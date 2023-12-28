package io.shardmc.arte.common.logger;

public interface ArteLogger {
    void info(String... message);
    void warning(String... message);
    void error(String... message);
    void throwing(Throwable throwable, String... message);
}
