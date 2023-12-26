package the.grid.smp.arte.common.zip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Stream;

public class ZipDirectoryTask extends RecursiveAction {

    private final Zip zip;
    private final Path path;

    public ZipDirectoryTask(Zip zip, Path path) {
        this.zip = zip;
        this.path = path;
    }

    @Override
    protected void compute() {
        try (Stream<Path> stream = Files.list(this.path)) {
            stream.forEach(file -> {
                if (Files.isDirectory(file)) {
                    new ZipDirectoryTask(this.zip, file).fork();
                    return;
                }

                this.zip.addFile(file);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
