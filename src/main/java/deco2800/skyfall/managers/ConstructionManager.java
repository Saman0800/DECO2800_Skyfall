package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import java.io.*;
import java.util.List;
import java.util.TreeMap;
import deco2800.skyfall.worlds.Tile;


public class ConstructionManager extends AbstractManager {
    //This manager while control all features related to construction

    //Is menu visible
    private Boolean menuVisible = false;
    //Has menu been created and added
    private Boolean menuAdded = false;

    private Window buildMenu;

    //Start of UI
    //Create UI Window
    //Does nothing if window already created
    public void createWindow(Stage stage){
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
    //Called every time window is shown so that most accurate (up to date info is shown"
    public void initialiseMenu(){
        System.out.println("Window Initialised");
    }

    //Switches the menu on or odd
    public void switchView(){
        if(menuVisible){
            hideBuildMenu();
        } else {
            showBuildMenu();
        }
    }

    public void showBuildMenu(){
        menuVisible = true;
        buildMenu.setVisible(true);
        System.out.println("Menu Shown");
    }

    public void hideBuildMenu(){
        menuVisible = false;
        buildMenu.setVisible(false);
        System.out.println("Menu Hidden");
    }

    // terrain map is list of terrains with their building permission
    private TreeMap<String, Boolean> terrainMap = new TreeMap<String, Boolean>();

    // load terrain permission file into terrain map
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

    public void updateTerrainMap(String texture, Boolean value) {
        terrainMap.put(texture, value);
    }

    // use terrain map to verify if tile(s) is built valid or not
    public boolean verifyTile(Tile ...tiles) {
        for (Tile tile : tiles) {
            String texture = tile.getTextureName();
            if (terrainMap.containsKey(texture)) {
                if (terrainMap.get(texture) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    // uses terrain_map to verify if a region (list of tiles) is built valid or not
    public boolean verifyRegion(List<Tile> tiles) {
        for (Tile tile : tiles) {
            String texture = tile.getTextureName();
            if (terrainMap.containsKey(texture)) {
                if (terrainMap.get(texture) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    //TODO: Inventory check
    //return a list of how much of each relevant resources the player owns

    //TODO: Inventory remove
    //takes in a structure class and removes the material cost from
    //player inventory
}
