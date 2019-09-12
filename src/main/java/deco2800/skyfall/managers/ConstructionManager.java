package deco2800.skyfall.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.io.*;
import java.util.*;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.buildings.BuildingEntity;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.buildings.BuildingFactory;
import deco2800.skyfall.buildings.BuildingWidgets;
import deco2800.skyfall.entities.structures.AbstractBuilding;
import deco2800.skyfall.buildings.BuildingType;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.AbstractEntity;


/**
 * Managers the construction process
 * Only a single instance should exist
 * Sets all build properties to false as nothing has been set up yet
 */
public class ConstructionManager extends TickableManager {
    //This manager while control all features related to construction

    //lmao
    private int spaghetti = 0;
    private int meatballs;

    private BuildingWidgets buildingWidgets;
    private BuildingFactory buildingFactory;

    private BuildingEntity buildingToBePlaced;

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
        buildingWidgets = BuildingWidgets.get(GameManager.get());
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

        TextButton buttonClose = new TextButton("Close Window", skin);

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
        buttonTable.add(buttonClose).width(cw/3.0f);

        tableContainer.setActor(table);
        settingMenu.addActor(tableContainer);
        stage.addActor(settingMenu);

        buttonClose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //call build function for specific building
                settingMenu.setVisible(false);
                menuSetUp =false;
                displayWindow();
            }
        });
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
            TextButton house = new TextButton("House", skin);
            TextButton storageUnit= new TextButton("Stroage Unit", skin);
            TextButton setting = new TextButton("Setting", skin);

            setting.setBounds(50, 150, 140, 40);

            house.setBounds(50, 450, 140, 40);
            storageUnit.setBounds(50, 350, 140, 40);


            buildMenu.addActor(house);
            buildMenu.addActor(storageUnit);
            buildMenu.addActor(setting);

            World world = GameManager.get().getWorld();

            for(int i = 0; i < buildingFactory.getCount(); i++){
                String name = BuildingType.values()[i].getName();
                TextButton building = new TextButton(name, skin);
                if (i < 3){
                    building.setBounds(50, 450 - i * 100, 140, 40);
                }else if(i < 6){
                    building.setBounds(300, 450 - (i - 3) * 100, 140, 40);
                }else{
                    building.setBounds(600, 450 - (i - 6) * 100, 140, 40);
                }

                final int FINALi = i;

                buildMenu.addActor(building);
                building.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y){

                        displayWindow();

                        //Place at character
                        //AbstractEntity mc = world.getSortedAgentEntities().get(world.getSortedAgentEntities().size() - 1);
                        //HexVector position = mc.getPosition();

                        //float row = 5;
                        //float col = 5;

                        System.out.println("hello");
                        //BuildingEntity toBePlaced = selectBuilding(FINALi, 0, 0);
                        spaghetti = 1;
                        meatballs = FINALi;

                    }
                });
            }



            house.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //call build function for specific building

                    /*
                    displayWindow();
                    //TODO implement permissions
                    //permission test have not been updated since switched to factory for buildings


                    AbstractEntity mc = world.getSortedAgentEntities().get(world.getSortedAgentEntities().size() - 1);
                    HexVector position = mc.getPosition();

                    float row = position.getRow();
                    float col = position.getCol();

                    BuildingEntity building1 = selectBuilding(i, );
                    building1.placeBuilding(building1.getRow(), building1.getCol(), building1.getHeight(), world);


                     */
                }
            });

            storageUnit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y){

                    /*
                    displayWindow();

                    //TODO implement permissions
                    //permission test have not been updated since switched to factory for buildings

                    AbstractEntity mc = world.getSortedAgentEntities().get(world.getSortedAgentEntities().size()-1);
                    HexVector position = mc.getPosition();

                    float row = position.getRow();
                    float col = position.getCol();

                    setBuildingToBePlaced(buildingFactory.createStorageUnit(row, col));
                    buildingToBePlaced.placeBuilding(buildingToBePlaced.getRow(), buildingToBePlaced.getCol(), buildingToBePlaced.getHeight(), world);

                     */
                }
            });

            setting.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y){
                    showSetting();
                }
                });
        }
    }

    public int getStatus() {
        int returnValue = spaghetti;
        spaghetti = 0;
        return returnValue;
    }

    public void build(World world, int x, int y) {
        System.out.printf("x = %d, y = %d\n", x, y);

        BuildingEntity buildingToBePlaced = selectBuilding(meatballs, x, y); 

        buildingToBePlaced.placeBuilding(x, y, buildingToBePlaced.getHeight(), world);
        //REMOVE THE INVENTORY
    }

    public void setNull() {
        buildingToBePlaced = null;
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
    public boolean verifyEntity(World worldMap, Tile... tiles) {
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
    public boolean isTilesBuildable(World worldMap, BuildingEntity building) {
        if (worldMap == null || building == null) {
            return false;
        }

        float xPos = building.getCol();
        float yPos = building.getRow();
        float xSize = building.getLength();
        float ySize = building.getWidth();
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
    public Boolean invCheck(BuildingEntity building, InventoryManager inventoryManager) {

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

    public void setBuildingToBePlaced (BuildingEntity building){
        buildingToBePlaced = building;
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

    /**
     *
     * @param index - index of the values in BuildingType
     * @param row - x position that building will be placed
     * @param col - y position that building will be placed
     * @return Building Entity that is selected
     */
    public BuildingEntity selectBuilding(int index, float row, float col){
        switch (index){
            default:
                return null;
            case 0:
                return buildingFactory.createCabin(row, col);
            case 1:
                return buildingFactory.createStorageUnit(row, col);
            case 2:
                return buildingFactory.createTownCentreBuilding(row, col);
            case 3:
                return buildingFactory.createFenceBuilding(row, col);
            case 4:
                return buildingFactory.createSafeHouse(row, col);
            case 5:
                return buildingFactory.createWatchTower(row, col);
            case 6:
                return buildingFactory.createCastle(row, col);


        }
    }

    // This method will be run per frame handled by GameManager automatically
    @Override
    public void onTick(long tick) {
        buildingWidgets.apply();
        buildingWidgets.update();
    }
}
