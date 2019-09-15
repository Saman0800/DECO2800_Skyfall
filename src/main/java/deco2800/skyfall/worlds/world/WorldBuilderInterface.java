package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.worlds.biomes.AbstractBiome;

public interface WorldBuilderInterface {


    void addEntity(AbstractEntity entity);

    void addBiome(AbstractBiome biome, int size);

    void addLake(int size);

    void setWorldSize(int size);

    void setSeed(long seed);

    void setNodeSpacing(int nodeSpacing);

    void setType(String type);

    void addRiver();

    void setRiverSize(int size);




}
