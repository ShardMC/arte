package the.grid.smp.arte.util.lambda;

import the.grid.smp.arte.Arte;
import the.grid.smp.arte.pack.zipper.PackZipper;

import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface PackZipperCreator {
    PackZipper create(Arte arte, Path root, Path output) throws IOException;
}
