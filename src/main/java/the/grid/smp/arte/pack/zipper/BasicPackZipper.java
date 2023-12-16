package the.grid.smp.arte.pack.zipper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class BasicPackZipper extends PackZipper {

    public BasicPackZipper(Logger logger, Path root, Path output) throws IOException {
        super(logger, root, output);
    }

    @Override
    protected void zip(Context context) throws IOException {
        try (Stream<Path> stream = Files.list(this.assets)) {
            stream.forEach(context::addNamespace);
        }
    }
}
