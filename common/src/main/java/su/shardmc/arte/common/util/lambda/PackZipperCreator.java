package su.shardmc.arte.common.util.lambda;

import su.shardmc.arte.common.pack.zipper.PackZipper;
import su.shardmc.arte.common.Arte;

import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface PackZipperCreator {
    PackZipper create(Arte arte, Path root, Path output) throws IOException;
}
