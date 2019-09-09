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

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.Tile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  A BuildingWidgets is a UI widgets for existing building entities, and provides some handling
 *  methods shown on the screen to the existing building entities.
 */
public class BuildingWidgets {

    // a debug logger
    private final transient Logger logger = LoggerFactory.getLogger(BuildingWidgets.class);

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

    // the game camera position
    private HexVector cameraPos;

    /**
     * Returns an instance of the building widgets.
     * @return the building widgets
     */
    public static BuildingWidgets get(GameManager gm) {
        if (instance == null) {
            return new BuildingWidgets(gm);
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
            this.upgradeBtn = new TextButton("Update", this.skin);
            this.destroyBtn = new TextButton("Destroy", this.skin);

            this.menu.setVisible(false);
            this.menu.align(Align.left|Align.top);
            this.menu.add(label).padBottom(3);
            this.menu.row();
            this.menu.add(healthBar).padBottom(3);
            this.menu.row();
            this.menu.add(upgradeBtn).padBottom(3);
            this.menu.row();
            this.menu.add(destroyBtn);
            this.stage.addActor(this.menu);

            this.cameraPos = new HexVector();
        } catch (Exception e) {
            // print errors, but no impact to game
            logger.debug("Null skin provided for style.");
        }
    }

    private ProgressBar createHealthBar() {
        // progress bar style setup
        Pixmap bg = new Pixmap(50, 20, Pixmap.Format.RGBA8888);
        bg.setColor(Color.RED);
        bg.fill();
        TextureRegionDrawable bgBar = new TextureRegionDrawable(new TextureRegion(new Texture(bg)));
        bg.dispose();
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = bgBar;

        Pixmap healthEnd = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        healthEnd.setColor(Color.GREEN);
        healthEnd.fill();
        TextureRegionDrawable endBar = new TextureRegionDrawable(new TextureRegion(new Texture(healthEnd)));
        healthEnd.dispose();
        progressBarStyle.knob = endBar;

        Pixmap healthFill = new Pixmap(50, 20, Pixmap.Format.RGBA8888);
        healthFill.setColor(Color.GREEN);
        healthFill.fill();
        TextureRegionDrawable fillBar = new TextureRegionDrawable(new TextureRegion(new Texture(healthFill)));
        healthFill.dispose();
        progressBarStyle.knobBefore = fillBar;

        // progress bar creation with above style
        ProgressBar healthBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);
        healthBar.setValue(1.0f);
        healthBar.setAnimateDuration(0.25f);
        healthBar.setBounds(0, 0, 50, 20);
        return healthBar;
    }

    /**
     * Get the widget layout.
     * @return an table object forms widget
     */
    public Table getMenu() {
        return menu;
    }

    /**
     * Updates the building object when update button is clicked.
     * @param building a building is selected on the world
     */
    private void upgradeBuilding(BuildingEntity building) {
        // TODO: check if building is upgradable then update the building
    }

    /**
     * Destroys the building object when destroy button is clicked.
     * @param building a building is selected on the world
     */
    private void destroyBuilding(BuildingEntity building) {
        world.removeEntity(building);
    }

    private void setMenu(BuildingEntity building) {
        float[] wCords = WorldUtil.colRowToWorldCords(building.getCol(), building.getRow());
        cameraPos.setCol(gm.getCamera().position.x);
        cameraPos.setRow(gm.getCamera().position.y);
        menu.setPosition(stage.getWidth()/2 + wCords[0] - cameraPos.getCol() - menu.getMinWidth(),
                stage.getHeight()/2 + wCords[1] - cameraPos.getRow() + menu.getMinHeight());
    }

    private void setHealthBar(BuildingEntity building) {
        float health = building.getCurrentHealth() / building.getInitialHealth();
        healthBar.setValue(health);
        if (health < 0f) {
            healthBar.setValue(0f);
        } else if (health > 1f) {
            healthBar.setValue(1f);
        }
    }

    private void setUpgradeBtn(BuildingEntity building) {
        if (upgradeListener != null) {
            upgradeBtn.removeListener(upgradeListener);
        }
        upgradeBtn.addListener(upgradeListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                upgradeBuilding(building);
                menu.setVisible(false);
            }
        });
    }

    private void setDestroyBtn(BuildingEntity building) {
        if (destroyListener != null) {
            destroyBtn.removeListener(destroyListener);
        }
        destroyBtn.addListener(destroyListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                destroyBuilding(building);
                menu.setVisible(false);
            }
        });
    }

    /**
     * Sets up a widget with specific information of a selected building and its position, then showing it.
     * @param building a building is selected on the world
     */
    private void setWidgets(BuildingEntity building) {
        label.setText(building.getObjectName());
        setMenu(building);
        setHealthBar(building);
        setUpgradeBtn(building);
        setDestroyBtn(building);
    }

    public void update() {
        // update widget position
        if (cameraPos.getCol() != gm.getCamera().position.x
                || cameraPos.getRow() != gm.getCamera().position.y) {
            menu.setPosition(menu.getX() - gm.getCamera().position.x + cameraPos.getCol(),
                    menu.getY() - gm.getCamera().position.y + cameraPos.getRow());
            cameraPos.setCol(gm.getCamera().position.x);
            cameraPos.setRow(gm.getCamera().position.y);
        }

        // update health bar value
        if (menu.isVisible()) {
            int value = building.getCurrentHealth() / building.getInitialHealth();
            if ((float)value != healthBar.getValue()) {
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
                    Collider collider = entity.getCollider();
                    if ((collider != null) && !(collider.getX() <= mousePos[0] && collider.getY() <= mousePos[1]
                            && collider.getX() + collider.getXLength() >= mousePos[0]
                            && collider.getY() + collider.getYLength() >= mousePos[1])) {
                        continue;
                    } else if ((collider == null) && !tile.getCoordinates().equals(entity.getPosition())) {
                        continue;
                    }

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
