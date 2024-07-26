package su.shardmc.arte.common.util;

import su.shardmc.arte.common.util.lambda.FileVisitor;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

public class Util {

    // performance hack. minecraft doesn't allow dots in namespaces
    public static void walkCheap(Path path, FileVisitor function) {
        try (Stream<Path> stream = Files.list(path)) {
            stream.forEach(file -> {
                // this is why it's called cheap
                if (!file.getFileName().toString().contains(".")) {
                    walkCheap(file, function);
                    return;
                }

                function.visit(file);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hash(Path path) throws IOException {
        return DigestUtils.sha1Hex(Files.newInputStream(path));
    }

    public static UUID uuid(String str) {
        return UUID.nameUUIDFromBytes(str.getBytes(StandardCharsets.UTF_8));
    }

    public static UUID uuid(Path path) {
        return Util.uuid(path.getFileName().toString());
    }
}
