package de.bluecolored.nbtlibtest;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public interface NBTLibrary {

    default Collection<Chunk> getChunks(Path regionFile) throws IOException {

        if (Files.notExists(regionFile)) return Collections.emptyList();

        long fileLength = Files.size(regionFile);
        if (fileLength == 0) return Collections.emptyList();

        Collection<Chunk> chunks = new ArrayList<>();
        try (FileChannel channel = FileChannel.open(regionFile, StandardOpenOption.READ)) {
            byte[] header = new byte[1024 * 8];
            byte[] chunkDataBuffer = null;

            // read the header
            readFully(channel, header, 0, header.length);

            // iterate over all chunks
            for (int x = 0; x < 32; x++) {
                for (int z = 0; z < 32; z++) {
                    int xzChunk = z * 32 + x;

                    int size = header[xzChunk * 4 + 3] * 4096;
                    if (size == 0) continue;

                    int i = xzChunk * 4 + 4096;
                    int timestamp = header[i++] << 24;
                    timestamp |= (header[i++] & 0xFF) << 16;
                    timestamp |= (header[i++] & 0xFF) << 8;
                    timestamp |= header[i] & 0xFF;

                    i = xzChunk * 4;
                    int offset = header[i++] << 16;
                    offset |= (header[i++] & 0xFF) << 8;
                    offset |= header[i] & 0xFF;
                    offset *= 4096;

                    if (chunkDataBuffer == null || chunkDataBuffer.length < size)
                        chunkDataBuffer = new byte[size];

                    chunks.add(loadChunk(channel, offset, size, chunkDataBuffer));
                }
            }
        }

        return chunks;
    }

    default Chunk loadChunk(FileChannel channel, int offset, int size, byte[] dataBuffer) throws IOException {
        channel.position(offset);
        readFully(channel, dataBuffer, 0, size);

        int compressionTypeId = dataBuffer[4];
        Compression compression = switch (compressionTypeId) {
            case 0, 3 -> Compression.NONE;
            case 1 -> Compression.GZIP;
            case 2 -> Compression.DEFLATE;
            default -> throw new IOException("Unknown chunk compression-id: " + compressionTypeId);
        };

        InputStream in = compression.decompress(new ByteArrayInputStream(dataBuffer, 5, size - 5));
        return loadChunk(in);
    }

    Chunk loadChunk(InputStream in) throws IOException;

    @SuppressWarnings("SameParameterValue")
    private static void readFully(ReadableByteChannel src, byte[] dst, int off, int len) throws IOException {
        readFully(src, ByteBuffer.wrap(dst), off, len);
    }

    private static void readFully(ReadableByteChannel src, ByteBuffer bb, int off, int len) throws IOException {
        int n = 0;
        while (n < len) {
            bb.limit(Math.min(off + len, bb.capacity()));
            bb.position(off);
            int count = src.read(bb);
            if (count < 0) throw new EOFException();
            n += count;
        }
    }

}
