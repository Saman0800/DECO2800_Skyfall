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
 * A class for help table pop up.
 */
public class HelpTable extends AbstractPopUpElement{
    private Skin skin;
    private Table table;

    /**
     * Constructs a help table.
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
     */
    @Override
    public void hide() {
        super.hide();
        table.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        super.show();
        table.setVisible(true);
    }


    /**
     * {@inheritDoc}
     * Draw help table.
     */
    @Override
    public void draw() {
        super.draw();
        // Set up main table
        table = new Table().top();
        table.setSize(750, 600);
        table.setPosition(Gdx.graphics.getWidth()/2f - table.getWidth()/2,
                Gdx.graphics.getHeight() / 2f - table.getHeight()/2);
        table.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_bg"));

        drawBanner();
        // Show first page
        toPrev();
        table.setVisible(false);
        stage.addActor(table);
    }

    /**
     * Draws out table banner, i.e. "HELP".
     */
    private void drawBanner() {
        Table banner = new Table();
        banner.setBackground(gameMenuManager.generateTextureRegionDrawableObject("popup_banner"));

        Label title = new Label("HELP", skin, "navy-text");
        banner.add(title);

        table.add(banner).width(700).height(70).padTop(20).colspan(2);
        table.row().padTop(10);
    }

    /**
     * Show the second page of the table (more commands)
     */
    private void toNext() {
        Image page2 = new Image(gameMenuManager.generateTextureRegionDrawableObject("help_page2"));
        table.add(page2).width(650).height(1704f / 2556 * 650).colspan(2);
        table.row();

        TextureRegionDrawable arrow = gameMenuManager.generateTextureRegionDrawableObject("arrow");
        ImageButton leftArrow = new ImageButton(arrow);
        // Rotates the arrow
        leftArrow.setTransform(true);
        leftArrow.setOrigin(30, 25);
        leftArrow.setRotation(180);
        table.add(leftArrow).width(60).height(50).padLeft(10).spaceRight(10);
        leftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Redraw the table and switch to first page
                table.clearChildren();
                drawBanner();
                toPrev();
            }
        });

        Label next = new Label("PREVIOUS", skin, "white-text");
        next.setFontScale(0.7f);
        table.add(next).left().expandX();
    }

    /**
     * Shows the previous page, i.e. the first page.
     */
    private void toPrev() {
        Image page1 = new Image(gameMenuManager.generateTextureRegionDrawableObject("help_page1"));
        table.add(page1).width(650).height(1704f / 2556 * 650).colspan(2);
        table.row();

        Label next = new Label("NEXT", skin, "white-text");
        next.setFontScale(0.7f);
        table.add(next).right().expandX();

        TextureRegionDrawable arrow = gameMenuManager.generateTextureRegionDrawableObject("arrow");
        ImageButton rightArrow = new ImageButton(arrow);
        table.add(rightArrow).width(60).height(50).spaceLeft(10).padRight(10);
        rightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Redraws the table and switch to the second page
                table.clearChildren();
                drawBanner();
                toNext();
            }
        });
    }
}
