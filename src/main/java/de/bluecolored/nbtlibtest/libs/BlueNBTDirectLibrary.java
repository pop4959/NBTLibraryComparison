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
            switch (reader.name()) {
                case "DataVersion" -> chunk.dataVersion = reader.nextInt();
                case "xPos" -> chunk.xPos = reader.nextInt();
                case "yPos" -> chunk.yPos = reader.nextInt();
                case "zPos" -> chunk.zPos = reader.nextInt();
                case "InhabitedTime" -> chunk.inhabitedTime = reader.nextLong();
                case "Status" -> chunk.status = reader.nextString();
                default -> reader.skip();
            }
        }
        reader.endCompound();

        return chunk;
    }

    @Getter
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
