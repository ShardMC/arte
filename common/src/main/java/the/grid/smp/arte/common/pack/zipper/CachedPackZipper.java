package the.grid.smp.arte.common.pack.zipper;

import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.data.FilterList;
import the.grid.smp.arte.common.pack.meta.BuiltPack;
import the.grid.smp.arte.common.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CachedPackZipper extends BasicPackZipper {

    public CachedPackZipper(Arte arte, Path root, Path output) throws IOException {
        super(arte, root, output);
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
}
