package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.Camel;
import deco2800.skyfall.entities.Horse;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.enemies.*;
import deco2800.skyfall.entities.pets.IceWhitebear;
import deco2800.skyfall.entities.pets.LizardHome;
import deco2800.skyfall.entities.pets.Tiger;
import deco2800.skyfall.entities.vehicle.Bike;
import deco2800.skyfall.entities.vehicle.SandCar;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.worlds.biomes.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that helps with creating worlds using WorldBuilders
 */
public class WorldDirector {
    private WorldDirector() {}

    /**
     * A simple world used in single player with n random biomes
     *
     * @param builder The builder used to construct the world
     *
     * @return The builder that was passed in
     *
     * @throws IllegalArgumentException if n is less than 1 or greater than 5
     * @author Ontonator
     */
    public static WorldBuilder constructNBiomeSinglePlayerWorld(WorldBuilder builder, long seed, int n,
                                                                boolean renderUI) {
        Random random = new Random(seed);

        builder.setType("single_player");
        if (n < 1 || n > 5) {
            throw new IllegalArgumentException("n must be between 1 and 5");
        }
        builder.addBiome(new ForestBiome(random), 25);

        ArrayList<AbstractBiome> choices = new ArrayList<>();
        choices.add(new DesertBiome(random));
        choices.add(new MountainBiome(random));
        choices.add(new VolcanicMountainsBiome(random));
        // For future development of the game
        //choices.add(new SwampBiome(random));
        //choices.add(new SnowyMountainsBiome(random));

        for (int i = 0; i < choices.size(); i++) {
            // builder.addBiome(choices.remove(random.nextInt(choices.size())), 40);
            // builder.addBiome(choices.remove(random.nextInt(choices.size())), 25);
            builder.addBiome(choices.remove(random.nextInt(choices.size())), 5);
        }


        builder.setWorldSize(100);
        //builder.setWorldSize(300);
        builder.setNodeSpacing(20);
        // builder.setNodeSpacing(25);
        builder.setSeed(random.nextInt());

        builder.addLake(1);
        builder.addLake(1);
        builder.addRiver();

        builder.setRiverSize(1);
        builder.setBeachSize(2);

        builder.setStaticEntities(true);

        MainCharacter mainCharacter = MainCharacter.getInstance(0, 0, 10f, "Main Piece", 10);
        mainCharacter.setCol(0);
        mainCharacter.setRow(0);

        // mainCharacter.getUnlockedBiomes();
        // for (String s: mainCharacter.getUnlockedBiomes()) {
        //     for (AbstractBiome b: builder.getWorld().getBiomes()) {
        //         if (b.getBiomeName() == "desert") {
        //             for (Tile t: b.getTiles()){
        //                 t.setObstructed(true);
        //             }
        //         }
        //    }
        // }

        if (renderUI) {
            StatisticsManager sm = new StatisticsManager(mainCharacter);
            GameManager.addManagerToInstance(sm);
            GameMenuManager gmm = GameManager.getManagerFromInstance(GameMenuManager.class);
            gmm.addStatsManager(sm);
            gmm.drawAllElements();
        }
        builder.addEntity(mainCharacter);
        builder.addEntity(new LizardHome(0, 2, mainCharacter));
        builder.addEntity(new IceWhitebear(-2, 0, mainCharacter));
        builder.addEntity(new Tiger(-4f,-2f,mainCharacter));
        builder.addEntity(new Abductor(3,2, 0.7f, "Forest",
                "enemyAbductor"));
        builder.addEntity(new Heavy(0,6, 0.7f, "Iceland",
                "enemyHeavy"));
        builder.addEntity(new Scout(2,7, 0.7f, "Forest",
                "enemyScout"));

        return builder;
    }

    /**
     * Constructs a tutorial world
     *
     * @param builder The builder used to construct the world
     */
    public static WorldBuilder constructTutorialWorld(WorldBuilder builder, long seed) {
        Random random = new Random(seed);


        MainCharacter mainCharacter = MainCharacter.getInstance(0, 0, 0.05f, "Main Piece", 10);
        mainCharacter.setCol(0);
        mainCharacter.setRow(0);

        builder.addEntity(mainCharacter);

        builder.addLake(5);
        builder.addRiver();
        builder.setRiverSize(5);
        builder.setBeachSize(12);

        builder.setNodeSpacing(15);
        builder.setWorldSize(80);
        builder.setType("tutorial");
        builder.setSeed(2);
        builder.setStaticEntities(true);
        builder.addBiome(new ForestBiome(random), 20);
        builder.addBiome(new DesertBiome(random), 20);
        builder.addBiome(new MountainBiome(random), 20);

        return builder;
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

        builder.setType("single_player");

        builder.addBiome(new ForestBiome(random), 10);

        return builder;
    }
}
