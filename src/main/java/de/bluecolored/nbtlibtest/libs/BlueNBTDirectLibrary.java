package de.bluecolored.nbtlibtest.libs;

import de.bluecolored.bluenbt.NBTReader;
import de.bluecolored.nbtlibtest.Chunk;
import de.bluecolored.nbtlibtest.NBTLibrary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

public class BlueNBTDirectLibrary implements NBTLibrary {

    @Override
    public Chunk loadChunk(InputStream in) throws IOException {
        NBTReader reader = new NBTReader(in);
        ChunkImpl chunk = new ChunkImpl();

        reader.beginCompound();
        while (reader.hasNext()) {
            if (reader.name().equals("Status")) {
                chunk.status = reader.nextString();
            } else {
                reader.skip();
            }
        }
        reader.endCompound();

        return chunk;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChunkImpl implements Chunk {
        private String status;
    }

}
