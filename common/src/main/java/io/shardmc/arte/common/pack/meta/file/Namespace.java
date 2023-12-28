package io.shardmc.arte.common.pack.meta.file;

import io.shardmc.arte.common.zip.Zip;

import java.nio.file.Path;
import java.util.Collection;

public record Namespace(String name, Collection<Path> files) implements PackFile {

    @Override
    public void zip(Zip zip) {
        for (Path file : this.files) {
            zip.addFile(file);
        }
    }
}
