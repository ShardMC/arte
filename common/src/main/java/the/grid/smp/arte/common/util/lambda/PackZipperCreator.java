package the.grid.smp.arte.common.util.lambda;

import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.pack.zipper.PackZipper;

import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface PackZipperCreator {
    PackZipper create(Arte arte, Path root, Path output) throws IOException;
}
