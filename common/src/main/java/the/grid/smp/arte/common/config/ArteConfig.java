package the.grid.smp.arte.common.config;

import the.grid.smp.arte.common.data.PackMode;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public abstract class ArteConfig {

    protected final ArteLogger logger;
    protected final Path file;

    protected int port;
    protected String address;

    protected String prompt;
    protected PackMode mode;
    protected boolean scramble;

    protected Set<String> namespaces;
    protected boolean whitelist;

    protected List<List<String>> groups;

    public ArteConfig(ArteLogger logger, Path file, boolean reload) {
        this.logger = logger;
        this.file = file;

        if (reload)
            this.reload();
    }

    public ArteConfig(ArteLogger logger, Path file) {
        this(logger, file, true);
    }

    public ArteConfig(ArteLogger logger, File path) {
        this(logger, path.toPath());
    }

    protected abstract void read() throws IOException;
    protected abstract void write() throws IOException;
    protected abstract void defaults() throws IOException;

    protected abstract void create() throws IOException;
    protected abstract void dump() throws IOException;

    protected InputStream getResource(Path path) throws IOException {
        String name = "/" + path.getFileName().toString();
        InputStream stream = Util.class.getClassLoader().getResourceAsStream(name);

        if (stream == null)
            throw new IOException("Couldn't find the default config! The build may be corrupt! Path: " + name);

        return stream;
    }

    protected File getResourceFile(Path path) throws IOException {
        String name = "/" + path.getFileName().toString();
        URL url = Util.class.getClassLoader().getResource(name);

        if (url == null)
            throw new IOException("Couldn't find the default config! The build may be corrupt! Path: " + name);

        return new File(url.getFile());
    }

    private void saveDefault() throws IOException {
        this.logger.info("Config file doesn't exist! Copying from files...");
        try (InputStream stream = this.getResource(this.file)) {
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
            this.logger.throwing(e, "Failed to reload config.");
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
            this.logger.throwing(e, "Failed to save config.");
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
