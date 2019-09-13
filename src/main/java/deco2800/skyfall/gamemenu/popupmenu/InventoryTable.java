package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.gamemenu.GameMenuBar;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class InventoryTable extends AbstractPopUpElement {
    private Skin skin;
    private Table inventoryTable;
    private Table resourcePanel;
    private GameMenuManager gameMenuManager;
    private InventoryManager inventory;
    private String inventorySelected;
    private MainCharacter mainCharacter;
    private GameMenuBar gameMenuBar;

    public InventoryTable(Stage stage, ImageButton exitButton, String[] textureNames, TextureManager tm, Skin skin, GameMenuManager gameMenuManager) {
        super(stage, exitButton, textureNames, tm, gameMenuManager);
        this.skin = skin;
        this.gameMenuManager = gameMenuManager;
        this.inventory = gameMenuManager.getInventory();
        this.mainCharacter = gameMenuManager.getMainCharacter();

        if(gameMenuManager.getUIElement("gameMenuBar") instanceof GameMenuBar){
            this.gameMenuBar = (GameMenuBar) gameMenuManager.getUIElement("gameMenuBar");
        }
        this.draw();
    }

    @Override
    public void hide() {
        super.hide();
        System.out.println("Hiding inventory table");
        inventoryTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
        updateResourcePanel();
        System.out.println("Showing inventory table");
        inventoryTable.setVisible(true);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    @Override
    public void draw() {
        super.draw();

        inventoryTable = new Table();
        inventoryTable.setSize(910, 510);
        inventoryTable.setPosition(Gdx.graphics.getWidth()/2f - inventoryTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - inventoryTable.getHeight()/2);
        inventoryTable.setDebug(true);
        inventoryTable.top();
        inventoryTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));
        inventoryTable.setName("inventoryTable");

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));
        infoBar.setSize(650, 55);
        infoBar.setPosition(130, 430);

        Label text = new Label("INVENTORY", skin, "black-text");
        infoBar.add(text);

        Table infoPanel = new Table();
        infoPanel.setSize(410, 320);
        infoPanel.setPosition(25, 98);
        infoPanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        updateResourcePanel();

        ImageButton drop = new ImageButton(generateTextureRegionDrawableObject("drop"));
        drop.setSize(100, 60);
        drop.setPosition(285, 20);
        drop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inventory.dropAll(inventorySelected);
                updateResourcePanel();
                gameMenuBar.removeQuickAccessPanel();
                gameMenuBar.setQuickAccessPanel();
                inventorySelected = null;
            }
        });

        ImageButton equip = new ImageButton(generateTextureRegionDrawableObject("equip"));
        equip.setSize(100, 60);
        equip.setPosition(405, 20);
        equip.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainCharacter.setEquippedItem(inventory.drop(inventorySelected));
                System.out.println(mainCharacter.getEquippedItem());
                updateResourcePanel();
                gameMenuBar.removeQuickAccessPanel();
                gameMenuBar.setQuickAccessPanel();
                inventorySelected = null;
            }
        });

        ImageButton addqa = new ImageButton(generateTextureRegionDrawableObject("addqa"));
        addqa.setSize(100, 60);
        addqa.setPosition(530, 20);
        addqa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inventory.quickAccessAdd(inventorySelected);
                inventorySelected = null;
                gameMenuBar.removeQuickAccessPanel();
                gameMenuBar.setQuickAccessPanel();

            }
        });

        inventoryTable.addActor(infoBar);
        inventoryTable.addActor(infoPanel);
        inventoryTable.addActor(drop);
        inventoryTable.addActor(equip);
        inventoryTable.addActor(addqa);

        inventoryTable.setVisible(false);
        stage.addActor(inventoryTable);
    }

    /***
     * Updates the resources panel to display the current inventory contents.
     */
    protected void updateResourcePanel(){
        if(resourcePanel != null){
            inventoryTable.removeActor(resourcePanel);
        }

        resourcePanel = new Table();
        resourcePanel.setName("resourcePanel");
        resourcePanel.setSize(410, 320);
        resourcePanel.setPosition(475, 98);
        resourcePanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        Map<String, Integer> inventoryAmounts = gameMenuManager.getInventory().getAmounts();

        int count = 0;
        int xpos = 115;
        int ypos = 215;
        int size = 80;
        int xspace = 20;

        for (Map.Entry<String, Integer> entry : inventoryAmounts.entrySet()) {
            Image selected = new Image(generateTextureRegionDrawableObject("selected"));
            selected.setName(entry.getKey() + "-selected");
            selected.setSize((float) size + 20, (float) size + 20);
            selected.setPosition((float)(xpos + -10 + (size+xspace)*(count-1)), ypos -10);
            selected.setVisible(false);

            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(entry.getKey()));
            icon.setName(entry.getKey());
            icon.setSize((float)size, (float)size);
            icon.setPosition((float)(xpos + (size+xspace)*(count-1)), ypos);

            icon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(inventorySelected != icon.getName()){
                        inventorySelected = icon.getName();
                    }else{
                        inventorySelected = null;
                    }

                    Actor selected = stage.getRoot().findActor(icon.getName() + "-selected");

                    if(selected.isVisible()){
                        selected.setVisible(false);
                    }else{
                        for(Actor actor: resourcePanel.getChildren()){
                            String name = actor.getName();
                            if(name != null && name.contains("-selected")){
                                actor.setVisible(false);
                            }
                        }

                        selected.setVisible(true);
                    }

                }
            });

            resourcePanel.addActor(selected);
            resourcePanel.addActor(icon);

            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setFontScale((float)0.4);
            int numWidth = 18;
            if(entry.getValue()>9){
                numWidth += 8;
            }
            num.setSize(numWidth, 25);
            num.setPosition(xspace*count + size*count + xpos - 35, ypos + 65);
            resourcePanel.addActor(num);

            count++;

            if ((count) % 4 == 0) {
                ypos -= 98;
                count = 0;
            }
        }

        inventoryTable.addActor(this.resourcePanel);

    }


}
