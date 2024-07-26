package su.shardmc.arte.common.util.lambda;

import java.nio.file.Path;

@FunctionalInterface
public interface FileVisitor {
    void visit(Path file);
}
