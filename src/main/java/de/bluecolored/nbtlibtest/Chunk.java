package de.bluecolored.nbtlibtest;

public interface Chunk {

    int getDataVersion();

    int getXPos();

    int getYPos();

    int getZPos();

    long getInhabitedTime();

    String getStatus();

}
