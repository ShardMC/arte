package the.grid.smp.arte.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import the.grid.smp.arte.common.util.lambda.FileVisitor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

    public static File getDefaultResourceFile(Path file) throws IOException {
        String name = file.getFileName().toString();
        URL url = Util.class.getClassLoader().getResource(name);

        if (url == null)
            throw new IOException("Couldn't find the default config! The build may be corrupt!");

        File result = new File(url.getFile());

        if (!result.exists())
            throw new IOException("Couldn't find the default config! The build may be corrupt!");

        return result;
    }

    public static InputStream getDefaultResourceStream(Path file) throws IOException {
        String name = file.getFileName().toString();
        InputStream stream = Util.class.getClassLoader().getResourceAsStream(name);

        if (stream == null)
            throw new IOException("Couldn't find the default config! The build may be corrupt!");

        return stream;
    }
}
