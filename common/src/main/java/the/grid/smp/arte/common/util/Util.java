package the.grid.smp.arte.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.file.SimplePathVisitor;
import the.grid.smp.arte.common.util.lambda.FileVisitor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Util {

    public static void walk(Path path, FileVisitor function) throws IOException {
        Files.walkFileTree(path, new SimplePathVisitor() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                function.visit(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void walk2(ThreadPool pool, Path path, Consumer<Path> fileConsumer) throws IOException {
        try (Stream<Path> stream = Files.list(path)) {
            stream.forEach(file -> {
                if (Files.isDirectory(file)) {
                    pool.add(() -> {
                        try {
                            Util.walk2(pool, file, fileConsumer);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    return;
                }

                fileConsumer.accept(file);
            });
        }
    }

    public static byte[] hash(Path path) throws IOException {
        return DigestUtils.sha1(Files.newInputStream(path));
    }

    public static UUID uuid(String str) {
        return UUID.nameUUIDFromBytes(str.getBytes(StandardCharsets.UTF_8));
    }

    public static UUID uuid(Path path) {
        return Util.uuid(path.getFileName().toString());
    }
}
