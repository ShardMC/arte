package the.grid.smp.arte.common.pack.meta.namespace;

import the.grid.smp.arte.common.zip.Zip;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public record NamespaceGroup(String name, Collection<Path> namespaces) implements NamespaceLike {

    public NamespaceGroup(String name, Path... namespaces) {
        this(name, List.of(namespaces));
    }

    @Override
    public void zip(Zip zip) throws IOException {
        zip.add(this.namespaces);
    }
}
