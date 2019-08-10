package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import java.util.ArrayList;
import java.util.TreeMap;


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


    private TreeMap<String, Boolean> terrain_map = new TreeMap<String, Boolean>();

    //TODO: Add single tile terrain check
    //uses terrain_map to know if a tile is valid or not

    //TODO: Add multi tile terrain check
    //uses terrain_map to know if a tile is valid or not

    //TODO: Inventory check
    //return a list of how much of each relevant resources the player owns

    //TODO: Inventory remove
    //takes in a structure class and removes the material cost from
    //player inventory


    public void initialiseTerrainMap() {
        //TODO: create method
        //Get information for all terrain types from .txt document
        //texture, boolean
        //e.g. grass, true
        //run updateTerrainMap for each piece of information
    }

    public void updateTerrainMap(String texture, Boolean value) {
        terrain_map.put(texture, value);
    }
}
