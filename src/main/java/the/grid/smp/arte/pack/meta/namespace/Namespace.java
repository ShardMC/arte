package the.grid.smp.arte.pack.meta.namespace;

import the.grid.smp.arte.zip.Zip;

import java.io.IOException;
import java.nio.file.Path;

public record Namespace(String name, Path path) implements NamespaceLike {

    @Override
    public void zip(Zip zip) throws IOException {
        zip.add(this.path);
    }
}
