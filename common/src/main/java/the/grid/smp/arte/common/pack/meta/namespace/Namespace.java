package the.grid.smp.arte.common.pack.meta.namespace;

import the.grid.smp.arte.common.zip.Zip;

import java.io.IOException;
import java.nio.file.Path;

public record Namespace(String name, Path path) implements NamespaceLike {

    @Override
    public void zip(Zip zip) throws IOException {
        zip.add(this.path);
    }
}
