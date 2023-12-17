package the.grid.smp.arte.util;

import org.apache.commons.codec.digest.DigestUtils;
import the.grid.smp.arte.util.lambda.FileVisitor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Util {

    public static void walk(Path path, FileVisitor function) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor(function));
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

    public static String removeExtension(String file) {
        int dotIndex = file.lastIndexOf('.');
        return (dotIndex == -1) ? file : file.substring(0, dotIndex);
    }

    public static String nameWithoutExtension(Path path) {
        return Util.removeExtension(path.getFileName().toString());
    }
}
