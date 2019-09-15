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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class InventoryTable extends AbstractPopUpElement {
    //The inventory table
    private Table inventoryTable;

    //Resource panel on right side of inventory table
    private Table resourcePanel;

    //Item information panel on left side of inventory table
    private Table infoPanel;

    //Game skin
    private Skin skin;

    //GameMenuManager being used by GameManager
    private GameMenuManager gameMenuManager;

    //InventoryManager being used by GameManager
    private InventoryManager inventory;

    //Current item selected in inventory user interface
    private String inventorySelected;

    //Main Character being used by GameManager
    private MainCharacter mainCharacter;

    //Current GameMenuBar in game, from GameMenuManager
    private GameMenuBar gameMenuBar;

    //Inventory user interface drop item button (active)
    private ImageButton dropButton;

    //Inventory user interface add to quick access button (active)
    private ImageButton addqaButton;

    //Inventory user interface equip item button (active)
    private ImageButton equipButton;

    //Inventory user interface drop item button (inactive)
    private ImageButton inactiveDropButton;

    //Inventory user interface add to quick access button (inactive)
    private ImageButton inactiveAddqaButton;

    //Inventory user interface equip item button (inactive)
    private ImageButton inactiveEquipButton;

    //Item information currently displayed in the info panel
    private Label itemInfo;

    //Logger for Inventory Table
    private final transient Logger LOGGER =
            LoggerFactory.getLogger(InventoryTable.class);


    /**
     * Constructs an Inventory Table pop up.
     * @param stage Game stage
     * @param exitButton ImageButton used to hide the pop up
     * @param textureNames Array of texture names (strings)
     * @param tm Texture manager used in the game
     * @param skin Game skin
     * @param gameMenuManager GameMenuManager used in the game
     */
    public InventoryTable(Stage stage, ImageButton exitButton, String[]
            textureNames, TextureManager tm, Skin skin, GameMenuManager gameMenuManager) {
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

    /**
     * Hides the inventory pop up
     */
    @Override
    public void hide() {
        super.hide();
        inventorySelected = null;
        LOGGER.info("Hiding inventory table");
        inventoryTable.setVisible(false);
    }

    /**
     * Shows the inventory pop up
     */
    @Override
    public void show() {
        super.show();
        updatePanels();
        LOGGER.info("Showing inventory table");
        inventoryTable.setVisible(true);
    }

    /**
     * Updates the pop up position
     */
    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    /**
     * Draws the pop up
     */
    @Override
    public void draw() {
        super.draw();

        inventoryTable = setBaseInventoryTable();
        Table infoBar = setHeading();
        infoPanel = setInfoPanel();
        updatePanels();
        setButtons();

        inventoryTable.addActor(infoBar);
        inventoryTable.addActor(infoPanel);
        inventoryTable.addActor(dropButton);
        inventoryTable.addActor(inactiveDropButton);
        inventoryTable.addActor(equipButton);
        inventoryTable.addActor(inactiveEquipButton);
        inventoryTable.addActor(addqaButton);
        inventoryTable.addActor(inactiveAddqaButton);

        inventoryTable.setVisible(false);
        stage.addActor(inventoryTable);
    }

    /***
     * Updates the resources and info panels to display the current inventory contents and information.
     */
    protected void updatePanels(){
        if(resourcePanel != null){
            inventoryTable.removeActor(resourcePanel);
        }

        resourcePanel = setResourcePanel();

        Map<String, Integer> inventoryAmounts = gameMenuManager.getInventory().getAmounts();

        updateItemInfo();

        setCounts(inventoryAmounts, 115, 215, 80, 20);

        inventoryTable.addActor(this.resourcePanel);

    }

    /**
     * Sets the buttons in the inventory pop up to active or inactive forms
     * @param active boolean whether buttons are active
     */
    private void setButtonsActive(boolean active){
        if(active){
            inactiveAddqaButton.setVisible(false);
            inactiveDropButton.setVisible(false);
            inactiveEquipButton.setVisible(false);
            addqaButton.setVisible(true);
            equipButton.setVisible(true);
            dropButton.setVisible(true);
        }else{
            inactiveAddqaButton.setVisible(true);
            inactiveDropButton.setVisible(true);
            inactiveEquipButton.setVisible(true);
            addqaButton.setVisible(false);
            equipButton.setVisible(false);
            dropButton.setVisible(false);
        }
    }

    /**
     * Sets the base of the inventory table
     * @return inventoryTable
     */
    private Table setBaseInventoryTable(){
        inventoryTable = new Table();
        inventoryTable.setSize(910, 510);
        inventoryTable.setPosition(Gdx.graphics.getWidth()/2f - inventoryTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - inventoryTable.getHeight()/2);
        inventoryTable.setDebug(false);
        inventoryTable.top();
        inventoryTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));
        inventoryTable.setName("inventoryTable");

        return inventoryTable;
    }

    /**
     * Sets the heading of the inventory table
     * @return infoBar containing Inventory heading
     */
    private Table setHeading(){
        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));
        infoBar.setSize(650, 55);
        infoBar.setPosition(130, 430);

        Label text = new Label("INVENTORY", skin, "black-text");
        infoBar.add(text);

        return infoBar;
    }

    /**
     * Sets the information panel on left hand side of inventory pop up
     * @return infoPanel
     */
    private Table setInfoPanel(){
        Table infoPanel = new Table();
        infoPanel.setSize(410, 320);
        infoPanel.setPosition(25, 98);
        infoPanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        itemInfo = new Label("Click on an item to use it, and " + "\n" + "find out more information.", skin, "default");
        itemInfo.setSize(350, 200);
        itemInfo.setPosition(90, 70);

        infoPanel.addActor(itemInfo);

        return infoPanel;
    }

    /**
     * Sets the buttons in the inventory pop up, with corresponding click events.
     */
    private void setButtons(){
        this.inactiveDropButton = new ImageButton(generateTextureRegionDrawableObject("drop inactive"));
        this.inactiveDropButton.setSize(100, 60);
        this.inactiveDropButton.setPosition(285, 20);

        this.dropButton = new ImageButton(generateTextureRegionDrawableObject("drop"));
        this.dropButton.setSize(100, 60);
        this.dropButton.setPosition(285, 20);
        this.dropButton.setVisible(false);
        this.dropButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(inventorySelected != null){
                    inventory.dropAll(inventorySelected);
                    inventorySelected = null;
                    setButtonsActive(false);
                    updatePanels();
                    gameMenuBar.removeQuickAccessPanel();
                    gameMenuBar.setQuickAccessPanel();
                }
            }
        });

        this.inactiveEquipButton = new ImageButton(generateTextureRegionDrawableObject("equip inactive"));
        this.inactiveEquipButton.setSize(100, 60);
        this.inactiveEquipButton.setPosition(405, 20);

        this.equipButton = new ImageButton(generateTextureRegionDrawableObject("equip"));
        this.equipButton.setSize(100, 60);
        this.equipButton.setPosition(405, 20);
        this.equipButton.setVisible(false);
        this.equipButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(inventorySelected != null) {
                    mainCharacter.setEquippedItem(inventory.drop(inventorySelected));
                    inventorySelected = null;
                    setButtonsActive(false);
                    updatePanels();
                    gameMenuBar.removeQuickAccessPanel();
                    gameMenuBar.setQuickAccessPanel();
                }
            }
        });

        this.inactiveAddqaButton = new ImageButton(generateTextureRegionDrawableObject("addqa inactive"));
        this.inactiveAddqaButton.setSize(100, 60);
        this.inactiveAddqaButton.setPosition(530, 20);

        this.addqaButton = new ImageButton(generateTextureRegionDrawableObject("addqa"));
        this.addqaButton.setSize(100, 60);
        this.addqaButton.setPosition(530, 20);
        this.addqaButton.setVisible(false);
        this.addqaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(inventorySelected != null) {
                    inventory.quickAccessAdd(inventorySelected);
                    inventorySelected = null;
                    updatePanels();
                    gameMenuBar.removeQuickAccessPanel();
                    gameMenuBar.setQuickAccessPanel();
                }
            }
        });
    }

    /**
     * Sets the resource panel on the right hand side of inventory pop up
     * @return resourcePanel
     */
    private Table setResourcePanel(){
        resourcePanel = new Table();
        resourcePanel.setName("resourcePanel");
        resourcePanel.setSize(410, 320);
        resourcePanel.setPosition(475, 98);
        resourcePanel.setBackground(generateTextureRegionDrawableObject("menu_panel"));

        return resourcePanel;
    }

    /**
     * Updates the item information displayed in the info panel, depending on
     * what item is selected.
     */
    private void updateItemInfo(){
        //System.out.println(inventorySelected);
        if (inventorySelected != null){
            String info = inventory.getItemDescription(inventorySelected);
            itemInfo.setText(info);
        } else{
            itemInfo.setText("Click on an item to use it, and " + "\n" + "find out more information.");
        }
        infoPanel.removeActor(itemInfo);
        infoPanel.add(itemInfo);
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
        int count = 0;

        for (Map.Entry<String, Integer> entry : inventoryAmounts.entrySet()) {
            Image selected = new Image(generateTextureRegionDrawableObject("selected"));
            selected.setName(entry.getKey() + "-selected");
            selected.setSize((float) size + 20, (float) size + 20);
            selected.setPosition((float)(xpos + -10 + (size+xspace)*(count-1)), ypos -10);
            selected.setVisible(false);
            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(entry.getKey() + "_inv"));
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
                        setButtonsActive(false);

                    }else{
                        for(Actor actor: resourcePanel.getChildren()){
                            String name = actor.getName();
                            if(name != null && name.contains("-selected")){
                                actor.setVisible(false);
                            }
                        }

                        selected.setVisible(true);

                        setButtonsActive(true);

                    }

                    updateItemInfo();

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
            if(entry.getValue()>99){
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
    }

}