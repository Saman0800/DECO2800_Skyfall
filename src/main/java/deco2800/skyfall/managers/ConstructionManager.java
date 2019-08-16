package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import java.io.*;
import java.util.AbstractList;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
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

    public ConstructionManager(){
        menuVisible = false;
        menuAdded = false;
        menuSetUp = false;
    }

    //Start of UI
    public void displayWindow(){
        createWindow();
        setUpMenu();
        updateWindow();
        switchView();
    }

    //Create UI Window
    //Does nothing if window already created
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
    private void setUpMenu() {
        if (!menuSetUp) {
            float width = GameManager.get().getStage().getWidth();
            float height = GameManager.get().getStage().getHeight();

            buildMenu.setHeight(3 * height / 4);
            buildMenu.setWidth(3 * width / 4);
            buildMenu.setPosition(width / 8, height / 8);

            //Add window components here: e.g. buttons, labels, etc
            //example buildMenu.add(button1);

            System.out.println("Window set up");
            menuSetUp = true;
        } else {
            System.out.println("Window already set up");
        }

    }

    //Called every time window is shown so that most accurate, up to date info is shown
    private void updateWindow() {
        //Update window components here:

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

    // TODO: check the buildability of tiles, in term of terrain, boime and entity
    // terrain map is a collection of terrains with their building permission
    private TreeMap<String, Boolean> terrainMap = new TreeMap<String, Boolean>();

    // load file of initial terrain permission into the terrain map
    // file format as (texture name, boolean value) for one terrain
    private void initializeTerrainMap(String fileBase) {
        try {
            File file = new File(fileBase);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] terrainInfo = line.split(",");
                String texture = terrainInfo[0];
                String boolStr = terrainInfo[1];
                boolean bool;

                if (boolStr.equalsIgnoreCase("true")
                        || boolStr.equalsIgnoreCase("false")) {
                    bool = Boolean.parseBoolean(boolStr);
                } else {
                    throw new Exception("Incorrect file format");
                }

                this.terrainMap.put(texture, bool);
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update terrain building permission to allow/disallow building
    public void updateTerrainMap(String texture, Boolean value) {
        if (texture == null || value == null) {
            return;
        }
        if (terrainMap.containsKey(texture)) {
            terrainMap.put(texture, value);
        }
    }

    // use terrain map to check if tile(s) is buildable or not
    public boolean verifyTile(Tile ...tiles) {
        for (Tile tile : tiles) {
            if (tile == null) {
                return false;
            }
            String texture = tile.getTextureName();
            if (!terrainMap.containsKey(texture)) {
                return false;
            }
            if (terrainMap.containsKey(texture) && (!terrainMap.get(texture))) {
                return false;
            }
        }
        return true;
    }

    // use terrain map to check if a collection of tiles is buildable or not
    public boolean verifyRegion(List<Tile> tiles) {
        if (tiles == null) {
            return false;
        }
        for (Tile tile : tiles) {
            if (tile == null) {
                return false;
            }
            String texture = tile.getTextureName();
            if (!terrainMap.containsKey(texture)) {
                return false;
            }
            if (terrainMap.containsKey(texture) && (!terrainMap.get(texture))) {
                return false;
            }
        }
        return true;
    }

    // use tile class's biome method provided to know buildability
    public boolean verifyBiome(Tile ...tiles) {
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

    // Non-empty entities in a tile can't be built and should be destroyed first
    // check if a region on the world contains entities or not
    public boolean verifyEntity(AbstractWorld worldMap, Tile ...tiles) {
        if (worldMap == null) {
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

    // check if a building could be located on a region or not
    // use most left-bottom position of a building with its size to check permission
    public boolean isBuildable(AbstractWorld worldMap, AbstractEntity building, int xSize, int ySize) {
        if (building == null || worldMap == null) {
            return false;
        }

        float col = building.getCol();
        float row = building.getRow();
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Tile tile = worldMap.getTile(col + xSize, row + ySize);
                if (tile == null) {
                    return false;
                }
                if (terrainMap.containsKey(tile.getTextureName())) {
                    if (!verifyTile(tile) || !verifyBiome(tile) || !verifyEntity(worldMap, tile)) {
                        return false;
                    }
                }
                if (!verifyBiome(tile) || !verifyEntity(worldMap, tile)) {
                    return false;
                }
            }
        }
        return true;
    }

    // TODO: world placement of building
    // place a building on the world
    public boolean placeBuilding(AbstractWorld worldMap, AbstractEntity building) {
        // wait for building class method to gain size and position
        return true;
    }

    //TODO: Inventory check
    //return a list of how much of each relevant resources the player owns

//    When given a structure class gets its cost and compares it to the players
//        inventory and returning a Boolean if the player meets the inventory requirements.

//    public Boolean invCheck(Structure building){
//
//    }
    //TODO: Inventory remove
    //takes in a structure class and removes the material cost from
    //player inventory
    //Takes in a structure class and removes it's material cost from
    //the players inventory, no return

//    public void invRemove(Structure building){ }
}

//class Structure
//{
//
//
//
//}