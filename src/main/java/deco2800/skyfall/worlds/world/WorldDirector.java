package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Robot;
import deco2800.skyfall.entities.Spider;
import deco2800.skyfall.entities.Stone;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.worlds.biomes.DesertBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.biomes.MountainBiome;
import deco2800.skyfall.worlds.biomes.SwampBiome;
import deco2800.skyfall.worlds.biomes.VolcanicMountainsBiome;
import java.util.Random;

public class WorldDirector {
    private static Random random = new Random(0);

    public static void constructSimpleSinglePlayerWorld(WorldBuilder builder){
        builder.setNodeSpacing(15);
        builder.setWorldSize(160);
        builder.setSeed(random.nextInt());
        builder.addLake(5);
        builder.addRiver();
        builder.setRiverSize(5);
        builder.setStaticEntities(true);

        MainCharacter mainCharacter = new MainCharacter(0,0,0.05f, "Main Piece", 10);

        builder.addEntity(mainCharacter);
        GameManager.getManagerFromInstance(GameMenuManager.class).setMainCharacter(mainCharacter);

        builder.addEntity(new Spider(-4f, -1f, mainCharacter));
        builder.addEntity(new Robot(-4, -2, mainCharacter));
        builder.addEntity(new Stone(-4, -2, mainCharacter));

        builder.addBiome(new ForestBiome(), 40);
        builder.addBiome(new DesertBiome(), 40);
        builder.addBiome(new MountainBiome(), 40);
        builder.addBiome(new VolcanicMountainsBiome(), 40);
        builder.addBiome(new SwampBiome(), 40);
    }

    public static void constructTutorialWorld(WorldBuilder builder){
        builder.setNodeSpacing(15);
        builder.setWorldSize(80);
        builder.setType("tutorial");
        builder.setSeed(2);
        builder.setStaticEntities(true);
        builder.addEntity(new Spider(-4f, -1f));
        builder.addEntity(new MainCharacter(0f,0f,0.05f, "Main Piece", 10));
        builder.addBiome(new ForestBiome(), 20);
        builder.addBiome(new DesertBiome(), 20);
        builder.addBiome(new MountainBiome(), 20);
    }

    public static void constructServerWorld(WorldBuilder builder){
        builder.setNodeSpacing(15);
        builder.setWorldSize(80);
        builder.setType("server");
        builder.addBiome(new ForestBiome(), 20);
        builder.addBiome(new DesertBiome(), 20);
        builder.addBiome(new MountainBiome(), 20);
    }

    public static void constructTestWorld(WorldBuilder builder){
        builder.setNodeSpacing(5);
        builder.setWorldSize(30);

        builder.setType("test");
        builder.setStaticEntities(true);

        builder.addBiome(new ForestBiome(), 20);
        builder.addBiome(new DesertBiome(), 20);
        builder.addBiome(new MountainBiome(), 20);
    }


    public static void smallWorldTest(WorldBuilder builder){
        builder.setNodeSpacing(5);
        builder.setWorldSize(30);
        builder.addBiome(new ForestBiome(), 5);
        builder.addBiome(new MountainBiome(), 5);
        builder.addBiome(new DesertBiome(), 5);
        builder.addBiome(new VolcanicMountainsBiome(), 5);
        builder.addBiome(new SwampBiome(), 5);
    }
}
