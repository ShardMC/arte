package io.shardmc.arte.common.pack.meta.file;

import io.shardmc.arte.common.zip.Zip;

import java.nio.file.Path;
import java.util.Collection;

public record Namespace(Path path, Collection<Path> files) implements PackFile {

    @Override
    public void zip(Zip zip) {
        for (Path file : this.files) {
            if (file.getFileName().toString().equals("pack.mcmeta") || file.getFileName().toString().equals("pack.png"))
                zip.addFile(file, Path.of(String.format("%s/%s", file.getParent().getParent().getParent(), file.getFileName())));
            else
                zip.addFile(file);
        }
    }

    @Override
    public Path getPath() {
        return this.path;
    }
}
