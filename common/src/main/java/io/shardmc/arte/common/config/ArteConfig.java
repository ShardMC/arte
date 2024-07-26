package io.shardmc.arte.common.config;

import io.shardmc.arte.common.Arte;
import io.shardmc.arte.common.config.data.PackMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public abstract class ArteConfig {

    protected final Arte arte;
    protected final Path file;

    protected int port;
    protected String address;

    protected String prompt;
    protected PackMode mode;
    protected boolean scramble;
    protected boolean useCache;

    protected Set<String> namespaces;
    protected boolean whitelist;

    public ArteConfig(Arte arte, boolean reload) {
        this.arte = arte;
        this.file = arte.platform().getConfigFile().toPath();

        if (reload)
            this.reload();
    }

    public ArteConfig(Arte arte) {
        this(arte, true);
    }

    protected abstract void read();
    protected abstract void write();

    protected abstract void defaults() throws IOException;

    protected abstract void create() throws IOException;
    protected abstract void dump() throws IOException;

    protected InputStream getResource(Path path) throws IOException {
        return this.arte.platform().getResourceStream(path.getFileName().toString());
    }

    protected File getResourceFile(Path path) throws IOException {
        return this.arte.platform().getResourceFile(path.getFileName().toString());
    }

    private void saveDefault() throws IOException {
        this.arte.logger().info("Config file doesn't exist! Copying from files...");

        Files.createDirectories(this.file.getParent());
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
            this.arte.logger().throwing(e, "Failed to reload config.");
        }
    }

    public void save() {
        try {
            if (Files.notExists(this.file)) {
                this.saveDefault();
                this.reload();
            }

            this.write();
            this.dump();
        } catch (IOException e) {
            this.arte.logger().throwing(e, "Failed to save config.");
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

    public boolean shouldUseCache() {
        return useCache;
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
}
