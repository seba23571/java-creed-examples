package com.javacreed.examples.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;

public class Example {
    public static void main(final String[] args) throws Exception {
        // Read from file
        final File inputFile = new File("src/main/resources/example.txt");
        try (final RandomAccessFile raf = new RandomAccessFile(inputFile, "rw")) {
            final FileChannel fc = raf.getChannel();
            final FileLock fl = fc.tryLock();
            if (fl == null) {
                // Failed to acquire lock
            } else {
                try (final ByteArrayOutputStream baos = new ByteArrayOutputStream(); final WritableByteChannel outChannel = Channels.newChannel(baos)) {
                    for (final ByteBuffer buffer = ByteBuffer.allocate(1024); fc.read(buffer) != -1;) {
                        buffer.flip();
                        outChannel.write(buffer);
                        buffer.clear();
                    }

                    Files.write(new File("target/example.txt").toPath(), baos.toByteArray());
                } finally {
                    fl.release();
                }
            }
        }
    }
}
