package the.grid.smp.arte.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import the.grid.smp.arte.common.util.lambda.FileVisitor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Util {

    public static void walk(Path path, FileVisitor function) throws IOException {
        long start = System.currentTimeMillis();
        Files.walkFileTree(path, new SimpleFileVisitor(function));
        System.out.println("Walked dir in " + (System.currentTimeMillis() - start) + "ms");
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
