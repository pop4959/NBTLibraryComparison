package de.bluecolored.nbtlibtest.libs;

import de.bluecolored.nbtlibtest.Chunk;
import de.bluecolored.nbtlibtest.NBTLibrary;

import de.bluecolored.bluenbt.BlueNBT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

public class BlueNBTLibrary implements NBTLibrary {

    private final BlueNBT blueNBT = new BlueNBT();

    @Override
    public Chunk loadChunk(InputStream in) throws IOException {
        return blueNBT.read(in, ChunkImpl.class);
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
