package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Robot;
import deco2800.skyfall.entities.Spider;
import deco2800.skyfall.entities.Stone;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.worlds.biomes.*;

import java.lang.management.MonitorInfo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class that helps with creating worlds using WorldBuilders
 */
public class WorldDirector {
    private static Random random = new Random(0);

    /**
     * A simple world used in single player
     * @param builder The builder used to construct the world
     */
    public static void constructSimpleSinglePlayerWorld(WorldBuilder builder){
        builder.setNodeSpacing(35);
        builder.setWorldSize(300);
        builder.setSeed(random.nextInt());
        builder.addLake(5);
        builder.addLake(5);
        builder.addRiver();
        builder.addRiver();
        builder.addRiver();
        builder.setRiverSize(5);
        builder.setBeachSize(12);
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

    /**
     * A simple world used in single player with n random biomes
     * @param builder The builder used to construct the world
     * @throws IllegalArgumentException if n is less than 1 or greater than 5
     */
    public static void constructNBiomeSinglePlayerWorld(WorldBuilder builder, int n){
        if (n < 1 || n > 5) {
            throw new IllegalArgumentException("n must be between 1 and 5");
        }

        builder.setNodeSpacing(15);
        builder.setWorldSize(160);
        builder.setSeed(random.nextInt());
        builder.addLake(5);
        builder.addRiver();
        builder.setRiverSize(5);
        builder.setBeachSize(12);
        builder.setStaticEntities(true);

        MainCharacter mainCharacter = new MainCharacter(0,0,0.05f, "Main Piece", 10);

        builder.addEntity(mainCharacter);
        GameManager.getManagerFromInstance(GameMenuManager.class).setMainCharacter(mainCharacter);

        builder.addEntity(new Spider(-4f, -1f, mainCharacter));
        builder.addEntity(new Robot(-4, -2, mainCharacter));
        builder.addEntity(new Stone(-4, -2, mainCharacter));

        ArrayList<AbstractBiome> choices = new ArrayList<>();
        choices.add(new ForestBiome());
        choices.add(new DesertBiome());
        choices.add(new MountainBiome());
        choices.add(new VolcanicMountainsBiome());
        choices.add(new SwampBiome());

        for (int i = 0; i < n; i++) {
            builder.addBiome(choices.remove(random.nextInt(choices.size())), 50);
        }
    }

    /**
     * Constructs a tutorial world
     * @param builder The builder used to construct the world
     */
    public static void constructTutorialWorld(WorldBuilder builder){

        MainCharacter mainCharacter = new MainCharacter(0,0,0.05f, "Main Piece", 10);

        builder.addEntity(mainCharacter);
        GameManager.getManagerFromInstance(GameMenuManager.class).setMainCharacter(mainCharacter);

        builder.addEntity(new Spider(-4f, -1f, mainCharacter));
        builder.addEntity(new Robot(-4, -2, mainCharacter));


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

    /**
     * Constructs a server world
     * @param builder The builder used to construct the world
     */
    public static void constructServerWorld(WorldBuilder builder){
        builder.setNodeSpacing(15);
        builder.setWorldSize(80);
        builder.setType("server");
        builder.addBiome(new ForestBiome(), 20);
        builder.addBiome(new DesertBiome(), 20);
        builder.addBiome(new MountainBiome(), 20);
    }

    /**
     * Constructs a world used for testing
     * @param builder The builder used to construct the world
     */
    public static void constructTestWorld(WorldBuilder builder){
        builder.setNodeSpacing(5);
        builder.setWorldSize(30);

        builder.setType("test");
        builder.setStaticEntities(true);

        builder.addBiome(new ForestBiome(), 10);
        builder.addBiome(new DesertBiome(), 10);
        builder.addBiome(new MountainBiome(), 10);
        builder.addBiome(new VolcanicMountainsBiome(), 10);
        builder.addBiome(new SwampBiome(), 10);
        builder.addLake(3);
    }
}