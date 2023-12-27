package the.grid.smp.arte.common.pack.zipper;

import the.grid.smp.arte.common.Arte;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BasicPackZipper extends PackZipper {

    private final Map<String, List<Path>> files = new HashMap<>();

    public BasicPackZipper(ArteLogger logger, Path root, Path output) throws IOException {
        super(logger, root, output);

        try (Stream<Path> stream = Files.list(this.assets)) {
            stream.parallel().forEach(path -> {
                //try {
                    List<Path> list = new ArrayList<>();
                    Util.walk(logger, path, list::add);

                    this.files.put(path.getFileName().toString(), list);
                /*} catch (IOException e) {
                    throw new RuntimeException(e);
                }*/
            });
        }
    }

    public BasicPackZipper(Arte arte, Path root, Path output) throws IOException {
        this(arte.logger(), root, output);
    }

    @Override
    protected void zip(Context context) throws IOException {
        for (Map.Entry<String, List<Path>> entry : this.files.entrySet()) {
            context.addNamespace(entry.getKey(), entry.getValue());
        }
    }
}
