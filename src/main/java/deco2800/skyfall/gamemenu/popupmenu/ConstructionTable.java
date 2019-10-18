package deco2800.skyfall.gamemenu.popupmenu;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import deco2800.skyfall.buildings.BuildingEntity;
import deco2800.skyfall.buildings.BuildingFactory;
import deco2800.skyfall.buildings.BuildingType;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.worlds.world.World;

/**
 * A class for blueprint shop table pop up.
 */
public class ConstructionTable extends AbstractPopUpConstruction {
    private final StatisticsManager sm;
    private BuildingType buildingID;

    /**
     * Constructs a blueprint shop table.
     *
     * @param stage           Current stage.
     * @param exit            Exit button if it has one.
     * @param textureNames    Names of the textures.
     * @param tm              Current texture manager.
     * @param gameMenuManager Current game menu manager.
     */
    public ConstructionTable(Stage stage, ImageButton exit, String[] textureNames, TextureManager tm,
            GameMenuManager gameMenuManager, StatisticsManager sm) {
        super(stage, exit, textureNames, tm, gameMenuManager);
        this.draw();
        this.sm = sm;
    }

    /**
     * {@inheritDoc}
     *
     * Draw the whole blueprint shop table.
     */
    @Override
    public void draw() {
        super.draw();

        Image infoBar = this.setNewInfoBar();

        Table infoPanel = new Table();
        infoPanel.setSize(410, 400);
        infoPanel.setPosition(25, 18);
        infoPanel.setBackground(gameMenuManager.generateTextureRegionDrawableObject("info_panel"));

        this.blueprintPanel = new Table();

        blueprintTable.addActor(infoBar);
        blueprintTable.addActor(infoPanel);
        blueprintTable.addActor(this.blueprintPanel);
        blueprintTable.setVisible(false);
        stage.addActor(blueprintTable);
    }

    /**
     * Actually display the blueprints and their prices (if not bought)
     */
    public void updateBlueprintShopPanel() {
        blueprintPanel.clear();
        blueprintPanel.setName("resourcePanel");
        blueprintPanel.setSize(410, 400);
        blueprintPanel.setPosition(475, 18);
        blueprintPanel.setBackground(gameMenuManager.generateTextureRegionDrawableObject("menu_panel"));

        List<BuildingType> unlocked = sm.getCharacter().getCraftedBuildings();

        int count = 0;
        int xpos = 20;
        int ypos = 280;

        for (BuildingType b : unlocked) {

            ImageButton icon = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject(b.getName()));
            icon.setName("icon");
            icon.setSize(100, 100);
            icon.setPosition(xpos + (float) count * 130, ypos);

            icon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Pixmap pm = new Pixmap(Gdx.files.internal("resources/world_structures/house3.png"));
                    Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
                    pm.dispose();
                    buildingID = b;
                    sm.getCharacter().setToBuild(true);
                    hide();
                }
            });

            blueprintPanel.addActor(icon);

            count++;

            if ((count) % 3 == 0) {
                ypos -= 120;
                count = 0;
            }
        }
    }

    public void setBuildingID(BuildingType buildingID) {
        this.buildingID = buildingID;
    }

    /**
     *
     * @param type - index of the values in BuildingType
     * @param row  - x position that building will be placed
     * @param col  - y position that building will be placed
     * @return Building Entity that is selected
     */
    public BuildingEntity selectBuilding(BuildingType type, float row, float col) {
        BuildingFactory buildingFactory = new BuildingFactory();
        switch (type) {
        case CABIN:
            return buildingFactory.createCabin(row, col);
        case STORAGE_UNIT:
            return buildingFactory.createStorageUnit(row, col);
        case TOWNCENTRE:
            return buildingFactory.createTownCentreBuilding(row, col);
        case FENCE:
            return buildingFactory.createFenceBuilding(row, col);
        case SAFEHOUSE:
            return buildingFactory.createSafeHouse(row, col);
        case WATCHTOWER:
            return buildingFactory.createWatchTower(row, col);
        case CASTLE:
            return buildingFactory.createCastle(row, col);
        case FORESTPORTAL:
            return buildingFactory.createForestPortal(row, col);
        case DESERTPORTAL:
            return buildingFactory.createDesertPortal(row, col);
        case MOUNTAINPORTAL:
            return buildingFactory.createMountainPortal(row, col);
        case VOLCANOPORTAL:
            return buildingFactory.createVolcanoPortal(row, col);
        default:
            return buildingFactory.createCabin(row, col);
        }
    }

    /**
     * Places a structure in the world.
     * 
     * @param world - World to place in
     * @param x     - x coordinate
     * @param y     - y coordinate
     */
    public void build(World world, float x, float y) {
        BuildingEntity buildingToBePlaced = selectBuilding(buildingID, x, y);
        // Permissions
        buildingToBePlaced.placeBuilding(x, y, buildingToBePlaced.getHeight(), world);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        sm.getCharacter().setToBuild(true);
    }

    public BuildingType getBuildingID() {
        return buildingID;
    }
}
