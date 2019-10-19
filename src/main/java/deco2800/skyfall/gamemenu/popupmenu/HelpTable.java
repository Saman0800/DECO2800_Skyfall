package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

/**
 * A class for help baseTable pop up.
 */
public class HelpTable extends AbstractPopUpElement{
    private Skin skin;
    private Image[] pages = {new Image(gameMenuManager.generateTextureRegionDrawableObject("help_page1")),
           new Image(gameMenuManager.generateTextureRegionDrawableObject("help_page2")),
         new Image(gameMenuManager.generateTextureRegionDrawableObject("help_page3"))};
    private static final int NUMBER_OF_PAGES = 3;

    /**
     * Constructs a help baseTable.
     *
     * @param stage Current stage.
     * @param exit Exit button if it has one.
     * @param textureNames Names of the textures.
     * @param tm Current texture manager.
     * @param gameMenuManager Current game menu manager.
     * @param skin Current skin.
     */
    public HelpTable(Stage stage, ImageButton exit,
                     String[] textureNames, TextureManager tm,
                     GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.skin = skin;
        this.draw();
    }


    /**
     * {@inheritDoc}
     * Draw help baseTable.
     */
    @Override
    public void draw() {
        super.draw();
        // Set up main baseTable
        baseTable = new Table().top();
        baseTable.setSize(750, 600);
        baseTable.setPosition(Gdx.graphics.getWidth()/2f - baseTable.getWidth()/2,
                Gdx.graphics.getHeight() / 2f - baseTable.getHeight()/2);
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_bg"));

        drawBanner();
        // Show first page
        toPage(1);
        baseTable.setVisible(false);
        stage.addActor(baseTable);
    }

    /**
     * Draws out baseTable banner, i.e. "HELP".
     */
    private void drawBanner() {
        Table banner = new Table();
        banner.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_banner"));

        Label title = new Label("HELP", skin, "navy-text");
        banner.add(title);


        baseTable.add(banner).width(700).height(70).padTop(20).colspan(4);
        baseTable.row().padTop(10);
    }

    /**
     * Switch page {page} (more commands).
     *
     * @param page page we want to switch to
     */
    private void toPage(int page) {
        Image currentPage = pages[page - 1];
        baseTable.add(currentPage).width(650).height(1704f / 2556 * 650).colspan(4);
        baseTable.row();

        TextureRegionDrawable arrow = gameMenuManager.generateTextureRegionDrawableObject("arrow");

        // Add left arrow except for the first page
        if (page > 1) {
            ImageButton leftArrow = new ImageButton(arrow);
            // Rotates the arrow
            leftArrow.setTransform(true);
            leftArrow.setOrigin(30, 25);
            leftArrow.setRotation(180);
            baseTable.add(leftArrow).width(60).height(50).padLeft(10).spaceRight(10);
            leftArrow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Redraws the table and switch to the next page
                    baseTable.clearChildren();
                    drawBanner();
                    toPage(page-1);
                }
            });
            Label prev = new Label("PREVIOUS", skin, "white-text");
            prev.setFontScale(0.7f);
            baseTable.add(prev).left().expandX();
        }

        // Add right arrow except for the last page
        if (page < NUMBER_OF_PAGES) {
            Label next = new Label("NEXT", skin, "white-text");
            next.setFontScale(0.7f);
            baseTable.add(next).right().expandX();

            ImageButton rightArrow = new ImageButton(arrow);
            baseTable.add(rightArrow).width(60).height(50).spaceLeft(10).padRight(10);
            rightArrow.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Redraw the table and switch to the previous page
                    baseTable.clearChildren();
                    drawBanner();
                    toPage(page+1);
                }
            });
        }

    }
}
