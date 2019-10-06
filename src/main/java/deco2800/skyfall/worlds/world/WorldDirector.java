package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.entities.enemies.*;
import deco2800.skyfall.entities.pets.LizardHome;
import deco2800.skyfall.entities.pets.IceWhitebear;
import deco2800.skyfall.entities.pets.Tiger;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.DesertBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.biomes.MountainBiome;
import deco2800.skyfall.worlds.biomes.VolcanicMountainsBiome;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.entities.worlditems.*;


import java.util.ArrayList;
import java.util.Random;

/**
 * Class that helps with creating worlds using WorldBuilders
 */
public class WorldDirector {
    private static Random random = new Random(0);



    /**
     * A simple world used in single player with n random biomes
     * @param builder The builder used to construct the world
     * @throws IllegalArgumentException if n is less than 1 or greater than 5
     * @return The builder that was passed in
     * @author Ontonator
     */
    public static WorldBuilder constructNBiomeSinglePlayerWorld(WorldBuilder builder, int n, boolean renderUI){
        builder.setType("single_player");
        if (n < 1 || n > 5) {
            throw new IllegalArgumentException("n must be between 1 and 5");
        }

        assert new Random(0) != null;

        ArrayList<AbstractBiome> choices = new ArrayList<>();
        choices.add(new ForestBiome(random));
        choices.add(new DesertBiome(random));
        choices.add(new MountainBiome(random));
        choices.add(new VolcanicMountainsBiome(random));
        // For future development of the game
        //choices.add(new SwampBiome(random));
        //choices.add(new SnowyMountainsBiome(random));

        for (int i = 0; i < n; i++) {
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

        MainCharacter mainCharacter = MainCharacter.getInstance(0,0,10f, "Main Piece", 10);
        mainCharacter.setCol(0);
        mainCharacter.setRow(0);


        //mainCharacter.getUnlockedBiomes();
        //for (String s: mainCharacter.getUnlockedBiomes()) {
        //    for (AbstractBiome b: builder.getWorld().getBiomes()) {
        //        if (b.getBiomeName() == "desert") {
        //            for (Tile t: b.getTiles()){
        //                t.setObstructed(true);
        //            }
        //        }

        //   }
        //}




        if (renderUI) {
            StatisticsManager sm = new StatisticsManager(mainCharacter);
            GameManager.addManagerToInstance(sm);
            GameMenuManager gmm = GameManager.getManagerFromInstance(GameMenuManager.class);
            gmm.addStatsManager(sm);
            gmm.drawAllElements();
        }

        builder.addEntity(mainCharacter);
        builder.addEntity(new Stone(-4, -3, mainCharacter));
        builder.addEntity(new LizardHome(0, 2, mainCharacter));
        builder.addEntity(new IceWhitebear(-2, 0, mainCharacter));
        builder.addEntity(new Flower(2f,2f,mainCharacter));
        builder.addEntity(new Flower(8f,2f,mainCharacter));
        builder.addEntity(new Treeman(-2f,-3f,mainCharacter));
        builder.addEntity(new Tiger(-4f,-2f,mainCharacter));

        builder.addEntity(new Camel(34, -7, mainCharacter));
        builder.addEntity(new Horse(-8,-6, mainCharacter));

        builder.addEntity(new hotSpring(2,10, mainCharacter));

        return builder;
    }

    /**
     * Constructs a tutorial world
     * @param builder The builder used to construct the world
     */
    public static void constructTutorialWorld(WorldBuilder builder){

        MainCharacter mainCharacter = MainCharacter.getInstance(0,0,0.05f, "Main Piece", 10);
        mainCharacter.setCol(0);
        mainCharacter.setRow(0);

        builder.addEntity(mainCharacter);

        builder.addEntity(new Spider(-4f, -1f, mainCharacter));
        builder.addEntity(new Robot(-4, -2, mainCharacter));

        builder.addLake(5);
        builder.addRiver();
        builder.setRiverSize(5);
        builder.setBeachSize(12);

        builder.setNodeSpacing(15);
        builder.setWorldSize(80);
        builder.setType("tutorial");
        builder.setSeed(2);
        builder.setStaticEntities(true);
        builder.addEntity(new Spider(-4f, -1f));
        builder.addBiome(new ForestBiome(random), 20);
        builder.addBiome(new DesertBiome(random), 20);
        builder.addBiome(new MountainBiome(random), 20);
    }

    /**
     * Constructs a server world
     * @param builder The builder used to construct the world
     */
    public static void constructServerWorld(WorldBuilder builder){
        builder.setNodeSpacing(15);
        builder.setWorldSize(80);
        builder.setType("server");
        builder.addBiome(new ForestBiome(random), 20);
        builder.addBiome(new DesertBiome(random), 20);
        builder.addBiome(new MountainBiome(random), 20);
    }

    /**
     * Constructs a world used for testing
     * @param builder The builder used to construct the world
     */
    public static void constructTestWorld(WorldBuilder builder){
        builder.setNodeSpacing(5);
        builder.setWorldSize(30);

        builder.setType("single_player");
//        builder.setStaticEntities(true);

        builder.addBiome(new ForestBiome(random), 10);
//        builder.addBiome(new DesertBiome(random), 10);
//        builder.addBiome(new MountainBiome(random), 10);
//        builder.addBiome(new VolcanicMountainsBiome(random), 10);
//        builder.addBiome(new SwampBiome(random), 10);
//        builder.addBiome(new SnowyMountainsBiome(random), 10);
    }
}
