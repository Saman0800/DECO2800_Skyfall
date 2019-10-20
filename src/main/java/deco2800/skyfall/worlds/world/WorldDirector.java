package deco2800.skyfall.worlds.world;

import java.util.ArrayList;
import java.util.Random;

import deco2800.skyfall.entities.Camel;
import deco2800.skyfall.entities.Horse;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.enemies.Abductor;
import deco2800.skyfall.entities.enemies.Heavy;
import deco2800.skyfall.entities.enemies.Medium;
import deco2800.skyfall.entities.enemies.Scout;
import deco2800.skyfall.entities.pets.IceWhitebear;
import deco2800.skyfall.entities.pets.LizardHome;
import deco2800.skyfall.entities.vehicle.Bike;
import deco2800.skyfall.entities.vehicle.SandCar;
import deco2800.skyfall.entities.worlditems.HotSpring;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;

import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.DesertBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.biomes.InvalidBiomeException;
import deco2800.skyfall.worlds.biomes.MountainBiome;
import deco2800.skyfall.worlds.biomes.SnowyMountainsBiome;
import deco2800.skyfall.worlds.biomes.SwampBiome;
import deco2800.skyfall.worlds.biomes.VolcanicMountainsBiome;


/**
 * Class that helps with creating worlds using WorldBuilders
 */
public class WorldDirector {

    private static final String SINGLE_PLAYER_STRING = "single_player";
    private static final String MAIN_PIECE_STRING = "Main Piece";
    private static final String FOREST_STRING = "Forest";

    private WorldDirector() {
    }

    public static WorldBuilder constructSingleBiomeWorld(WorldBuilder builder, long seed, boolean renderUI,
            String biomeName) {
        Random random = new Random(seed);
        final int BIOME_SIZE = 150;
        builder.setType(WorldDirector.SINGLE_PLAYER_STRING);
        switch (biomeName) {
        case "forest":
            builder.addBiome(new ForestBiome(random), BIOME_SIZE);
            builder.addLake(1);
            builder.addLake(1);
            builder.addRiver();
            break;
        case "desert":
            builder.addBiome(new DesertBiome(random), BIOME_SIZE);
            break;
        case "mountain":
            builder.addBiome(new MountainBiome(random), BIOME_SIZE);
            builder.addLake(2);
            builder.addLake(1);
            builder.addRiver();
            break;
        case "snowy_mountains":
            builder.addBiome(new SnowyMountainsBiome(random), BIOME_SIZE);
            builder.addLake(3);
            builder.addRiver();
            builder.addRiver();
            break;
        case "swamp":
            builder.addBiome(new SwampBiome(random), BIOME_SIZE);
            builder.addLake(4);
            builder.addLake(3);
            builder.addLake(2);
            builder.addLake(1);
            builder.addLake(1);
            builder.addRiver();
            break;
        case "volcanic_mountains":
            builder.addBiome(new VolcanicMountainsBiome(random), BIOME_SIZE);
            break;
        default:
            throw new InvalidBiomeException("Invalid Biome");
        }

        builder.setWorldSize(300);
        builder.setNodeSpacing(30);
        builder.setSeed(random.nextInt());

        setupBeachAndStats(builder, renderUI);

        return builder;
    }

    /**
     * A simple world used in single player with n random biomes
     *
     * @param builder The builder used to construct the world
     * @return The builder that was passed in
     * @throws IllegalArgumentException if n is less than 1 or greater than 5
     * @author Ontonator
     */
    public static WorldBuilder constructNBiomeSinglePlayerWorld(WorldBuilder builder, long seed, int n,
            boolean renderUI) {
        Random random = new Random(seed);

        builder.setType(WorldDirector.SINGLE_PLAYER_STRING);
        if (n < 1 || n > 5) {
            throw new IllegalArgumentException("n must be between 1 and 5");
        }
        builder.addBiome(new ForestBiome(random), 25);

        ArrayList<AbstractBiome> choices = new ArrayList<>();
        choices.add(new DesertBiome(random));
        choices.add(new MountainBiome(random));
        choices.add(new VolcanicMountainsBiome(random));

        for (int i = 0; i < choices.size(); i++) {
            builder.addBiome(choices.remove(random.nextInt(choices.size())), 5);
        }

        builder.setWorldSize(100);
        builder.setNodeSpacing(20);
        builder.setSeed(random.nextInt());

        builder.addLake(1);
        builder.addLake(1);
        builder.addRiver();

        MainCharacter mainCharacter = setupBeachAndStats(builder, renderUI);
        builder.addEntity(new LizardHome(0, 2, mainCharacter));
        builder.addEntity(new IceWhitebear(-2, 0, mainCharacter));
        builder.addEntity(new Scout(0, 2, 0.4f, WorldDirector.FOREST_STRING));
        builder.addEntity(new Medium(1, 2, 0.4f, WorldDirector.FOREST_STRING));
        builder.addEntity(new Heavy(7, 9, 0.2f, WorldDirector.FOREST_STRING));
        builder.addEntity(new Abductor(4, 9, 0.8f, WorldDirector.FOREST_STRING));

        builder.addEntity(new Camel(34, -7, mainCharacter));
        builder.addEntity(new Horse(-8, -6, mainCharacter));

        builder.addEntity(new HotSpring(2, 10, mainCharacter));

        return builder;
    }

    private static MainCharacter setupBeachAndStats(WorldBuilder builder, boolean renderUI) {
        builder.setRiverSize(1);
        builder.setBeachSize(2);

        builder.setStaticEntities(true);

        MainCharacter mainCharacter = MainCharacter.getInstance(0, 0, 10f, WorldDirector.MAIN_PIECE_STRING, 10);
        mainCharacter.setCol(0);
        mainCharacter.setRow(0);

        if (renderUI) {
            StatisticsManager sm = new StatisticsManager(mainCharacter);
            GameManager.addManagerToInstance(sm);
            GameMenuManager gmm = GameManager.getManagerFromInstance(GameMenuManager.class);
            gmm.addStatsManager(sm);
            gmm.drawAllElements();
        }

        builder.addEntity(mainCharacter);
        builder.addEntity(new Bike(-10f, -2f, mainCharacter));
        builder.addEntity(new SandCar(-20f, -2f, mainCharacter));

        return mainCharacter;
    }

    /**
     * Constructs a server world
     *
     * @param builder The builder used to construct the world
     */
    public static WorldBuilder constructServerWorld(WorldBuilder builder, long seed) {
        Random random = new Random(seed);

        builder.setNodeSpacing(15);
        builder.setWorldSize(80);
        builder.setType("server");
        builder.addBiome(new ForestBiome(random), 20);
        builder.addBiome(new DesertBiome(random), 20);
        builder.addBiome(new MountainBiome(random), 20);

        return builder;
    }

    /**
     * Constructs a world used for testing
     *
     * @param builder The builder used to construct the world
     */
    public static WorldBuilder constructTestWorld(WorldBuilder builder, long seed) {
        Random random = new Random(seed);

        builder.setNodeSpacing(5);
        builder.setWorldSize(30);

        builder.setType(WorldDirector.SINGLE_PLAYER_STRING);

        builder.addBiome(new ForestBiome(random), 10);

        return builder;
    }
}
