package su.shardmc.arte.common.pack.meta.file;

import su.shardmc.arte.common.zip.Zip;

import java.nio.file.Path;
import java.util.Collection;

public record Namespace(Path root, String name, Collection<Path> files) implements PackFile {

    @Override
    public void zip(Zip zip) {
        for (Path file : this.files) {
            String fileName = file.getFileName().toString();
            if ((fileName.equals("pack.mcmeta") || fileName.equals("pack.png")))
                zip.addFile(file, this.root.resolve(fileName));
            else zip.addFile(file);
        }
    }

    @Override
    public Path getPath() {
        return this.root.resolve("assets/" + name);
    }
}
