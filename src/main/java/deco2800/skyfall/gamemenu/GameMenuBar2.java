package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class GameMenuBar2 extends AbstractUIElement {

    private final GameMenuManager gmm;
    private final Skin skin;
    private Table quickAccessPanel;
    private ImageButton sideBar;
    private ImageButton build;

    public GameMenuBar2(Stage stage, String[] textureNames, TextureManager tm, Skin skin, GameMenuManager gmm) {
        super(stage, textureNames, tm);
        this.gmm = gmm;
        this.skin = skin;
        this.draw();
    }

    @Override
    public void updatePosition() {
        quickAccessPanel.setPosition(gmm.getTopRightX() - 170, gmm.getTopRightY() - 650);
        //t.setHeight(stage.getCamera().viewportHeight / 2);
        sideBar.setPosition(gmm.getTopRightX() - 180, gmm.getTopRightY() - 460);
        build.setPosition(gmm.getBottomRightX() - 170, gmm.getBottomRightY() + 10);
    }

    @Override
    public void draw() {
        quickAccessPanel = new Table().top().left();
        sideBar = new ImageButton(generateTextureRegionDrawableObject("quickaccess_side_bar"));
        quickAccessPanel.setDebug(true);
        quickAccessPanel.setBackground(GameMenuManager.generateTextureRegionDrawableObject("quickaccess_bg"));
        quickAccessPanel.setSize(150, 550);
        updateQuickAccess();
        TextButton.TextButtonStyle test = new TextButton.TextButtonStyle();
        test.up = generateTextureRegionDrawableObject("blue_pill");
        test.down = generateTextureRegionDrawableObject("blue_pill");
        test.font = skin.getFont("game-font");
        TextButton equip = new TextButton("EQUIP", test);
        equip.getLabel().setFontScale(0.8f);
        quickAccessPanel.add(equip).width(120).height(50).padTop(10);
        quickAccessPanel.row();
        TextButton remove = new TextButton("REMOVE", test);
        remove.getLabel().setFontScale(0.8f);
        quickAccessPanel.add(remove).width(120).height(50).padTop(10);

        sideBar.setSize(35, 360); //TODO: reduce chunkiness
        sideBar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened(gmm);
                gmm.setPopUp("inventoryTable");
            }
        });

        build = new ImageButton(generateTextureRegionDrawableObject("build"));
        build.setSize(150, 295f / 316 * 150);
        build.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened(gmm);
                gmm.setPopUp("buildingTable");
            }
        });

        stage.addActor(quickAccessPanel);
        stage.addActor(sideBar);
        stage.addActor(build);
    }

    /**
     * Updates the quick access inventory display to show the current contents
     * of the quick access inventory.
     */
    public void updateQuickAccess(){
        Map<String, Integer> quickAccess = gmm.getInventory().getQuickAccess();

        int count = 1;
        int size = 80;

        String[] weapons = {"axe", "box", "spear", "sword"};

        float sideBarWidth = 35;

        for (Map.Entry<String, Integer> entry : quickAccess.entrySet()) {
            String weaponName = entry.getKey();
            for (String weapon : weapons) {
                if (weapon.equals(entry.getKey())) {
                    weaponName = entry.getKey() + "_tex";
                }
            }
            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(weaponName + "_inv"));
            quickAccessPanel.add(icon).width(size).height(size).padTop(10).padLeft(20 + sideBarWidth/2);
            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setFontScale(0.4f);
            quickAccessPanel.add(num).top().left().padLeft(-10).padTop(5);
            quickAccessPanel.row();

            count++;
        }
    }

}
