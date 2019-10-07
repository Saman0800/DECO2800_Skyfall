package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.resources.Blueprint;
import java.util.List;


/**
 * A class for blueprint shop table pop up.
 */
public class BlueprintShopTable extends AbstractPopUpElement {
    private final Skin skin;
    private final StatisticsManager sm;
    private Table blueprintTable;
    private Table blueprintPanel;

    /**
     * Constructs a blueprint shop table.
     *
     * @param stage           Current stage.
     * @param exit            Exit button if it has one.
     * @param textureNames    Names of the textures.
     * @param tm              Current texture manager.
     * @param gameMenuManager Current game menu manager.
     * @param skin            Current skin.
     */
    public BlueprintShopTable(Stage stage, ImageButton exit, String[] textureNames, TextureManager tm,
            GameMenuManager gameMenuManager, StatisticsManager sm, Skin skin) {
        super(stage, exit, textureNames, tm, gameMenuManager);
        this.skin = skin;
        this.draw();
        this.sm = sm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        blueprintTable.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        blueprintTable.setVisible(true);
    }

    /**
     * {@inheritDoc}
     *
     * Draw the whole blueprint shop table.
     */
    public void draw() {
        super.draw();
        blueprintTable = new Table();
        blueprintTable.setSize(910, 510);
        blueprintTable.setPosition(Gdx.graphics.getWidth() / 2f - blueprintTable.getWidth() / 2,
                (Gdx.graphics.getHeight() + 160) / 2f - blueprintTable.getHeight() / 2);
        blueprintTable.setDebug(true);
        blueprintTable.top();
        blueprintTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("pop up screen"));
        blueprintTable.setName("chestTable");

        Image infoBar = new Image(generateTextureRegionDrawableObject("blueprint_shop_banner"));
        infoBar.setSize(650, 55);
        infoBar.setPosition(130, 435);

//        Table infoPanel = new Table();
//        infoPanel.setSize(410, 400);
//        infoPanel.setPosition(25, 18);
//        infoPanel.setBackground(generateTextureRegionDrawableObject("info_panel"));

        this.blueprintPanel = new Table();
        // updateChestPanel(chest);

        blueprintTable.addActor(infoBar);
        //blueprintTable.addActor(infoPanel);
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
        blueprintPanel.setSize(800, 400);
        blueprintPanel.setPosition(25, 18);
        blueprintPanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        List<Blueprint> unlocked = sm.getCharacter().getUnlockedBlueprints();

        int count = 0;
        int xpos = 20;
        int ypos = 280;

        for (Blueprint b : unlocked) {

            ImageButton icon = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject(b.getName()));
            icon.setName("icon");
            icon.setSize(100, 100);
            icon.setPosition(xpos + count * 130, ypos);
            if (isBought(b)) {
                Label cost = new Label("X", skin, "white-label");
                cost.setName(b.getName());
                cost.setPosition(xpos + 85 + count * 130, ypos + 75);
                blueprintPanel.addActor(cost);
            } else {
                Label cost = new Label("$" + b.getCost(), skin, "white-label");
                cost.setPosition(xpos + 85 + count * 130, ypos + 75);
                cost.setName(b.getName());
                blueprintPanel.addActor(cost);

                icon.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (sm.getCharacter().getGoldPouchTotalValue() >= b.getCost()) {
                            sm.getCharacter().removeGold(b.getCost());
                            sm.getCharacter().addBlueprint(b);
                        } else {
                        }
                        updateBlueprintShopPanel();
                    }
                });
            }
            blueprintPanel.addActor(icon);

            count++;

            if ((count) % 6 == 0) {
                ypos -= 120;
                count = 0;
            }
        }
    }

    /**
     * Check if a blueprint has been bought already
     *
     * @param b blueprint to check
     * @return true if bought, else false
     */
    private boolean isBought(Blueprint b) {
        for (Blueprint bt : sm.getCharacter().getBlueprintsLearned()) {
            if (b.getName().equals(bt.getName())) {
                return true;
            }
        }
        return false;
    }
}
