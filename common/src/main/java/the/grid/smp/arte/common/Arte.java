package the.grid.smp.arte.common;

import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.pack.manager.PackManager;

import java.io.File;
import java.util.logging.Logger;

public interface Arte {
    Logger getLogger();
    ArteConfig config();
    PackManager getPackManager();

    File getDataFolder();
}
