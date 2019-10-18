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
        this.sm = sm;
        this.draw();

    }

    /**
     * {@inheritDoc}
     * <p>
     * Draw the whole blueprint shop table.
     */
    @Override
    public void draw() {
        super.draw();
        baseTable = new Table();
        baseTable.setSize(910, 510);
        baseTable.setPosition(Gdx.graphics.getWidth() / 2f - baseTable.getWidth() / 2,
                (Gdx.graphics.getHeight() + 160) / 2f - baseTable.getHeight() / 2);
        baseTable.setDebug(true);
        baseTable.top();
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_bg"));
        baseTable.setName("bluePrintTable");


        Table infoBar = new Table();
        infoBar.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_banner"));
        infoBar.setSize(650, 55);
        infoBar.setPosition(130, 435);
        Label text = new Label("Blueprint Shop", skin, "navy-text");
        infoBar.add(text);
        this.blueprintPanel = new Table();

        baseTable.addActor(infoBar);
        baseTable.addActor(this.blueprintPanel);
        baseTable.setVisible(false);
        stage.addActor(baseTable);
    }

    /**
     * Actually display the blueprints and their prices (if not bought)
     */
    public void updateBlueprintShopPanel() {
        blueprintPanel.clear();
        blueprintPanel.setName("resourcePanel");
        blueprintPanel.setSize(800, 400);
        blueprintPanel.setPosition(60, 18);
        blueprintPanel.setBackground(gameMenuManager.generateTextureRegionDrawableObject("menu_panel"));

        List<Blueprint> unlocked = sm.getCharacter().getUnlockedBlueprints();

        float count = 0;
        float xpos = 20;
        float ypos = 280;

        for (Blueprint b : unlocked) {

            ImageButton icon = new ImageButton(
                    gameMenuManager.generateTextureRegionDrawableObject(b.getName() + "_inv"));
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
