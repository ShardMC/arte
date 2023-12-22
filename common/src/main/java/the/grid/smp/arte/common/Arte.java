package the.grid.smp.arte.common;

import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.pack.manager.PackManager;

import java.io.File;

public interface Arte {
    ArteLogger logger();
    ArteConfig config();
    PackManager getPackManager();

    File getDataFolder();
}
