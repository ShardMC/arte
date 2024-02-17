package io.shardmc.arte.common.config;

import io.shardmc.arte.common.Arte;

import java.util.Collection;

public abstract class ArteConfigAdapter<R> {

    protected final Arte arte;

    protected R file;
    protected R defaults;

    public ArteConfigAdapter(Arte arte) {
        this.arte = arte;
    }

    protected abstract <T> T read(R r, String name);
    protected abstract <T> Collection<T> readList(R r, String name);

    protected abstract void write(R r, String name, Object value);

    protected abstract boolean has(R r, String key);

    public <T> T read(String name) {
        if (this.has(this.file, name))
            return this.read(this.file, name);

        return this.read(this.defaults, name);
    }

    public <T> Collection<T> readList(String name) {
        if (this.has(this.file, name))
            return this.readList(this.file, name);

        return this.readList(this.defaults, name);
    }

    public void write(String name, Object value) {
        if (this.has(this.file, name)) {
            this.write(this.file, name, value);
            return;
        }

        this.write(this.defaults, name, value);
    }

    public void update(R r) {
        this.file = r;
    }

    public void updateDefaults(R r) {
        this.defaults = r;
    }
}
