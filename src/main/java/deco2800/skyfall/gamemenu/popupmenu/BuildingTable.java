package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.resources.Blueprint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A class for building table pop up.
 */
public class BuildingTable extends AbstractPopUpElement {
    private Skin skin;
    private Table table;
    private Table itemInfo;
    private Blueprint selectedItem = null;
    private InventoryTable inventoryTable;

    /**
     * Constructs a building table.
     *
     * @param stage           Current stage.
     * @param exit            Exit button if it has one.
     * @param textureNames    Names of the textures.
     * @param tm              Current texture manager.
     * @param gameMenuManager Current game menu manager.
     * @param skin            Current skin.
     */
    public BuildingTable(Stage stage, ImageButton exit, String[] textureNames, TextureManager tm,
            GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames, tm, gameMenuManager);
        this.skin = skin;
        inventoryTable = (InventoryTable) gameMenuManager.getPopUp("inventoryTable");
        this.draw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        super.hide();
        itemInfo.clearChildren();
        table.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        draw();
        super.show();
        table.setVisible(true);
    }

    /**
     * {@inheritDoc}
     *
     * Draw the whole building table.
     */
    @Override
    public void draw() {
        super.draw();

        table = new Table();
        table.setSize(800, 800 * 1346 / 1862f);
        table.setPosition(Gdx.graphics.getWidth() / 2f - table.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - table.getHeight() / 2);
        table.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_bg"));

        // table banner
        Table banner = new Table();
        banner.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_banner"));

        Label text = new Label("BUILDING TABLE", skin, "navy-text");
        text.setFontScale(1.1f);
        banner.add(text);

        table.add(banner).width(750).height(750 * 188f / 1756).padTop(20).colspan(2);
        table.row();

        // Left hand side of the table
        Table blueprint = new Table();
        Label blueprintTitle = new Label("BLUEPRINT", skin, "black-label");
        blueprint.add(blueprintTitle).padTop(10);
        blueprint.row();

        // Information about the item selected (left hand side)
        itemInfo = new Table();
        blueprint.add(itemInfo);

        // Right hand side of the table (list of blueprints
        Table items = new Table();
        items.row();


        List<Blueprint> blueprintsLearned = gameMenuManager.getMainCharacter().getBlueprintsLearned();
        // Generating items in getBlueprintsLearned
        // Row
        float itemWidth = 400 / 4f - 10;
        for (int i = 0; i < 3; i++) {
            // Column
            for (int j = 0; j < 4; j++) {
                try {
                    Blueprint item = blueprintsLearned.get(4 * i + j);
                    ImageButton icon = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject(item.getName() + "_inv"));
                    icon.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            showInfo(itemInfo, item);
                            selectedItem = item;
                        }
                    });
                    items.add(icon).width(itemWidth).height(itemWidth).pad(5);
                } catch (IndexOutOfBoundsException e) {
                    Image bg = new Image(gameMenuManager.generateTextureRegionDrawableObject("item_background"));
                    items.add(bg).width(itemWidth).height(itemWidth).pad(5);
                }
            }
            items.row();
        }

        table.add(blueprint).width(400).top().expand();
        table.add(items).width(400).top().expand();
        table.row();

        TextButton buildIt = new TextButton("BUILD IT", skin, "game");
        buildIt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedItem != null) {
                    gameMenuManager.getMainCharacter().createItem(selectedItem);
                    inventoryTable = (InventoryTable) gameMenuManager.getPopUp("inventoryTable");
                    inventoryTable.updatePanels();
                    hide();
                }
            }
        });

        table.add(buildIt).colspan(2).expand();

        table.setVisible(false);
        stage.addActor(table);
    }

    /**
     * Shows information, including items required to create that item.
     *
     * @param table Items' info table.
     */
    private void showInfo(Table table, Blueprint item) {
        table.clearChildren();
        Image test = new Image(gameMenuManager.generateTextureRegionDrawableObject(item.getName() + "_inv"));
        table.add(test).width(110).height(110).padTop(10).padBottom(10);
        table.row();

        Table itemsRequired = new Table();
        itemsRequired.top();
        itemsRequired.setBackground(gameMenuManager.generateTextureRegionDrawableObject("pop up screen"));

        Table infoBar = new Table();
        infoBar.setBackground(gameMenuManager.generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("ITEMS REQUIRED", skin, "black-text");
        text.setFontScale(0.5f);
        infoBar.add(text);

        itemsRequired.add(infoBar).width(230).height(230 * 188f / 1756).colspan(10).padTop(5);
        itemsRequired.row();

        List<String> itemsNeeded = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : item.getAllRequirements().entrySet()) {
            if (entry.getValue() != 0) {
                itemsNeeded.add(entry.getKey());
            }
        }

        // Generates items requried.
        for (int i = 0; i < 2; ++i) {
            itemsRequired.add().width(20);
            for (int j = 0; j < 4; ++j) {
                try {
                    String itemName = itemsNeeded.get(4 * i + j);
                    itemsRequired.add(new Image(gameMenuManager.generateTextureRegionDrawableObject(itemName + "_inv"))).width((250 - 20 - 20) / 4f - 5).height((250 - 20 - 20) / 4f - 5).pad(5).expandY();
                    Label number = new Label(String.valueOf(item.getAllRequirements().get(itemName)), skin, "white-label");
                    number.setFontScale(0.3f);
                    itemsRequired.add(number).top().padLeft(-15).padTop(5);
                } catch (IndexOutOfBoundsException e) {
                    itemsRequired.add(new Image(gameMenuManager.generateTextureRegionDrawableObject("item_background"))).width((250 - 20 - 20) / 4f - 5).height((250 - 20 - 20) / 4f - 5).pad(5).expandY();
                    itemsRequired.add();
                }
            }
            itemsRequired.add().width(20);
            itemsRequired.row();
        }

        table.add(itemsRequired).width(250).height(250 * 1346 / 1862f);

    }
}
