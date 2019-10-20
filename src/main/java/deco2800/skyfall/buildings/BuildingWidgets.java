package deco2800.skyfall.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.Timer;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.Tile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * A BuildingWidgets is a UI widgets for existing building entities, and
 * provides some handling methods shown on the screen to the existing building
 * entities.
 */
public class BuildingWidgets {

    // a logger
    private final Logger logger = LoggerFactory.getLogger(BuildingWidgets.class);

    private static BuildingWidgets instance = null;

    private GameManager gm;
    private Stage stage;
    private Skin skin;
    private World world;

    private BuildingEntity building;
    private Table menu;
    private Label label;
    private ProgressBar healthBar;
    private TextButton upgradeBtn;
    private ClickListener upgradeListener;
    private TextButton destroyBtn;
    private ClickListener destroyListener;

    private TextButton interactBtn;
    private ClickListener interactListener;

    // the game camera position
    private HexVector cameraPos;

    /**
     * Returns an instance of the building widgets.
     *
     * @return the building widgets
     */
    public static BuildingWidgets get(GameManager gm) {
        if (instance == null) {
            instance = new BuildingWidgets(gm);
        }
        return instance;
    }

    /**
     * Private constructor to enforce use of get().
     */
    private BuildingWidgets(GameManager gm) {
        try {
            this.gm = gm;
            this.stage = gm.getStage();
            this.skin = gm.getSkin();
            this.world = gm.getWorld();

            if (this.skin == null) {
                this.skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));
            }

            this.menu = new Table();
            this.label = new Label("Name", this.skin);
            this.healthBar = createHealthBar();
            this.upgradeBtn = new TextButton("Upgrade", this.skin);
            this.interactBtn = new TextButton("Interact", this.skin);
            this.destroyBtn = new TextButton("Destroy", this.skin);

            this.menu.setVisible(false);
            this.menu.align(Align.left | Align.top);
            this.menu.add(label).padBottom(3);
            this.menu.row();
            this.menu.add(healthBar).padBottom(3).width(100);
            this.menu.row();
            this.menu.add(upgradeBtn).padBottom(3).width(100);
            this.menu.row();
            this.menu.add(interactBtn).padBottom(3).width(100);
            this.menu.row();
            this.menu.add(destroyBtn).width(100);
            this.stage.addActor(this.menu);

            this.cameraPos = new HexVector();
        } catch (Exception e) {
            logger.warn("Null skin provided and the widget will not works.");
        }
    }

    /**
     * Returns a health bar for showing current health of a building.
     *
     * @return a health bar
     */
    private ProgressBar createHealthBar() {
        // progress bar style setup
        Pixmap bg = new Pixmap(100, 15, Pixmap.Format.RGBA8888);
        bg.setColor(Color.RED);
        bg.fill();
        TextureRegionDrawable bgBar = new TextureRegionDrawable(new TextureRegion(new Texture(bg)));
        bg.dispose();
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = bgBar;

        Pixmap healthEnd = new Pixmap(0, 15, Pixmap.Format.RGBA8888);
        healthEnd.setColor(Color.GREEN);
        healthEnd.fill();
        TextureRegionDrawable endBar = new TextureRegionDrawable(new TextureRegion(new Texture(healthEnd)));
        healthEnd.dispose();
        progressBarStyle.knob = endBar;

        Pixmap healthFill = new Pixmap(100, 15, Pixmap.Format.RGBA8888);
        healthFill.setColor(Color.GREEN);
        healthFill.fill();
        TextureRegionDrawable fillBar = new TextureRegionDrawable(new TextureRegion(new Texture(healthFill)));
        healthFill.dispose();
        progressBarStyle.knobBefore = fillBar;

        // progress bar creation with above style
        ProgressBar newHealthBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);
        newHealthBar.setValue(1.0f);
        newHealthBar.setAnimateDuration(0.25f);
        newHealthBar.setBounds(0, 0, 100, 15);
        return newHealthBar;
    }

    /**
     * Returns the widget container.
     *
     * @return the table object forms the widget
     */
    public Table getMenu() {
        return menu;
    }

    /**
     * Checks building upgrade costs and inventory resources.
     *
     * @param building the building clicked on the world
     * @return true if enough resources provided for a upgrade, otherwise false
     */
    private boolean checkCost(BuildingEntity building) {
        InventoryManager inventoryManager = gm.getManager(InventoryManager.class);
        Map<String, Integer> resources = inventoryManager.getQuickAccess();
        Map<String, Integer> costs = building.getCost();

        if (costs == null) {
            logger.warn("The widget can't read the costs of building {}.",
                    building.getObjectName() + building.getEntityID());
            return false;
        }

        for (Map.Entry<String, Integer> cost : costs.entrySet()) {
            if (!resources.containsKey(cost.getKey()) || resources.get(cost.getKey()) < cost.getValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Based on option to remove or add building resources into the inventory.
     *
     * @param costs  a list of resources with their amount
     * @param option remove resources if is 0, add resources if is 1
     */
    private void handleCost(Map<String, Integer> costs, int option) {
        InventoryManager inventoryManager = gm.getManager(InventoryManager.class);
        for (Map.Entry<String, Integer> cost : costs.entrySet()) {
            String resource = cost.getKey();
            Integer amount = cost.getValue();
            switch (option) {
            case 0:
                for (int i = 0; i < amount; i++) {
                    inventoryManager.quickAccessRemove(resource);
                }
                break;
            case 1:
                for (int i = 0; i < amount; i++) {
                    inventoryManager.quickAccessAdd(resource);
                }
                break;
            default:
                logger.warn("Out of options provided");
                break;
            }
        }
    }

    /**
     * Upgrades the building object when upgrade button is clicked.
     *
     * @param building a building is selected on the world
     */
    private void upgradeBuilding(BuildingEntity building) {
        int nextLevel = building.getBuildingLevel() + 1;

        if (building.isUpgradable() && building.getTextures().containsKey("level" + nextLevel)) {
            if (checkCost(building)) {
                handleCost(building.getCost(), 0);
                building.setTexture(building.getTextures().get("level" + nextLevel));
                building.setBuildingLevel(nextLevel);
                menu.setVisible(false);
                return;
            }

            // to notify not enough resources for a upgrade
            upgradeBtn.setText("Fail upgrade");
        } else {
            // max level or no relevant level texture provided
            upgradeBtn.setText("Max Level");
        }

        // upgrade button text back to normal after notice
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                upgradeBtn.setText("Upgrade");
            }
        }, 2);
    }

    /**
     * Destroys the building object when destroy button is clicked, and returns
     * customized percent of recourse costs back to inventory and also notes that
     * the percent's range is from 0 to 1.
     *
     * @param building a building is selected on the world
     * @param percent  the percent of resource costs back to inventory
     */
    private void destroyBuilding(BuildingEntity building, float percent) {
        Map<String, Integer> costs = building.getCost();

        if (costs == null) {
            logger.warn("The widget can't read the costs of building {}.",
                    building.getObjectName() + building.getEntityID());
            world.removeEntity(building);
            return;
        }

        for (Map.Entry<String, Integer> cost : costs.entrySet()) {
            int newValue = (int) Math.floor(percent * cost.getValue());
            costs.replace(cost.getKey(), cost.getValue(), newValue);
        }
        handleCost(costs, 1);
        world.removeEntity(building);
    }

    /**
     * Interact methods for buildings
     *
     * @param building building selected from world.
     */
    private void interactBuilding(BuildingEntity building) {
        switch (building.getBuildingType()) {
        case CABIN:
            building.cabinInteract();
            break;
        case FENCE:
            building.fenceInteract();
            break;
        case CASTLE:
            building.castleInteract();
            break;
        case SAFEHOUSE:
            building.safehouseInteract();
            break;
        case TOWNCENTRE:
            building.towncentreInteract();
            break;
        case WATCHTOWER:
            building.watchtowerInteract();
            break;
        case STORAGE_UNIT:
            default:
                break;
        }
    }

    /**
     * Sets up a container of the building widget with correct position settings.
     *
     * @param building a building is selected on the world
     */
    private void setMenu(BuildingEntity building) {
        float[] wCords = WorldUtil.colRowToWorldCords(building.getCol(), building.getRow());
        cameraPos.setCol(gm.getCamera().position.x);
        cameraPos.setRow(gm.getCamera().position.y);
        menu.setPosition(stage.getWidth() / 2 + wCords[0] - cameraPos.getCol() - menu.getMinWidth(),
                stage.getHeight() / 2 + wCords[1] - cameraPos.getRow() + menu.getMinHeight());
    }

    /**
     * Sets up a health bar inside the building widget with its relevant functions.
     *
     * @param building a building is selected on the world
     */
    private void setHealthBar(BuildingEntity building) {
        float health = (float) building.getCurrentHealth() / building.getInitialHealth();
        healthBar.setValue(health);
        if (health < 0f) {
            healthBar.setValue(0f);
        } else if (health > 1f) {
            healthBar.setValue(1f);
        }
    }

    /**
     * Sets up a upgrade button inside the building widget with its relevant
     * functions.
     *
     * @param building a building is selected on the world
     */
    private void setUpgradeBtn(BuildingEntity building) {
        if (upgradeListener != null) {
            upgradeBtn.removeListener(upgradeListener);
        }
        upgradeListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                upgradeBuilding(building);
            }
        };
        upgradeBtn.addListener(upgradeListener);
    }

    /**
     * Sets up a interact button inside the building widget with its relevant
     * functions.
     *
     * @param building a building is selected on the world
     */
    private void setInteractBtn(BuildingEntity building) {
        if (interactListener != null) {
            interactBtn.removeListener(interactListener);
        }
        interactListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                interactBuilding(building);
            }
        };
        interactBtn.addListener(interactListener);
    }

    /**
     * Sets up a destroy button inside the building widget with its relevant
     * functions.
     *
     * @param building a building is selected on the world
     */
    private void setDestroyBtn(BuildingEntity building) {
        if (destroyListener != null) {
            destroyBtn.removeListener(destroyListener);
        }
        destroyListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                destroyBuilding(building, 0.3f);
                menu.setVisible(false);
            }
        };
        destroyBtn.addListener(destroyListener);
    }

    /**
     * Sets up a widget with specific information of a selected building and its
     * position, then showing it.
     *
     * @param building a building is selected on the world
     */
    private void setWidgets(BuildingEntity building) {
        label.setText(building.getObjectName() + " Lv" + building.getBuildingLevel());
        setMenu(building);
        setHealthBar(building);
        setUpgradeBtn(building);
        setInteractBtn(building);
        setDestroyBtn(building);
    }

    public void update() {
        // update widget position
        if (cameraPos.getCol() != gm.getCamera().position.x || cameraPos.getRow() != gm.getCamera().position.y) {
            menu.setPosition(menu.getX() - gm.getCamera().position.x + cameraPos.getCol(),
                    menu.getY() - gm.getCamera().position.y + cameraPos.getRow());
            cameraPos.setCol(gm.getCamera().position.x);
            cameraPos.setRow(gm.getCamera().position.y);
        }

        // update health bar value
        if (menu.isVisible()) {
            int value = building.getCurrentHealth() / building.getInitialHealth();
            if ((float) value != healthBar.getValue()) {
                setHealthBar(building);
            }
        }
    }

    public void apply() {
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            float[] mousePos = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
            float[] clickedPos = WorldUtil.worldCoordinatesToColRow(mousePos[0], mousePos[1]);

            Tile tile = world.getTile(clickedPos[0], clickedPos[1]);
            if (tile == null) {
                return;
            }

            // initialize widget and building information
            menu.setVisible(false);
            building = null;

            for (AbstractEntity entity : world.getEntities()) {
                if (entity instanceof BuildingEntity) {
                    Collider collider = ((BuildingEntity) entity).getCollider();
                    float xLength = collider.getX() + collider.getXLength();
                    float yLength = collider.getY() + collider.getYLength();

                    // NOTE: a collider should not be null for every building
                    if ((collider.getX() <= mousePos[0]) && (mousePos[0] <= xLength)
                            && (collider.getY() <= mousePos[1]) && (mousePos[1] <= yLength)) {
                        // show the building widgets if a building is clicked
                        building = (BuildingEntity)entity;
                        setWidgets((BuildingEntity)entity);
                        menu.setVisible(true);
                        break;
                    }
                }
            }
        }
    }
}
