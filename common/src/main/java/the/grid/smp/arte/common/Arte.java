package the.grid.smp.arte.common;

import the.grid.smp.arte.common.config.ArteConfig;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.pack.manager.PackManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public interface Arte {
    ArteLogger logger();
    ArteConfig config();
    PackManager getPackManager();

    File getDataFolder();
    File getConfigFile();

    default InputStream getResourceStream(String path) throws IOException {
        return new FileInputStream(this.getResourceFile(path));
    }

    File getResourceFile(String path) throws IOException;
}
