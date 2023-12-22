package the.grid.smp.arte.common.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class SimpleFileVisitor extends java.nio.file.SimpleFileVisitor<Path> {

    private final the.grid.smp.arte.common.util.lambda.FileVisitor func;

    public SimpleFileVisitor(the.grid.smp.arte.common.util.lambda.FileVisitor func) {
        this.func = func;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        return this.func.visit(path, attrs);
    }
}
