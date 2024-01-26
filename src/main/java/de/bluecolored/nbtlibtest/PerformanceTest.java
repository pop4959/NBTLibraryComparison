package de.bluecolored.nbtlibtest;

import de.bluecolored.nbtlibtest.libs.*;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class PerformanceTest {

    private final NBTLibrary nbtLibrary;
    private final Path regionFilesFolder;

    private int regionCount;
    private int chunkCount;
    private long startTime;

    public synchronized void runPerformanceTest() {
        runSinglePerformanceTest();
    }

    public synchronized void runSinglePerformanceTest() {
        this.chunkCount = 0;
        this.regionCount = 0;
        this.startTime = System.nanoTime();

        try (Stream<Path> regionFiles = Files.list(regionFilesFolder)) {
                regionFiles
                        .sequential()
                        .forEach(this::loadRegionFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        long endTime = System.nanoTime();
        long deltaTime = endTime - this.startTime;

        System.out.println("Loaded " + chunkCount + " chunks from " + regionCount + " region files in " + deltaTime + "ns | " + getChunksPerSecond(endTime) + " chunks per second");
    }

    public synchronized void loadRegionFile(Path regionFile) {
        try {
            Collection<Chunk> chunks = nbtLibrary.getChunks(regionFile);
            this.chunkCount += chunks.size();
            this.regionCount += 1;
            System.out.println("Loaded " + chunks.size() + " chunks from " + regionFile.getFileName().toString() + " | " + getChunksPerSecond(System.nanoTime()) + " chunks per second");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public double getChunksPerSecond(long endTime) {
        long deltaTime = endTime - startTime;
        long nanosPerChunk = deltaTime / this.chunkCount;
        return 1e+9 / (double) nanosPerChunk;
    }

    public static void main(String[] args) {
        Path regionFileFolder = Path.of("regions");

        String lib = "bluenbt";
        if (args.length > 0) lib = args[0];

        NBTLibrary library = switch (lib) {
            case "querz" -> new QuerzLibrary();
            case "kyori" -> new KyoriLibrary();
            case "chunky" -> new ChunkyLibrary();
            case "bluenbtFull" -> new BlueNBTLoadFullChunkLibrary();
            case "bluenbtDirect" -> new BlueNBTDirectLibrary();
            default -> new BlueNBTLibrary();
        };

        System.out.println("Starting test for " + library.getClass().getSimpleName() + " ...");
        new PerformanceTest(library, regionFileFolder).runPerformanceTest();
    }

}
