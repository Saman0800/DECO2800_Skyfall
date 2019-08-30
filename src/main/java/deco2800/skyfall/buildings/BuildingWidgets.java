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
import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.Tile;

public class BuildingWidgets implements TouchDownObserver {
    private static BuildingWidgets instance = null;
    private Stage stage;
    private Skin skin;
    private AbstractWorld world;
    private Table menu;
    private Label label;
    private TextButton updateBtn;
    private TextButton destroyBtn;

    // return an instance of building widgets
    public static BuildingWidgets get() {
        if (instance == null) {
            return new BuildingWidgets();
        }
        return instance;
    }

    // private constructor to enforce use of get()
    private BuildingWidgets() {
        this.stage = GameManager.get().getStage();
        this.skin = GameManager.get().getSkin();
        this.world = GameManager.get().getWorld();

        this.menu = new Table();
        this.label = new Label("Name", this.skin);
        this.updateBtn = new TextButton("Update", this.skin);
        this.destroyBtn = new TextButton("Destroy", this.skin);

        this.menu.setVisible(false);
        this.menu.align(Align.left|Align.top);

        this.menu.add(label).padBottom(3);
        this.menu.row();
        this.menu.add(updateBtn).padBottom(3);
        this.menu.row();
        this.menu.add(destroyBtn);

        this.stage.addActor(this.menu);
        GameManager.get().getManager(InputManager.class).addTouchDownListener(this);
    }

    private void updateBuilding(BuildingEntity building) {
        // TODO: check if building is updatable then update the building
    }

    private void destroyBuilding(BuildingEntity building) {
        this.world.removeEntity(building);
    }

    private void setWidgets(BuildingEntity building) {
        float[] wCords = WorldUtil.colRowToWorldCords(building.getCol(), building.getRow());
        this.label.setText(building.getTexture());
        this.menu.setPosition(wCords[0] + (float)Gdx.graphics.getWidth()/2,
                wCords[1] + (float)Gdx.graphics.getHeight()/2);
        this.updateBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateBuilding(building);
                menu.setVisible(false);
            }
        });
        this.destroyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                destroyBuilding(building);
                menu.setVisible(false);
            }
        });
    }

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
            // hide the building widget if entity is not a building
            this.menu.setVisible(false);
        }
    }
}
