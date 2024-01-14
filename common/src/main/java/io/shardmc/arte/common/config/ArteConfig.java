package io.shardmc.arte.common.config;

import io.shardmc.arte.common.Arte;
import io.shardmc.arte.common.config.data.PackMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public abstract class ArteConfig<T> {

    protected final Arte arte;
    protected final Path file;

    protected final ArteConfigAdapter<T> serializer;

    protected int port;
    protected String address;

    protected String prompt;
    protected PackMode mode;

    protected boolean useCache;
    protected boolean scramble;

    protected Set<String> namespaces;
    protected boolean whitelist;

    public ArteConfig(Arte arte, boolean reload) {
        this.arte = arte;
        this.file = arte.getConfigFile().toPath();

        this.serializer = this.serializer();

        if (reload)
            this.reload();
    }

    public ArteConfig(Arte arte) {
        this(arte, true);
    }

    protected void read() {
        this.port = this.serializer.read("port");
        this.address = this.serializer.read("address");

        this.prompt = this.serializer.read("prompt");
        this.mode = PackMode.valueOf(this.serializer.read("mode"));

        this.useCache = this.serializer.read("useCache");
        this.scramble = this.serializer.read("scramble");

        this.namespaces = new HashSet<>(this.serializer.readList("namespaces"));
        this.whitelist = this.serializer.read("whitelist");
    }

    protected void write() {
        this.serializer.write("port", this.port);
        this.serializer.write("address", this.address);

        this.serializer.write("prompt", this.prompt);
        this.serializer.write("mode", this.mode.toString());

        this.serializer.write("use-cache", this.useCache);
        this.serializer.write("scramble", this.scramble);

        this.serializer.write("namespaces", this.namespaces);
        this.serializer.write("whitelist", this.whitelist);
    }

    protected abstract T defaults() throws IOException;

    protected abstract T create() throws IOException;
    protected abstract void dump(T r) throws IOException;

    protected abstract ArteConfigAdapter<T> serializer();

    protected InputStream getResource(Path path) throws IOException {
        return this.arte.getResourceStream(path.getFileName().toString());
    }

    protected File getResourceFile(Path path) throws IOException {
        return this.arte.getResourceFile(path.getFileName().toString());
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

            this.serializer.update(this.create());
            this.serializer.updateDefaults(this.defaults());

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
            this.dump(this.serializer.file);
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

    public String getPrompt() {
        return prompt;
    }

    public boolean shouldUseCache() {
        return useCache;
    }

    public boolean shouldScramble() {
        return scramble;
    }

    public Set<String> getNamespaces() {
        return namespaces;
    }

    public boolean isWhitelist() {
        return whitelist;
    }
}
