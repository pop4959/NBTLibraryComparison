package de.bluecolored.nbtlibtest.libs;

import de.bluecolored.nbtlibtest.Chunk;
import de.bluecolored.nbtlibtest.NBTLibrary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;

import java.io.IOException;
import java.io.InputStream;

public class KyoriLibrary implements NBTLibrary {

    @Override
    public Chunk loadChunk(InputStream in) throws IOException {
        CompoundBinaryTag data = BinaryTagIO.unlimitedReader().read(in);
        return new ChunkImpl(
                data.getString("Status")
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChunkImpl implements Chunk {
        private String status;
    }

}
