package su.shardmc.arte.common.pack.meta.file;

import su.shardmc.arte.common.zip.Zip;

import java.nio.file.Path;

public interface PackFile {

    void zip(Zip zip);

    Path getPath();
}
