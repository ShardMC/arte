package the.grid.smp.arte.common.util;

import the.grid.smp.arte.common.util.lambda.FileVisitor;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class SimpleFileVisitor extends java.nio.file.SimpleFileVisitor<Path> {

    private final FileVisitor func;

    public SimpleFileVisitor(FileVisitor func) {
        this.func = func;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
        return this.func.visit(path, attrs);
    }
}
