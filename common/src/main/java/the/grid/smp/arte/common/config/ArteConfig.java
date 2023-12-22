package the.grid.smp.arte.common.config;

import the.grid.smp.arte.common.data.PackMode;

import java.util.List;
import java.util.Set;

public interface ArteConfig {
    int getPort();
    String getAddress();

    PackMode getMode();
    boolean scramble();

    boolean repackOnStart();
    String getPrompt();

    Set<String> getNamespaces();
    boolean isWhitelist();

    List<List<String>> getGroups();
    void reload();
}
