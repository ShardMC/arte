package the.grid.smp.arte.zip;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Random;
import java.util.zip.ZipEntry;

public class Scrambler {
    private static final byte[] magic5s = new byte[]{5, 57};
    private static final byte[] magic0s = new byte[]{0, 0, 0, 0};
    private static final byte[] magic = Scrambler.magic();

    private static final FileTime time = FileTime.fromMillis(0L);

    private static byte[] magic() {
        byte[] bytes = new byte[255];
        Arrays.fill(bytes, (byte) 92);

        bytes[0] = 46;
        bytes[1] = 46;
        bytes[2] = 47;

        return bytes;
    }

    public static void scramble(ZipEntry var0) {
        var0.setCrc(0L);
        var0.setLastAccessTime(time);
        var0.setCreationTime(time);
        var0.setLastModifiedTime(time);

        if (var0.getName().toLowerCase().endsWith(".ogg")) {
            var0.setSize(Scrambler.scramble(9000, 15000));
            return;
        }

        var0.setSize(1337L);
    }

    @SuppressWarnings("SameParameterValue")
    private static int scramble(int i1, int i2) {
        return i2 == i1 ? i2 : new Random(System.currentTimeMillis())
                .nextInt(i2 - i1 + 1) + i1;
    }

    public static void scramble(File path) {
        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile(path, "rw");
            byte[] buffer = new byte[8192];
            byte[] small = new byte[2];
            int var6 = 0;

            while ((long) var6 < file.length()) {
                int amount = file.read(buffer);
                var6 += amount;

                long pointerDelta = file.getFilePointer() - (long) amount;

                for (int i = 0; i < amount - 3; ++i) {
                    if (buffer[i] == 80 && buffer[i + 1] == 75 && buffer[i + 2] == 3 && buffer[i + 3] == 4) {
                        file.seek(pointerDelta + (long) i + 18L);
                        file.write(magic0s);

                        file.seek(pointerDelta + (long) i + 22L);
                        file.write(magic0s);

                        file.seek(pointerDelta + (long) i + 26L);
                        file.read(small);

                        file.seek(pointerDelta + (long) i + 30L);
                        file.write(magic, 0, small[0] & 255 | (small[1] & 255) << 8);
                        i += 98;
                        continue;
                    }

                    if (buffer[i] == 80 && buffer[i + 1] == 75 && buffer[i + 2] == 1 && buffer[i + 3] == 2) {
                        file.seek(pointerDelta + (long) i + 34L);
                        file.write(magic5s);
                        i += 34;
                    }
                }

                file.seek(pointerDelta + (long) amount - 4L);
            }
        } catch (Throwable t) {
            System.out.println("Failed to protect zip file. " + t);
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                System.out.println("Failed to close zip file after protection. " + e);
            }
        }
    }

    public static void scramble(Path path) {
        Scrambler.scramble(path.toFile());
    }
}
