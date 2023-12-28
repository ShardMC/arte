package io.shardmc.arte.common.util;

import io.shardmc.arte.common.logger.ArteLogger;
import io.shardmc.arte.common.util.lambda.FileVisitor;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Util {

    public static void walk(ArteLogger logger, Path path, FileVisitor function) {
        walk(path.toFile(), function);
    }

    private static void walk(File file, FileVisitor function) {
        String[] ignore = file.list((dir, name) -> {
            File target = new File(dir, name);

            if (target.isDirectory()) {
                walk(target, function);
                return false;
            }

            function.visit(target.toPath());
            return false;
        });
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
