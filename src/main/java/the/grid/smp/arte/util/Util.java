package the.grid.smp.arte.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.file.SimplePathVisitor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;

public class Util {

    public static void walk(Path path, FileVisitor function) throws IOException {
        Files.walkFileTree(path, new SimplePathVisitor() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                return function.visit(file, attrs);
            }
        });
    }

    public static String getName(Path path) {
        return path.getFileName().toString();
    }

    public static byte[] hash(InputStream stream) throws IOException {
        return DigestUtils.sha1(stream);
    }

    public static byte[] hash(File path) throws IOException {
        return Util.hash(new FileInputStream(path));
    }

    public static byte[] hash(Path path) throws IOException {
        return Util.hash(path.toFile());
    }

    public static UUID uuid(String str) {
        return UUID.nameUUIDFromBytes(str.getBytes(StandardCharsets.UTF_8));
    }

    public static UUID uuid(File file) {
        return Util.uuid(file.getName());
    }

    public static UUID uuid(Path path) {
        return Util.uuid(Util.getName(path));
    }
}
