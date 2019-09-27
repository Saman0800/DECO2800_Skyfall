package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class GameMenuBar extends AbstractUIElement {


    private GameMenuManager gameMenuManager;
    private Table quickAccessPanel;
    private Skin skin;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMenuBar.class);

    public GameMenuBar(Stage stage, String[] textureNames, TextureManager tm, GameMenuManager gameMenuManager) {
        super(stage, textureNames, tm);
        this.gameMenuManager = gameMenuManager;
        this.skin = gameMenuManager.getSkin();
        this.draw();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePosition() {
        //TODO : make menu bar scale with window size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        TextureManager textureManager = this.tm;
        Image menuBar = new Image(textureManager.getTexture("game menu bar"));
        menuBar.setSize(910, 140);
        menuBar.setPosition(185, 20);
        stage.addActor(menuBar);
        showButtons();
    }

    /**
     * Display buttons in the menu bar.
     */
    private void showButtons() {
        int width;
        width = 65;
        ImageButton pause = new ImageButton(generateTextureRegionDrawableObject("pause"));

        pause.setSize(width, width * 146 / 207f);
        pause.setPosition(208, 105);
        stage.addActor(pause);
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened();
                gameMenuManager.setPopUp("pauseTable");
            }
        });

        //Set quick access panel with inventory button
        setQuickAccessPanel();


        ImageButton selectCharacter = new ImageButton(generateTextureRegionDrawableObject("select-character"));
        selectCharacter.setSize(width, width * 146 / 207f);
        selectCharacter.setPosition(208, 30 * 1000 / 800f);
        stage.addActor(selectCharacter);

        selectCharacter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened();
                gameMenuManager.setPopUp("playerSelectTable");
            }
        });

        ImageButton info = new ImageButton(generateTextureRegionDrawableObject("info"));
        info.setSize(width, width * 146 / 207f);
        info.setPosition(1015, 105);
        stage.addActor(info);

        info.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened();
                gameMenuManager.setPopUp("helpTable");
            }
        });

        ImageButton settings = new ImageButton(generateTextureRegionDrawableObject("settings"));
        settings.setSize(width, width * 146 / 207f);
        settings.setPosition(1015, 30 * 1000 / 800f);
        stage.addActor(settings);

        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened();
                gameMenuManager.setPopUp("settingsTable");
            }
        });

        ImageButton build = new ImageButton(generateTextureRegionDrawableObject("build"));
        build.setSize(219 * 0.55f, 207 * 0.55f);
        build.setPosition(300, 30 * 1000 / 800f);
        stage.addActor(build);

        build.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened();
                gameMenuManager.setPopUp("buildingTable");
            }
        });


        LOGGER.info("Finished drawing menu bar");
    }

    /***
     * Sets the quick access panel and inventory button displayed on the game's hot bar.
     */
    public void setQuickAccessPanel(){
        //Set Quick Access Panel
        quickAccessPanel = new Table();
        quickAccessPanel.setSize(450, 207 * 0.55f);
        quickAccessPanel.setPosition(560, 30 * 1000 / 800f);
        quickAccessPanel.setBackground(generateTextureRegionDrawableObject("quick_access_panel"));

        //Populate quick access GUI with resources
        updateQuickAccess();

        stage.addActor(quickAccessPanel);


        //Set open inventory button icon in quick access
        ImageButton inventoryButton = new ImageButton(generateTextureRegionDrawableObject("inv_button"));
        inventoryButton.setSize(50, 50 * 146 / 207f);
        inventoryButton.setPosition(950, 78);

        stage.addActor(inventoryButton);

        //Add inventory button listener
        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.setPopUp("inventoryTable");
            }
        });
    }

    /***
     * Updates the quick access inventory display to show the current contents
     * of the quick access inventory.
     */
    public void updateQuickAccess(){
        Map<String, Integer> quickAccess = gameMenuManager.getInventory().getQuickAccess();

        int count = 1;
        int xpos = 15;
        int ypos = 28;
        int size = 55;

        String[] weapons = {"axe", "box", "spear", "sword"};

        for (Map.Entry<String, Integer> entry : quickAccess.entrySet()) {
            String weaponName = entry.getKey();
            for (String weapon : weapons) {
                if (weapon.equals(entry.getKey())) {
                    weaponName = entry.getKey() + "_tex";
                }
            }
            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(weaponName + "_inv"));
            icon.setSize(size, size);
            icon.setPosition((xpos*count) + size*(count-1), ypos);

            quickAccessPanel.addActor(icon);

            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setPosition(xpos*count + size*count - 10, ypos + 40);
            num.setFontScale((float)0.4);
            int numWidth = 18;
            if(entry.getValue()>9){
                numWidth += 8;
            }
            if(entry.getValue()>99){
                numWidth += 8;
            }
            num.setSize(numWidth, 25);
            quickAccessPanel.addActor(num);

            count++;

        }
    }

    public Table getQuickAccessPanel(){
        return quickAccessPanel;
    }

    public void removeQuickAccessPanel(){
        quickAccessPanel.remove();
    }

    /**
     * If there is any opened popup, closes it.
     */
    private void hideOpened() {
        if (gameMenuManager.getCurrentPopUp() != null) {
            gameMenuManager.getCurrentPopUp().hide();
        }
    }
}
