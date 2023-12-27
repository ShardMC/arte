package the.grid.smp.arte.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.file.SimplePathVisitor;
import the.grid.smp.arte.common.logger.ArteLogger;
import the.grid.smp.arte.common.util.lambda.FileVisitor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

public class Util {

    private static final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingDeque<>();

    public static void init() {
        new Thread(() -> {
            while (true) {
                if (blockingQueue.isEmpty())
                    continue;

                blockingQueue.poll().run();
            }
        }).start();
    }

    /*public static void walk(ArteLogger logger, Path path, FileVisitor function) throws IOException {
        blockingQueue.add(() -> {
            long start = System.currentTimeMillis();
            try {
                Files.walkFileTree(path, new SimplePathVisitor() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        function.visit(file);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logger.info("Walk-d dir " + path + " in " + (System.currentTimeMillis() - start));
        });
    }*/

    public static void walk(ArteLogger logger, Path path, FileVisitor function) {
        long start = System.currentTimeMillis();
        io(logger, path.toFile(), function);
        logger.info("Walk-d dir " + path + " in " + (System.currentTimeMillis() - start));
    }

    private static void io(ArteLogger logger, File file, FileVisitor function) {
        String[] ignore = file.list((dir, name) -> {
            File target = new File(dir, name);

            if (target.isDirectory()) {
                io(logger, target, function);
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
