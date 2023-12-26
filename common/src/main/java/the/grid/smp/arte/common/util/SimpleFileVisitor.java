package the.grid.smp.arte.common.util;

import the.grid.smp.arte.common.util.lambda.FileVisitor;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class SimpleFileVisitor extends java.nio.file.SimpleFileVisitor<Path> {

    private final FileVisitor func;
    private long time;

    public SimpleFileVisitor(FileVisitor func) {
        this.func = func;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
        if (!attrs.isDirectory()) {
            long start = System.currentTimeMillis();
            this.func.visit(path);

            this.time += System.currentTimeMillis() - start;
        }

        return FileVisitResult.CONTINUE;
    }

    public void finish() {
        System.out.println("Took " + time);
    }
}
