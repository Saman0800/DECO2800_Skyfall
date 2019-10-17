package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.entities.Chest;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.worlds.world.World;

import java.util.Map;


/**
 * A class for chest table pop up.
 */
public class ChestTable extends AbstractPopUpElement{
    private final Skin skin;
    private final StatisticsManager sm;
    private Table baseTable;
    private Table resourcePanel;
    private World world;
    private Chest chestEntity;

    /**
     * Constructs a chest table.
     *
     * @param stage Current stage.
     * @param exit Exit button if it has one.
     * @param textureNames Names of the textures.
     * @param tm Current texture manager.
     * @param gameMenuManager Current game menu manager.
     * @param skin Current skin.
     */
    public ChestTable(Stage stage, ImageButton exit, String[] textureNames,
                         TextureManager tm, GameMenuManager gameMenuManager,
                      StatisticsManager sm, Skin skin) {
        super(stage,exit, textureNames, tm, gameMenuManager);
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
        baseTable.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        baseTable.setVisible(true);
    }
    

    /**
     * {@inheritDoc}
     *
     * Draw the whole chest table.
     */
    @Override
    public void draw() {
        super.draw();
        baseTable = new Table();
        baseTable.setSize(500, 510);
        baseTable.setPosition((Gdx.graphics.getWidth()/2f - baseTable.getWidth()/2 + 60),
                (Gdx.graphics.getHeight() + 160) / 2f - baseTable.getHeight()/2);
        baseTable.top();
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_bg"));
        baseTable.setName("baseTable");

        Table infoBar = new Table();
        infoBar.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_banner"));
        infoBar.setSize(410, 55);
        infoBar.setPosition(45, 430);
        Label text = new Label("CHEST", skin, "navy-text");
        infoBar.add(text);

        this.resourcePanel = new Table();

        baseTable.addActor(infoBar);
        baseTable.addActor(this.resourcePanel);
        baseTable.setVisible(false);
        stage.addActor(baseTable);
    }

    /**
     * Display the items in the chest and allow the player to 'takeall' said items
     * @param chest chest to display
     */
    public void updateChestPanel(Chest chest) {
        resourcePanel.clear();
        resourcePanel.setName("resourcePanel");
        resourcePanel.setSize(410, 320);
        resourcePanel.setPosition(45, 98);
        resourcePanel.setBackground(gameMenuManager.generateTextureRegionDrawableObject("menu_panel"));

        Map<String, Integer> inventoryAmounts = chest.getManager().getAmounts();

        setCounts(inventoryAmounts, 115, 215, 80, 20);

        ImageButton button = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("take all"));
        button.setName("Take all");
        button.setSize(170, 60);
        button.setPosition(165, 20);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sm.getInventory().inventoryAddMultiple(chest.getManager().getContents());
                hide();
                world.removeEntity(chestEntity);
            }
        });

        baseTable.addActor(button);
    }

    public void setWorldAndChestEntity(World world, Chest chest) {
        this.world = world;
        this.chestEntity = chest;
    }
    /**
     * Sets the item icons and counts in the resource panel.
     * @param inventoryAmounts Map<String, Integer> of inventory contents
     * @param xpos x position for item icon
     * @param ypos y position for item icon
     * @param size size of item icon
     * @param xspace space between icons
     */
    private void setCounts(Map<String, Integer> inventoryAmounts, int xpos, int ypos, int size, int xspace){
        float count = 0;

        String[] weapons = {"axe", "bow", "spear", "sword"};

        for (Map.Entry<String, Integer> entry : inventoryAmounts.entrySet()) {
            String weaponName = entry.getKey();
            for (String weapon : weapons) {
                if (weapon.equals(entry.getKey())) {
                    weaponName = entry.getKey() + "_display";
                }
            }
            ImageButton icon =
                    new ImageButton(gameMenuManager.generateTextureRegionDrawableObject(weaponName + "_inv"));
            icon.setName(entry.getKey());
            icon.setSize((float)size, (float)size);
            icon.setPosition((float)(xpos + (size+xspace)*(count-1)), ypos);
            resourcePanel.addActor(icon);

            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setFontScale((float)0.4);
            int numWidth = 18;
            if(entry.getValue()>9){
                numWidth += 8;
            }
            if(entry.getValue()>99){
                numWidth += 8;
            }
            num.setSize(numWidth, 25);
            num.setPosition((float) xspace*count + size*count + xpos - 35, (float) ypos + 65);
            resourcePanel.addActor(num);

            count++;

            if ((count) % 4 == 0) {
                ypos -= 98;
                count = 0;
            }
        }
    }
}
