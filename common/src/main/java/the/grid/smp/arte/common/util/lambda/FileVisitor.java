package the.grid.smp.arte.common.util.lambda;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

@FunctionalInterface
public interface FileVisitor {
    void visit(Path file, BasicFileAttributes attrs);
}
