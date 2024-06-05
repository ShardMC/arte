package io.shardmc.arte.common.pack.meta.file;

import io.shardmc.arte.common.zip.Zip;

import java.nio.file.Path;

public interface PackFile {

    void zip(Zip zip);

    Path getPath();
}
