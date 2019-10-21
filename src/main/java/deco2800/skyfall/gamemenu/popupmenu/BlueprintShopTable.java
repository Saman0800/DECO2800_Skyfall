package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.buildings.DesertPortal;
import deco2800.skyfall.buildings.ForestPortal;
import deco2800.skyfall.buildings.MountainPortal;
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
        super.blueprintShopTableDuplicatedCode() ;
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
            Label cost;
            if (isBought(b)) {
                cost = new Label("X", skin, "white-label");
                cost.setName(b.getName());
                cost.setPosition(xpos + 80 + count * 130, ypos + 75);
                cost.setFontScale((float)0.5);

                int costWidth = 30;
                if(b.getCost()>9){
                    costWidth += 10;
                }
                if(b.getCost()>99){
                    costWidth += 10;
                }

                cost.setSize(costWidth, 40);
            } else {
                cost = handleNotBought(count, xpos, ypos, b, icon);
            }
            blueprintPanel.addActor(icon);
            blueprintPanel.addActor(cost);

            count++;

            if ((count) % 6 == 0) {
                ypos -= 120;
                count = 0;
            }
        }
    }

    /**
     * For when a blue print is not bought
     * @param count The count of the blue print
     * @param xpos The x position of the blue print
     * @param ypos The y position of the blue print
     * @param b The blue print
     * @param icon The icon of the blue print
     * @return Returns the label
     */
    private Label handleNotBought(float count, float xpos, float ypos, Blueprint b, ImageButton icon) {
        Label cost;
        cost = new Label("$" + b.getCost(), skin, "white-label");
        cost.setPosition(xpos + 80 + count * 130, ypos + 75);
        cost.setName(b.getName());
        cost.setFontScale((float)0.5);

        int costWidth = 35;
        if(b.getCost()>9){
            costWidth += 10;
        }
        if(b.getCost()>99){
            costWidth += 10;
        }

        cost.setSize(costWidth, 40);

        icon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (sm.getCharacter().getGoldPouchTotalValue() >= b.getCost()) {
                    sm.getCharacter().removeGold(b.getCost());
                    sm.getCharacter().addBlueprint(b);
                    if (b instanceof ForestPortal || b instanceof DesertPortal || b instanceof MountainPortal) {
                        ((TeleportTable) gameMenuManager.getPopUp("teleportTable")).setPurchased(b);
                    }
                }
                updateBlueprintShopPanel();
            }
        });
        return cost;
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
