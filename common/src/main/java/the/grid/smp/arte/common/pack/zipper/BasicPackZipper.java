package the.grid.smp.arte.common.pack.zipper;

import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.logger.ArteLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class BasicPackZipper extends PackZipper {

    public BasicPackZipper(ArteLogger logger, Path root, Path output) throws IOException {
        super(logger, root, output);
    }

    public BasicPackZipper(Arte arte, Path root, Path output) throws IOException {
        this(arte.logger(), root, output);
    }

    @Override
    protected void zip(Context context) throws IOException {
        try (Stream<Path> stream = Files.list(this.assets)) {
            stream.forEach(context::addNamespace);
        }
    }
}
