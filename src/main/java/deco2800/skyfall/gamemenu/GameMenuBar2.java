package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.popupmenu.InventoryTable;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.resources.Item;

import java.util.Map;

/**
 * A class for the right hand side of the menu.
 */
public class GameMenuBar2 extends AbstractUIElement {

    // Current game menu manager
    private final GameMenuManager gmm;
    // Current skin
    private final Skin skin;
    // Quick access panel on the right
    private Table quickAccessPanel;
    // Shows what is equipped (Top right corner)
    private Table equippedTable;
    // Text in eqiuppedTable
    private Label equipped;
    // Blue side bar next to quickAccessPanel
    private ImageButton sideBar;
    // Opens up building table (bottom right)
    private ImageButton build;
    // Current item selected in inventory user interface
    private String quickAccessSelected = "";

    private ImageButton equipInactive;
    private ImageButton equipActive;
    private ImageButton removeInactive;
    private ImageButton removeActive;

    // Current inventory
    private InventoryManager inventory;
    // Main character in the game
    private MainCharacter mainCharacter;

    private boolean isInventoryTableOn;

    /**
     * Constructs the right side of the menu including equipped table which shows
     * what users are equipping, quick access panel, side bar which opens up
     * inventory and build icon which opens up building table.
     *
     * @param stage        Stage of the game.
     * @param textureNames Texture names.
     * @param tm           TextureManager of the game.
     * @param skin         Skin used for the game.
     * @param gmm          Current game menu manager
     */
    public GameMenuBar2(Stage stage, String[] textureNames, TextureManager tm, Skin skin, GameMenuManager gmm) {
        super(stage, textureNames, tm);
        this.gmm = gmm;
        this.skin = skin;
        this.draw();
        this.inventory = gmm.getInventory();
        this.mainCharacter = gmm.getMainCharacter();
    }

    /**
     * Updates position of each actor, i.e let them stick to one side
     */
    @Override
    public void updatePosition() {
        equippedTable.setPosition(gmm.getTopRightX() - 170, gmm.getTopLeftY() - 130);
        quickAccessPanel.setPosition(gmm.getTopRightX() - 170, gmm.getTopRightY() - 650);
        // t.setHeight(stage.getCamera().viewportHeight / 2);
        sideBar.setPosition(gmm.getTopRightX() - 180, gmm.getTopRightY() - 520);
        build.setPosition(gmm.getBottomRightX() - 170, gmm.getBottomRightY());
    }

    /**
     * Draws out the right hand side of the menu, including quick access and build
     * icon (at the button right).
     */
    @Override
    public void draw() {
        showEquipped();
        setQuickAccessPanel();

        build = new ImageButton(gmm.generateTextureRegionDrawableObject("build"));
        build.setSize(150, 295f / 316 * 150);
        build.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gmm.hideOpened();
                gmm.setPopUp("buildingTable");
            }
        });

        stage.addActor(build);
    }

    /**
     * Displays the equipped table at the top right corner showing what is equipped.
     */
    public void showEquipped() {
        equippedTable = new Table().bottom();
        equippedTable.setBackground(gmm.generateTextureRegionDrawableObject("equipped_bar"));
        equippedTable.setSize(150, 100);
        // Equipped item text
        equipped = new Label("Nothing", skin, "white-text");
        equipped.setFontScale(0.7f);
        equippedTable.add(equipped).padBottom(10);
        stage.addActor(equippedTable);
    }

    public void setQuickAccessPanel() {
        quickAccessPanel = new Table().top().left();
        quickAccessPanel.setBackground(gmm.generateTextureRegionDrawableObject("quickaccess_bg"));
        quickAccessPanel.setSize(150, 490);
        setQuickAccessItems();

        equipInactive = new ImageButton(gmm.generateTextureRegionDrawableObject("equip inactive"));
        equipInactive.setSize(130, 50);
        equipActive = new ImageButton(gmm.generateTextureRegionDrawableObject("equip"));
        equipActive.setSize(130, 50);
        equipActive.setVisible(false);
        this.equipActive.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!quickAccessSelected.isEmpty() && !isInventoryTableOn) {
                    Item item = inventory.drop(quickAccessSelected);
                    if (mainCharacter.setEquippedItem(item)) {
                        setEquipped(quickAccessSelected);
                        quickAccessSelected = "";
                        setButtonsActive(false);
                        removeQuickAccessPanel();
                        setQuickAccessPanel();
                    } else {
                        inventory.add(item);
                    }
                }
            }
        });

        Table equipCell = new Table();
        equipCell.addActor(equipActive);
        equipCell.addActor(equipInactive);
        quickAccessPanel.add(equipCell).width(130).height(50).padTop(5).padLeft(10);
        quickAccessPanel.row();

        removeInactive = new ImageButton(gmm.generateTextureRegionDrawableObject("removeqa inactive"));
        removeInactive.setSize(130, 50);
        removeActive = new ImageButton(gmm.generateTextureRegionDrawableObject("removeqa"));
        removeActive.setSize(130, 50);
        removeActive.setVisible(false);
        this.removeActive.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!quickAccessSelected.isEmpty() && !isInventoryTableOn) {
                    inventory.quickAccessRemove(quickAccessSelected);
                    quickAccessSelected = "Nothing";
                    removeQuickAccessPanel();
                    setQuickAccessPanel();
                }
            }
        });

        Table removeCell = new Table();
        removeCell.addActor(removeActive);
        removeCell.addActor(removeInactive);
        quickAccessPanel.add(removeCell).width(130).height(50).padTop(5).padLeft(10);

        // Adding the side bar near quick access
        sideBar = new ImageButton(gmm.generateTextureRegionDrawableObject("quickaccess_side_bar"));
        sideBar.setSize(35, 360);
        sideBar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gmm.hideOpened();
                gmm.setPopUp("inventoryTable");
            }
        });

        stage.addActor(quickAccessPanel);
        stage.addActor(sideBar);
    }

    public void removeQuickAccessPanel() {
        quickAccessPanel.remove();
        sideBar.remove();
    }

    /**
     * Sets the items in the quick access inventory
     */
    public void setQuickAccessItems() {
        Map<String, Integer> quickAccess = gmm.getInventory().getQuickAccess();

        int size = 80;

        String[] weapons = { "axe", "box", "spear", "sword" };

        float sideBarWidth = 35;

        // Places each item to quick access
        for (Map.Entry<String, Integer> entry : quickAccess.entrySet()) {
            Image selected = new Image(gmm.generateTextureRegionDrawableObject("selected"));
            selected.setName(entry.getKey() + "-qaSelected");
            selected.setSize((float) size + 15, (float) size + 15);
            selected.setPosition((float) -7.5, (float) -7.5);
            selected.setVisible(false);

            String weaponName = entry.getKey();
            for (String weapon : weapons) {
                if (weapon.equals(entry.getKey())) {
                    weaponName = entry.getKey() + "_tex";
                }
            }
            Table iconCell = new Table();
            iconCell.setName("iconCell");
            ImageButton icon = new ImageButton(gmm.generateTextureRegionDrawableObject(weaponName + "_inv"));
            icon.setName(entry.getKey());

            icon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (isInventoryTableOn) {
                        return;
                    }
                    if (!quickAccessSelected.equals(icon.getName())) {
                        quickAccessSelected = icon.getName();
                    } else {
                        quickAccessSelected = "Nothing";
                    }

                    Actor selected = stage.getRoot().findActor(icon.getName() + "-qaSelected");

                    if (selected.isVisible()) {
                        selected.setVisible(false);
                        setButtonsActive(false);

                    } else {
                        for (Actor actor : quickAccessPanel.getChildren()) {
                            if (actor.getName() != null && actor.getName().equals("iconCell")
                                    && actor instanceof Table) {
                                Table iconCell = (Table) actor;

                                for (Actor iconActor : iconCell.getChildren()) {
                                    String name = iconActor.getName();
                                    if (name != null && name.contains("-qaSelected")) {
                                        iconActor.setVisible(false);
                                    }
                                }
                            }
                        }

                        selected.setVisible(true);
                        setButtonsActive(true);
                    }
                }
            });

            icon.setSize(size, size);
            iconCell.addActor(selected);
            iconCell.addActor(icon);
            quickAccessPanel.add(iconCell).width(size).height(size).padTop(10).padLeft(20 + sideBarWidth / 2);
            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setFontScale(0.4f);
            quickAccessPanel.add(num).top().left().padLeft(-20).padTop(5);
            quickAccessPanel.row();
        }

        int sizeDifference = 4 - quickAccess.entrySet().size();
        while (sizeDifference > 0) {
            sizeDifference -= 1;
            Table blankCell = new Table();
            quickAccessPanel.add(blankCell).width(size).height(size).padTop(10).padLeft(20 + sideBarWidth / 2);
            quickAccessPanel.row();
        }
    }

    /**
     * Sets the buttons in the inventory pop up to active or inactive forms
     * 
     * @param active boolean whether buttons are active
     */
    private void setButtonsActive(boolean active) {
        if (active) {
            removeInactive.setVisible(false);
            removeActive.setVisible(true);

            if (inventory.getItemInstance(quickAccessSelected).isEquippable()) {
                equipActive.setVisible(true);
                equipInactive.setVisible(false);
            }
        } else {
            equipInactive.setVisible(true);
            removeInactive.setVisible(true);
            equipActive.setVisible(false);
            removeActive.setVisible(false);
        }
    }

    /**
     * Gets what is equipped right now.
     *
     * @return The name of the equipped item.
     */
    public String getEquipped() {
        return equipped.getText().toString();
    }

    /**
     * Sets the text in equipped table to {itemName}.
     *
     * @param itemName Item that is equipped.
     */
    public void setEquipped(String itemName) {
        equipped.setText(itemName);
    }

    @Override
    public void update() {
        super.update();
        if (gmm.getCurrentPopUp() instanceof InventoryTable) {
            isInventoryTableOn  = true;
        } else {
            isInventoryTableOn = false;
        }
        setEquipped(gmm.getMainCharacter().getEquippedItem().getName());
    }
}
