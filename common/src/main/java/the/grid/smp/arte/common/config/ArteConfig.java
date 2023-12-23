package the.grid.smp.arte.common.config;

import the.grid.smp.arte.common.data.PackMode;
import the.grid.smp.arte.common.logger.ArteLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public abstract class ArteConfig {

    protected final ArteLogger logger;
    protected final Path file;

    protected int port;
    protected String address;

    protected PackMode mode;
    protected boolean scramble;

    protected boolean repackOnStart;
    protected String prompt;

    protected Set<String> namespaces;
    protected boolean whitelist;

    protected List<List<String>> groups;

    public ArteConfig(ArteLogger logger, Path file) {
        this.logger = logger;
        this.file = file;
    }

    public ArteConfig(ArteLogger logger, File path) {
        this(logger, path.toPath());
    }

    protected abstract void read() throws IOException;
    protected abstract void write() throws IOException;
    protected abstract void defaults() throws IOException;

    protected abstract void create() throws IOException;
    protected abstract void dump() throws IOException;

    private void saveDefault() throws IOException {
        String name = this.file.getFileName().toString();
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(name)) {
            if (stream == null)
                throw new IOException("Couldn't find the default config! The build may be corrupt!");

            Files.copy(stream, this.file);
        }
    }

    public void reload() {
        try {
            if (Files.notExists(this.file))
                this.saveDefault();

            this.create();
            this.defaults();
            this.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Files.createDirectories(this.file.getParent());

            if (Files.notExists(this.file)) {
                this.saveDefault();
                this.reload();
            }

            this.write();
            this.dump();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public PackMode getMode() {
        return mode;
    }

    public boolean shouldScramble() {
        return scramble;
    }

    public boolean repackOnStart() {
        return repackOnStart;
    }

    public String getPrompt() {
        return prompt;
    }

    public Set<String> getNamespaces() {
        return namespaces;
    }

    public boolean isWhitelist() {
        return whitelist;
    }

    public List<List<String>> getGroups() {
        return groups;
    }
}
