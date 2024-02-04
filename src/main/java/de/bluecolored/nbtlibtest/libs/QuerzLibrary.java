package de.bluecolored.nbtlibtest.libs;

import de.bluecolored.nbtlibtest.Chunk;
import de.bluecolored.nbtlibtest.NBTLibrary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.querz.mca.MCAUtil;
import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NBTInputStream;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;

import java.io.IOException;
import java.io.InputStream;

public class QuerzLibrary implements NBTLibrary {

    private final NBTDeserializer nbt = new NBTDeserializer(false);

    @Override
    public Chunk loadChunk(InputStream in) throws IOException {
        CompoundTag data = (CompoundTag) nbt.fromStream(in).getTag();
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
