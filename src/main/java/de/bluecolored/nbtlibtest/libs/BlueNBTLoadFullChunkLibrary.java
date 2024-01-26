package de.bluecolored.nbtlibtest.libs;

import de.bluecolored.bluenbt.BlueNBT;
import de.bluecolored.nbtlibtest.Chunk;
import de.bluecolored.nbtlibtest.NBTLibrary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class BlueNBTLoadFullChunkLibrary implements NBTLibrary {

    private final BlueNBT blueNBT = new BlueNBT();

    @SuppressWarnings("unchecked")
    @Override
    public Chunk loadChunk(InputStream in) throws IOException {
        Map<String, Object> data = (Map<String, Object>) blueNBT.read(in, Object.class);
        return new ChunkImpl(
                (int) data.get("DataVersion"),
                (int) data.get("xPos"),
                (int) data.get("yPos"),
                (int) data.get("zPos"),
                (long) data.get("InhabitedTime"),
                (String) data.get("Status")
        );
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
