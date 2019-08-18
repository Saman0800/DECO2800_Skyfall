package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import java.io.*;
import java.util.*;

import deco2800.skyfall.entities.structures.AbstractBuilding;
import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.AbstractEntity;


public class ConstructionManager extends AbstractManager {
    //This manager while control all features related to construction

    //Is menu visible
    private Boolean menuVisible;
    //Has menu been created and added
    private Boolean menuAdded;
    //Has the menu been set up
    private Boolean menuSetUp;
    private Window buildMenu;

    //Put window components here:

    public ConstructionManager() {
        menuVisible = false;
        menuAdded = false;
        menuSetUp = false;
    }

    //Start of UI

    //Called to either display or hide the build menu
    //Sets up the menu the first time its called
    //Updates the menu each time it is called
    public void displayWindow() {
        createWindow();
        setUpMenu();
        updateWindow();
        switchView();
    }

    //Create UI Window
    //Does nothing if the window already created
    private void createWindow() {
        Stage stage = GameManager.get().getStage();
        if (!menuAdded) {
            Skin skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));
            buildMenu = new Window("Construction", skin);
            hideBuildMenu();
            stage.addActor(buildMenu);
            menuAdded = true;
            System.out.println("Menu Added");
        } else {
            System.out.println("Menu already added");
        }
    }

    //Set up window
    //Does nothing if the window has already been set up
    private void setUpMenu() {
        if (!menuSetUp) {
            float width = GameManager.get().getStage().getWidth();
            float height = GameManager.get().getStage().getHeight();

            buildMenu.setHeight(3 * height / 4);
            buildMenu.setWidth(3 * width / 4);
            buildMenu.setPosition(width / 8, height / 8);

            //TODO: Add window components here: e.g. buttons, labels, etc
            //example buildMenu.add(button1);

            System.out.println("Window set up");
            menuSetUp = true;
        } else {
            System.out.println("Window already set up");
        }

    }

    //Called every time window is shown so that most accurate, up to date info is shown
    private void updateWindow() {
        //TODO: Add update code

        System.out.println("Window updated");
    }

    //Switches the menu on or odd
    private void switchView() {
        if (menuVisible) {
            hideBuildMenu();
        } else {
            showBuildMenu();
        }
    }

    private void showBuildMenu() {
        menuVisible = true;
        buildMenu.setVisible(true);
        System.out.println("Menu Shown");
    }

    private void hideBuildMenu() {
        menuVisible = false;
        buildMenu.setVisible(false);
        System.out.println("Menu Hidden");
    }

    //End of UI

    //Start of terrain Check

    // terrain map is a collection of terrains' building permission
    private TreeMap<String, Boolean> terrainMap = new TreeMap<String, Boolean>();

    // load file of initial terrains' building permission into the terrain map
    // file format as (texture name, boolean value) for one terrain type
    private boolean initializeTerrainMap(String fileBase) {
        if (fileBase == null) {
            return false;
        }

        try {
            File file = new File(fileBase);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] terrainInfo = line.split(",");
                if (terrainInfo.length != 2) {
                    throw new Exception("Incorrect file format");
                }
                String texture = terrainInfo[0];
                String boolStr = terrainInfo[1];
                boolean bool;
                if (boolStr.equalsIgnoreCase("true")
                        || boolStr.equalsIgnoreCase("false")) {
                    bool = Boolean.parseBoolean(boolStr);
                    this.terrainMap.put(texture, bool);
                } else {
                    throw new Exception("Incorrect file format");
                }
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    // update terrain's building permission to allow/disallow building
    public boolean updateTerrainMap(String texture, Boolean value) {
        if (texture == null || value == null) {
            return false;
        }
        this.terrainMap.put(texture, value);
        return true;
    }

    // use terrain map to check if tile(s) is buildable or not
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

    // inherit provided method inside tile's class to check if tile(s) is buildable or not
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

    // check if tile(s) on the world contains entities or not
    // non-empty entities in tiles interfere building and should be destroyed first
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

    // check if a building could be located on tiles or not
    // use most left-bottom position of a building with its size to check permission
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

    // TODO: building placement on world
    // place a building on the world
    public boolean placeBuilding(AbstractWorld worldMap, AbstractBuilding building) {
        // conflict with worldGen teams
        return true;
    }


    /**
     * return a list of how much of each relevant resources the player owns
     * When given a structure class gets its cost and compares it to the players inventory
     *
     * @param building         - AbstractBuilding
     * @param inventoryManager - player's inventory
     * @return True, if the player's inventory meets the inventory requirements, otherwise false
     */
    public Boolean invCheck(AbstractBuilding building, InventoryManager inventoryManager) {

        TreeMap<String, Integer> buildingCost = building.getCost();

        for (Map.Entry<String, Integer> entry : buildingCost.entrySet()) {

            String item = entry.getKey();
            Integer value = entry.getValue();
//          System.out.println(item + " => " + value);

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
        TreeMap<String, Integer> buildingCost = building.getCost();
        for (Map.Entry<String, Integer> entry : buildingCost.entrySet()) {

            String item = entry.getKey();
            Integer amount = entry.getValue();
            inventoryManager.inventoryDropMultiple(item, amount);
        }
    }
}
