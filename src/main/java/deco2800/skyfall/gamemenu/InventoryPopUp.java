package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class InventoryPopUp extends AbstractPopUpElement {
    private InventoryManager inventory;
    private Table resourcePanel;
    private PopUpTable inventoryTable;
    private Skin skin;
    private GameMenuManager gameMenuManager;
    private Stage stage;



    public InventoryPopUp(Stage stage, String[] textureNames, TextureManager tm, Skin skin, GameMenuManager gameMenuManager) {
        super(stage, textureNames, tm);

        this.skin = skin;
        this.gameMenuManager = gameMenuManager;
        this.stage = stage;
    }

    @Override
    public void updatePosition() {

    }


    @Override
    public void draw() {
        PopUpTable inventoryTable = new PopUpTable(910, 510, "i");
        inventoryTable.setName("inventoryTable");

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));
        infoBar.setSize(650, 55);
        infoBar.setPosition(130, 430);

        Label text = new Label("INVENTORY", skin, "black-text");
        infoBar.add(text);

        Table infoPanel = new Table();
        infoPanel.setSize(410, 400);
        infoPanel.setPosition(25, 18);
        infoPanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));


        update();

        inventoryTable.addActor(infoBar);
        inventoryTable.addActor(infoPanel);
        inventoryTable.addActor(this.resourcePanel);

        this.inventoryTable = inventoryTable;

        setExitButton(this.inventoryTable);

        this.stage.addActor(inventoryTable);
        stage.addActor(inventoryTable.getExit());
    }



    @Override
    public void update() {
        if(resourcePanel != null){
            inventoryTable.removeActor(resourcePanel);
        }

        resourcePanel = new Table();
        resourcePanel.setName("resourcePanel");
        resourcePanel.setSize(410, 400);
        resourcePanel.setPosition(475, 18);
        resourcePanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        Map<String, Integer> inventoryAmounts = gameMenuManager.getInventory().getInventoryAmounts();

        int count = 0;
        int xpos = 20;
        int ypos = 280;

        for (Map.Entry<String, Integer> entry : inventoryAmounts.entrySet()) {

            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(entry.getKey()));
            icon.setName("icon");
            icon.setSize(100, 100);
            icon.setPosition(xpos + count * 130, ypos);

            resourcePanel.addActor(icon);

            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setPosition(xpos + 85 + count * 130, ypos + 75);
            num.setFontScale((float)0.7);
            num.setSize(20, 40);
            resourcePanel.addActor(num);

            count++;

            if ((count) % 3 == 0) {
                ypos -= 120;
                count = 0;
            }
        }

        inventoryTable.addActor(resourcePanel);
    }

    /**
     * Initialise the Exit button for given PopUpTable
     *
     * @param table PopUpTable of the Exit Button
     */
    //MIGHT NEED TO ADD TO ABSTRACT POPUP ELEMENT AS WILL BE IN ALL
    private void setExitButton(PopUpTable table) {
        ImageButton exit = table.getExit();
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.resume(table);
            }
        });
    }

}
