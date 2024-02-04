package de.bluecolored.nbtlibtest.libs;

import de.bluecolored.nbtlibtest.Chunk;
import de.bluecolored.nbtlibtest.NBTLibrary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.popcraft.chunky.nbt.CompoundTag;
import org.popcraft.chunky.nbt.Tag;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChunkyLoadFullChunkLibrary implements NBTLibrary {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Chunk loadChunk(InputStream in) throws IOException {
        CompoundTag data = (CompoundTag) Tag.load(new DataInputStream(in));
        return new ChunkImpl(
                data.getString("Status").get().value()
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChunkImpl implements Chunk {
        private String status;
    }

}
