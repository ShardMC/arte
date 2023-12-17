package the.grid.smp.arte.pack.zipper;

import the.grid.smp.arte.Arte;
import the.grid.smp.arte.config.data.FilterList;
import the.grid.smp.arte.pack.meta.BuiltPack;
import the.grid.smp.arte.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CachedPackZipper extends PackZipper {

    public CachedPackZipper(Logger logger, Path root, Path output) throws IOException {
        super(logger, root, output);
    }

    public CachedPackZipper(Arte arte, Path root, Path output) throws IOException {
        this(arte.getLogger(), root, output);
    }

    @Override
    public void zip(FilterList list, boolean scramble, Consumer<BuiltPack> consumer) throws IOException {
        try (Stream<Path> stream = Files.list(this.output)) {
            stream.parallel().forEach(path -> {
                try {
                    boolean force = !(list.elements().contains(
                            Util.nameWithoutExtension(path))
                    ) && list.whitelist();

                    this.packs.add(new BuiltPack(path, force));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    protected void zip(Context context) throws IOException {

    }
}
