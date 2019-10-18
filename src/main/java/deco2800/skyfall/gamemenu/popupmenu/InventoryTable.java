package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.gamemenu.GameMenuBar2;
import deco2800.skyfall.gamemenu.HeadsUpDisplay;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.resources.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class InventoryTable extends AbstractPopUpElement {
    //Resource panel on right side of inventory table
    private Table resourcePanel;

    //Item information panel on left side of inventory table
    private Table infoPanel;

    //Game skin
    private Skin skin;

    //GameMenuManager being used by GameManager
    private GameMenuManager menuManager;

    //InventoryManager being used by GameManager
    private InventoryManager inventory;

    //Current item selected in inventory user interface
    private String inventorySelected = "";

    //Main Character being used by GameManager
    private MainCharacter mainCharacter;

    //Current GameMenuBar in game, from GameMenuManager
    private GameMenuBar2 gameMenuBar;

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

    //Logger for Inventory Table

    private final Logger logger =
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
        this.menuManager = gameMenuManager;
        this.inventory = gameMenuManager.getInventory();
        this.mainCharacter = gameMenuManager.getMainCharacter();

        if(menuManager.getUIElement("HUD") instanceof HeadsUpDisplay){
            HeadsUpDisplay hud = (HeadsUpDisplay) menuManager.getUIElement("HUD");
            if(hud.gethudElement("gameMenuBar2") instanceof GameMenuBar2){
                this.gameMenuBar = (GameMenuBar2) hud.gethudElement("gameMenuBar2");
            }
        }
        this.draw();
    }

    @Override
    public void update() {
        super.update();
        baseTable.toFront();
        infoPanel.toFront();
    }

    /**
     * Hides the inventory pop up
     */
    @Override
    public void hide() {
        super.hide();
        inventorySelected = "";
        logger.info("Hiding inventory table");
        baseTable.setVisible(false);
    }

    /**
     * Shows the inventory pop up
     */
    @Override
    public void show() {
        super.show();
        updatePanels();
        logger.info("Showing inventory table");
        baseTable.setVisible(true);

    }


    /**
     * Draws the pop up
     */
    @Override
    public void draw() {
        super.draw();

        baseTable = setBaseInventoryTable();
        Table infoBar = setHeading();
        infoPanel = setInfoPanel();
        updatePanels();
        setButtons();

        baseTable.addActor(infoBar);
        baseTable.addActor(infoPanel);
        baseTable.addActor(dropButton);
        baseTable.addActor(inactiveDropButton);
        baseTable.addActor(equipButton);
        baseTable.addActor(inactiveEquipButton);
        baseTable.addActor(addqaButton);
        baseTable.addActor(inactiveAddqaButton);

        baseTable.setVisible(false);
        stage.addActor(baseTable);
    }

    /***
     * Updates the resources and info panels to display the current inventory contents and information.
     */
    protected void updatePanels(){
        if(resourcePanel != null){
            baseTable.removeActor(resourcePanel);
        }

        resourcePanel = setResourcePanel();

        Map<String, Integer> inventoryAmounts = menuManager.getInventory().getAmounts();

        updateItemInfo();

        setCounts(inventoryAmounts, 115, 215, 80, 20);

        baseTable.addActor(this.resourcePanel);

    }

    /**
     * Sets the buttons in the inventory pop up to active or inactive forms
     * @param active boolean whether buttons are active
     */
    private void setButtonsActive(boolean active){
        if(active){
            inactiveAddqaButton.setVisible(false);
            inactiveDropButton.setVisible(false);
            addqaButton.setVisible(true);
            dropButton.setVisible(true);


            if(inventory.getItemInstance(inventorySelected).isEquippable()){
                equipButton.setVisible(true);
                inactiveEquipButton.setVisible(false);
            } else {
                equipButton.setVisible(false);
                inactiveEquipButton.setVisible(false);
            }
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
     * @return baseTable
     */
    private Table setBaseInventoryTable(){
        baseTable = new Table();
        baseTable.setSize(910, 510);
        baseTable.setPosition((Gdx.graphics.getWidth()/2f - baseTable.getWidth()/2 + 60),
                (Gdx.graphics.getHeight() + 160) / 2f - baseTable.getHeight()/2);
        baseTable.setDebug(false);
        baseTable.top();
        baseTable.setBackground(menuManager.generateTextureRegionDrawableObject("popup_bg"));
        baseTable.setName("baseTable");

        return baseTable;
    }

    /**
     * Sets the heading of the inventory table
     * @return infoBar containing Inventory heading
     */
    private Table setHeading(){
        Table infoBar = new Table();
        infoBar.setBackground(menuManager.generateTextureRegionDrawableObject("popup_banner"));
        infoBar.setSize(650, 55);
        infoBar.setPosition(130, 430);

        Label text = new Label("INVENTORY", skin, "navy-text");
        infoBar.add(text);

        return infoBar;
    }

    /**
     * Sets the information panel on left hand side of inventory pop up
     * @return infoPanel
     */
    private Table setInfoPanel(){
        Table table = new Table();
        table.setSize(410, 320);
        table.setPosition(25, 98);
        table.setBackground(menuManager.generateTextureRegionDrawableObject("Description_Panel"));

        return table;
    }

    /**
     * Sets the buttons in the inventory pop up, with corresponding click events.
     */
    private void setButtons(){
        this.inactiveDropButton = new ImageButton(menuManager.generateTextureRegionDrawableObject("drop inactive"));
        this.inactiveDropButton.setSize(170, 60);
        this.inactiveDropButton.setPosition(225, 20);

        this.dropButton = new ImageButton(menuManager.generateTextureRegionDrawableObject("drop"));
        this.dropButton.setSize(170, 60);
        this.dropButton.setPosition(225, 20);
        this.dropButton.setVisible(false);
        this.dropButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(inventorySelected != null){
                    inventory.dropAll(inventorySelected);
                    inventorySelected = "";
                    setButtonsActive(false);
                    updatePanels();
                    gameMenuBar.removeQuickAccessPanel();
                    gameMenuBar.setQuickAccessPanel();
                }
            }
        });

        this.inactiveEquipButton = new ImageButton(menuManager.generateTextureRegionDrawableObject("equip inactive inv"));
        this.inactiveEquipButton.setSize(170, 60);
        this.inactiveEquipButton.setPosition(390, 20);

        this.equipButton = new ImageButton(menuManager.generateTextureRegionDrawableObject("equip inv"));
        this.equipButton.setSize(170, 60);
        this.equipButton.setPosition(390, 20);
        this.equipButton.setVisible(false);
        this.equipButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (inventorySelected != null) {
                    Item item = inventory.drop(inventorySelected);
                    if (mainCharacter.setEquippedItem(item)) {
                        gameMenuBar.setEquipped(item.getName());
                        inventorySelected = "";
                        setButtonsActive(false);
                        updatePanels();
                        gameMenuBar.removeQuickAccessPanel();
                        gameMenuBar.setQuickAccessPanel();
                    } else {
                        inventory.add(item);
                    }
                }
            }
        });

        this.inactiveAddqaButton = new ImageButton(menuManager.generateTextureRegionDrawableObject("addqa inactive"));
        this.inactiveAddqaButton.setSize(170, 60);
        this.inactiveAddqaButton.setPosition(560, 20);

        this.addqaButton = new ImageButton(menuManager.generateTextureRegionDrawableObject("addqa"));
        this.addqaButton.setSize(170, 60);
        this.addqaButton.setPosition(560, 20);
        this.addqaButton.setVisible(false);
        this.addqaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(inventorySelected != null) {
                    inventory.quickAccessAdd(inventorySelected);
                    inventorySelected = "";
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
        resourcePanel.setBackground(menuManager.generateTextureRegionDrawableObject("inventory_panel"));

        return resourcePanel;
    }

    /**
     * Updates the item information displayed in the info panel, depending on
     * what item is selected.
     */
    private void updateItemInfo(){
        if (inventorySelected != null){
            infoPanel.setBackground(menuManager.generateTextureRegionDrawableObject(inventorySelected + "_desc"));
        } else{
            infoPanel.setBackground(menuManager.generateTextureRegionDrawableObject("Description_Panel"));
        }
    }

    private static final String SELECTED_SUFFIX = "-selected";

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


        for (Map.Entry<String, Integer> entry : inventoryAmounts.entrySet()) {
            Image selected = new Image(menuManager.generateTextureRegionDrawableObject("selected"));
            selected.setName(entry.getKey() + SELECTED_SUFFIX);
            selected.setSize((float) size + 20, (float) size + 20);
            selected.setPosition((float)(xpos + -10 + (size+xspace)*(count-1)), (float) ypos -10);
            selected.setVisible(false);
            String itemName = entry.getKey();

            ImageButton icon =
                    new ImageButton(menuManager.generateTextureRegionDrawableObject(itemName + "_inv"));
            icon.setName(entry.getKey());
            icon.setSize((float)size, (float)size);
            icon.setPosition(xpos + (size+xspace)*(count-1), ypos);
            iconAddListener(icon);
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
            num.setPosition(xspace*count + (float) size*count + xpos - 35, (float) ypos + 65);
            resourcePanel.addActor(num);

            count++;

            if ((count) % 4 == 0) {
                ypos -= 98;
                count = 0;
            }
        }
    }

    private void iconAddListener(ImageButton icon) {
        icon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(!inventorySelected.equals(icon.getName())){
                    inventorySelected = icon.getName();
                }else{
                    inventorySelected = "";
                }

                Actor selected = stage.getRoot().findActor(icon.getName() + SELECTED_SUFFIX);

                if(selected.isVisible()){
                    selected.setVisible(false);
                    setButtonsActive(false);

                }else{
                    for(Actor actor: resourcePanel.getChildren()){
                        String name = actor.getName();
                        if(name != null && name.contains(SELECTED_SUFFIX)){
                            actor.setVisible(false);
                        }
                    }

                    selected.setVisible(true);

                    setButtonsActive(true);

                }

                updateItemInfo();

            }
        });
    }

}
