package io.shardmc.arte.common.pack.meta.file;

import io.shardmc.arte.common.zip.Zip;

import java.nio.file.Path;

public record BasicPackFile(Path path) implements PackFile {

    @Override
    public void zip(Zip zip) {
        zip.addFile(this.path);
    }

    @Override
    public Path getPath() {
        return this.path;
    }
}
