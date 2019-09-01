package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.io.*;
import java.util.*;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.buildings.BuildingFactory;
import deco2800.skyfall.buildings.BuildingWidgets;
import deco2800.skyfall.entities.structures.AbstractBuilding;
import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.AbstractEntity;

import javax.swing.*;


/**
 * Managers the construction process
 * Only a single instance should exist
 * Sets all build properties to false as nothing has been set up yet
 */
public class ConstructionManager extends AbstractManager {
    //This manager while control all features related to construction

    private BuildingWidgets buildingWidgets;
    private BuildingFactory buildingFactory;

    /**
     * Stores the current status of the build menu
     */
    private Boolean menuVisible;
    private Boolean menuAdded;
    private Boolean menuSetUp;

    /**
     * The menu through which the building process will be handled
     */
    private Window buildMenu;

    //TODO: Put window components here:

    public ConstructionManager() {
        menuVisible = false;
        menuAdded = false;
        menuSetUp = false;

        buildingFactory = new BuildingFactory();
//        buildingWidgets = BuildingWidgets.get();

        // testing requirement fro widget, removed it later
//        GameManager.get().getWorld().addEntity(buildingFactory.createHouse(0f, 0f));
    }

    //Start of UI

    /*
    Called to alternate the display status of the build menu
    Sets up the menu the first time its called
    Updates the menu each time it is called
    */
    public void displayWindow() {
        createWindow();
        setUpMenu();
        updateWindow();
        switchView();
    }

    /**
     * Creates and sets up the build menu
     * Does nothing after it has been called once
     */
    private void createWindow() {
        Stage stage = GameManager.get().getStage();
        if (!menuAdded) {
            Skin skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));
            buildMenu = new Window("Construction", skin);
            hideBuildMenu();
            stage.addActor(buildMenu);
            menuAdded = true;
        }
    }

    private void showSetting(){
        Stage stage = GameManager.get().getStage();
        Skin skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));
        Window settingMenu = new Window("settingMenu", skin);

        float width = GameManager.get().getStage().getWidth();
        float height = GameManager.get().getStage().getHeight();

        settingMenu.setHeight(3 * height / 4);
        settingMenu.setWidth(3 * width / 4);
        settingMenu.setPosition(width / 8, height / 8);

        buildMenu.setVisible(false);
        Container<Table> tableContainer = new Container<Table>();

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        float cw = width * 0.7f;
        float ch = height * 0.5f;

        tableContainer.setSize(cw, ch);
//        tableContainer.setPosition((sw - cw) / 2.0f, (sh - ch) / 2.0f);
        tableContainer.fillX();

        Table table = new Table(skin);

        Label topLabel = new Label("Construction Speed", skin);
        topLabel.setAlignment(Align.center);
        Slider slider = new Slider(0, 100, 1, false, skin);
//        Label anotherLabel = new Label("ANOTHER LABEL", skin);
//        anotherLabel.setAlignment(Align.center);

        Table buttonTable = new Table(skin);

        TextButton buttonA = new TextButton("Back to Main", skin);

        table.row().colspan(3).expandX().fillX();
        table.add(topLabel).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(slider).fillX();
        table.row().colspan(3).expandX().fillX();
//        table.add(anotherLabel).fillX();
        table.row().expandX().fillX();

        table.row().expandX().fillX();;

        table.add(buttonTable).colspan(3);

        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(buttonA).width(cw/3.0f);

        tableContainer.setActor(table);
        settingMenu.addActor(tableContainer);
        stage.addActor(settingMenu);
    }
    /**
     * Add components (such as buttons) to the build menu
     * Does nothing after it has been called once
     */
    private void setUpMenu() {

        if (!menuSetUp) {
            float width = GameManager.get().getStage().getWidth();
            float height = GameManager.get().getStage().getHeight();

            buildMenu.setHeight(3 * height / 4);
            buildMenu.setWidth(3 * width / 4);
            buildMenu.setPosition(width / 8, height / 8);

            //TODO: Add window components here: e.g. buttons, labels, etc

            menuSetUp = true;

            Skin skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));

            //to be improved when building factory has been created
            TextButton building1 = new TextButton("Building 1", skin);
            TextButton building2 = new TextButton("Building 2", skin);
            TextButton building3 = new TextButton("Building 3", skin);
            TextButton setting = new TextButton("Setting", skin);
            ImageIcon a = new ImageIcon("resources/world_structures/house1.png");
            Image b = new Image();


            building1.setBounds(50, 450, 140, 40);
            building2.setBounds(50, 350, 140, 40);
            building3.setBounds(50, 250, 140, 40);
            setting.setBounds(50, 150, 140, 40);


            buildMenu.addActor(building1);
            buildMenu.addActor(building2);
            buildMenu.addActor(building3);
            buildMenu.addActor(setting);

            building1.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //call build function for specific building

                }
            });

            building2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //call build function for specific building

                }
            });

            building3.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //call build function for specific building

                }
            });

            setting.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //call build function for specific building
                    showSetting();
                }
            });



        }
    }

    /**
     * Updates the information shown within the build menu
     */
    private void updateWindow() {
        //TODO: Add update code

    }

    /**
     * Alternates current display state of the build menu
     * If the window is currently visible its hidden
     * If the window is currently hidden its displayed
     */
    private void switchView() {
        if (menuVisible) {
            hideBuildMenu();
        } else {
            showBuildMenu();
        }
    }

    /**
     * Displays the build menu
     */
    private void showBuildMenu() {
        menuVisible = true;
        buildMenu.setVisible(true);
    }

    /**
     * Hides the build menu
     */
    private void hideBuildMenu() {
        menuVisible = false;
        buildMenu.setVisible(false);
    }

    //End of UI

    // Start of buildability on tiles check

    // terrain map is a collection of terrains' building permission
    private TreeMap<String, Boolean> terrainMap = new TreeMap<>();

    /**
     * Load a file of initial terrains' building permission into the terrain map, while
     * the file format as (texture name, boolean value) for one terrain type.
     *
     * @param fileBase - a file's name
     * @return true if file is successfully loaded, otherwise false
     */
    private boolean initializeTerrainMap(String fileBase) {
        if (fileBase == null) {
            return false;
        }

        File file = new File(fileBase);
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr);) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] terrainInfo = line.split(",");
                if (terrainInfo.length != 2) {
                    throw new IOException("Incorrect file format");
                }
                String texture = terrainInfo[0];
                String boolStr = terrainInfo[1];
                boolean bool;
                if (boolStr.equalsIgnoreCase("true")
                        || boolStr.equalsIgnoreCase("false")) {
                    bool = Boolean.parseBoolean(boolStr);
                    this.terrainMap.put(texture, bool);
                } else {
                    throw new IOException("Incorrect file format");
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Update a terrain's building permission to allow/disallow building.
     *
     * @param texture - a terrain's texture name on tile
     * @param value   - boolean value to allow/disallow building for the terrain
     * @return true if a terrain building permission is updated, otherwise false
     */
    public boolean updateTerrainMap(String texture, Boolean value) {
        if (texture == null || value == null) {
            return false;
        }
        this.terrainMap.put(texture, value);
        return true;
    }

    /**
     * Use terrain map to check if tile(s) is buildable or not.
     * Note that if terrain type is not in the terrain map, the check will be skipped.
     *
     * @param tiles - tiles that will be checked their terrain type
     * @return true if tile's terrain type allow building, otherwise false
     */
    public boolean verifyTerrain(Tile... tiles) {
        if (tiles == null) {
            return false;
        }
        for (Tile tile : tiles) {
            if (tile == null) {
                return false;
            }
            String texture = tile.getTextureName();
            if (terrainMap.containsKey(texture) && (!terrainMap.get(texture))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Inherit provided method inside tile's class to check if tile(s) is buildable or not.
     *
     * @param tiles - tiles that will be checked their biome type
     * @return true if tile's biome type allow building, otherwise false
     */
    public boolean verifyBiome(Tile... tiles) {
        if (tiles == null) {
            return false;
        }
        for (Tile tile : tiles) {
            if (tile == null) {
                return false;
            }
            if (!tile.getIsBuildable()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if tile(s) on the world contains entities or not.
     * Non-empty entities in tiles interfere building and should be destroyed first.
     *
     * @param worldMap - a game's world
     * @param tiles    - tiles that will be checked if contain entities
     * @return true if empty entities on tiles, otherwise false
     */
    public boolean verifyEntity(AbstractWorld worldMap, Tile... tiles) {
        if (worldMap == null || tiles == null) {
            return false;
        }
        for (Tile tile : tiles) {
            if (tile == null) {
                return false;
            }
            float col = tile.getCol();
            float row = tile.getRow();
            for (AbstractEntity entity : worldMap.getEntities()) {
                if ((entity.getCol() == col) && (entity.getRow() == row)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if a building could be located on tiles or not, while the process includes
     * terrain, biome and entity check.
     * Use most left-bottom position of a building with its size to check permission.
     *
     * @param worldMap - a game's world
     * @param building - a construction object
     * @return true if a building is buildable on tiles, otherwise false
     */
    public boolean isTilesBuildable(AbstractWorld worldMap, AbstractBuilding building) {
        if (worldMap == null || building == null) {
            return false;
        }

        float xPos = building.getXcoord();
        float yPos = building.getYcoord();
        float xSize = building.getXSize();
        float ySize = building.getYSize();
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Tile tile = worldMap.getTile(xPos + xSize, yPos + ySize);
                if (tile == null) {
                    return false;
                }
                if (!verifyTerrain(tile) || !verifyBiome(tile) || !verifyEntity(worldMap, tile)) {
                    return false;
                }
            }
        }
        return true;
    }

    // End of buildability on tiles check

    // Start of inventory code

    /**
     * return a list of how much of each relevant resources the player owns
     * When given a structure class gets its cost and compares it to the players inventory
     *
     * @param building         - AbstractBuilding
     * @param inventoryManager - player's inventory
     * @return True, if the player's inventory meets the inventory requirements, otherwise false
     */
    public Boolean invCheck(AbstractBuilding building, InventoryManager inventoryManager) {

        Map<String, Integer> buildingCost = building.getCost();

        for (Map.Entry<String, Integer> entry : buildingCost.entrySet()) {

            String item = entry.getKey();
            Integer value = entry.getValue();
            // System.out.println(item + " => " + value);

            if (value.intValue() > inventoryManager.getAmount(item)) {
                return false;
            }
        }

        return true;
    }


    /**
     * Takes in a structure class and removes the material cost from player inventory
     *
     * @param building         - AbstractBuilding
     * @param inventoryManager - player's inventory
     * @pre: Assume that building has been verified against inventoryAmount in inventoryManager
     */
    public void invRemove(AbstractBuilding building, InventoryManager inventoryManager) {
        Map<String, Integer> buildingCost = building.getCost();
        for (Map.Entry<String, Integer> entry : buildingCost.entrySet()) {

            String item = entry.getKey();
            Integer amount = entry.getValue();
            inventoryManager.inventoryDropMultiple(item, amount);
        }
    }

    // End of inventory code

    /**
     *
     * @param buildings
     */
    public boolean mergeBuilding(AbstractBuilding[] buildings, InventoryManager inventoryManager) {

        if (buildings.length == 0) return false;
        String className = buildings[0].getClass().getName();
        for (int i = 0; i < buildings.length; i++) {

            if (buildings[i].getClass().getName() != className) {
                return false;
            }

            if (i != 0) {
                invRemove(buildings[i], inventoryManager);
            }
        }

        buildings[0].placeBuilding(buildings[0].getXcoord(), buildings[0].getYcoord(),
                buildings.length, GameManager.get().getWorld());

        return true;
    }
}
