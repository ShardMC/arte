package io.shardmc.arte.common.util.lambda;

import io.shardmc.arte.common.pack.zipper.PackZipper;
import io.shardmc.arte.common.Arte;

import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface PackZipperCreator {
    PackZipper create(Arte arte, Path root, Path output) throws IOException;
}
