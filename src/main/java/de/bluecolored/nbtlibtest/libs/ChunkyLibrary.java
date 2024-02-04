package de.bluecolored.nbtlibtest.libs;

import de.bluecolored.nbtlibtest.Chunk;
import de.bluecolored.nbtlibtest.NBTLibrary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.popcraft.chunky.nbt.Tag;
import org.popcraft.chunky.nbt.TagType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChunkyLibrary implements NBTLibrary {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Chunk loadChunk(InputStream in) throws IOException {
        Tag data = Tag.find(new DataInputStream(in), TagType.STRING, "Status");
        return new ChunkImpl(
                data == null ? null : data.name()
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChunkImpl implements Chunk {
        private String status;
    }

}
