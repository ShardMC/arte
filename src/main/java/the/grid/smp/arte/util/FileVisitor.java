package the.grid.smp.arte.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

@FunctionalInterface
public interface FileVisitor {
    FileVisitResult visit(Path file, BasicFileAttributes attrs) throws IOException;
}
