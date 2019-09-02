package deco2800.skyfall.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.Tile;
import org.lwjgl.Sys;

/**
 *  A BuildingWidgets is a UI widgets for existing building entities, and provides some handling
 *  methods shown on the screen to the existing building entities.
 */
public class BuildingWidgets implements TouchDownObserver {
    private static BuildingWidgets instance = null;

    private Stage stage;
    private Skin skin;
    private World world;
    private InputManager inputManager;

    private Table menu;
    private Label label;
    private TextButton upgradeBtn;
    private ClickListener upgradeListener;
    private TextButton destroyBtn;
    private ClickListener destroyListener;

    /**
     * Returns an instance of the building widgets.
     * @return the building widgets
     */
    public static BuildingWidgets get(Stage stage, Skin skin, World world, InputManager input) {
        if (instance == null) {
            return new BuildingWidgets(stage, skin, world, input);
        }
        return instance;
    }

    /**
     * Private constructor to enforce use of get().
     */
    private BuildingWidgets(Stage stage, Skin skin, World world, InputManager input) {
        try {
            this.stage = stage;
            this.skin = skin;
            this.world = world;
            this.inputManager = input;

            if (this.skin == null) {
                // using a skin for test, removed it later
                this.skin = new Skin(Gdx.files.internal("asserts/skin_for_test/uiskin.json"));
            }

            this.menu = new Table();
            this.label = new Label("Name", this.skin);
            this.upgradeBtn = new TextButton("Update", this.skin);
            this.destroyBtn = new TextButton("Destroy", this.skin);

            this.menu.setVisible(false);
            this.menu.align(Align.left|Align.top);
            this.menu.add(label).padBottom(3);
            this.menu.row();
            this.menu.add(upgradeBtn).padBottom(3);
            this.menu.row();
            this.menu.add(destroyBtn);

            this.stage.addActor(this.menu);
            this.inputManager.addTouchDownListener(this);
        } catch (Exception e) {
            // print exception trace, but no impact to game
        }
    }

    /**
     * Updates the building object when update button is clicked.
     * @param building a building is selected on the world
     */
    private void updateBuilding(BuildingEntity building) {
        // TODO: check if building is upgradable then update the building
    }

    /**
     * Destroys the building object when destroy button is clicked.
     * @param building a building is selected on the world
     */
    private void destroyBuilding(BuildingEntity building) {
        this.world.removeEntity(building);
    }

    /**
     * Sets up a widget with specific information of a selected building and its position, then showing it.
     * @param building a building is selected on the world
     */
    private void setWidgets(BuildingEntity building) {
        float[] wCords = WorldUtil.colRowToWorldCords(building.getCol(), building.getRow());
        this.label.setText(building.getObjectName());
        this.menu.setPosition(this.stage.getWidth()/2 + wCords[0] - GameManager.get().getCamera().position.x,
                this.stage.getHeight()/2 + wCords[1] - GameManager.get().getCamera().position.y);
        this.upgradeBtn.addListener(upgradeListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateBuilding(building);
                menu.setVisible(false);
            }
        });
        this.destroyBtn.addListener(destroyListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                destroyBuilding(building);
                menu.setVisible(false);
            }
        });
    }

    /**
     * Get the widget layout.
     * @return an table object forms widget
     */
    public Table getMenu() {
        return this.menu;
    }

    /**
     * Runs the method when right button of a mouse is clicked on the world. Used to check and get a specific
     * building object on the mouse's position.
     * @param screenX screen X coordinate
     * @param screenY screen Y coordinate
     * @param pointer index or order of touch down event
     * @param button mouse button of left (0) or right (1)
     */
    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        // only allow right click to handle buildings
        if (button != 1) {
            return;
        }
        float[] mousePos = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPos = WorldUtil.worldCoordinatesToColRow(mousePos[0], mousePos[1]);

        Tile tile = this.world.getTile(clickedPos[0], clickedPos[1]);
        if (tile == null) {
            return;
        }

        // hide the building widget initially
        this.menu.setVisible(false);
        if (upgradeListener != null && destroyListener != null) {
            this.upgradeBtn.removeListener(upgradeListener);
            this.destroyBtn.removeListener(destroyListener);
        }

        for (AbstractEntity entity : this.world.getEntities()) {
            if (!tile.getCoordinates().equals(entity.getPosition())) {
                continue;
            }
            if (entity instanceof BuildingEntity) {
                // show the building widgets if a building is clicked
                setWidgets((BuildingEntity)entity);
                this.menu.setVisible(true);
                break;
            }
        }
    }
}
