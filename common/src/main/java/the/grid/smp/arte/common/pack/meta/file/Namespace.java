package the.grid.smp.arte.common.pack.meta.file;

import the.grid.smp.arte.common.rewrite.Zip;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public record Namespace(String name, Collection<Path> files) implements PackFile {

    @Override
    public void zip(Zip zip) throws IOException {
        for (Path file : this.files) {
            zip.addFile(file);
        }
    }
}
