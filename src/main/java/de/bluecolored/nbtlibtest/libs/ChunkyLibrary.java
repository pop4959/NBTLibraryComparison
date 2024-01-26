package de.bluecolored.nbtlibtest.libs;

import de.bluecolored.nbtlibtest.Chunk;
import de.bluecolored.nbtlibtest.NBTLibrary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.popcraft.chunky.nbt.CompoundTag;
import org.popcraft.chunky.nbt.Tag;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChunkyLibrary implements NBTLibrary {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Chunk loadChunk(InputStream in) throws IOException {
        CompoundTag data = (CompoundTag) Tag.load(new DataInputStream(in));
        return new ChunkImpl(
                data.getInt("DataVersion").get().value(),
                data.getInt("xPos").get().value(),
                data.getInt("yPos").get().value(),
                data.getInt("zPos").get().value(),
                data.getLong("InhabitedTime").get().value(),
                data.getString("Status").get().value()
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChunkImpl implements Chunk {
        private int dataVersion;
        private int xPos;
        private int yPos;
        private int zPos;
        private long inhabitedTime;
        private String status;
    }

}
