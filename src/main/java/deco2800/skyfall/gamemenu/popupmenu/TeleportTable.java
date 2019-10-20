package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.buildings.AbstractPortal;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.saving.Save;

public class TeleportTable extends AbstractPopUpElement {
    private Skin skin;
    private Label locationLabel;
    private Label teleportLabel;
    private Save save;
    private AbstractPortal portal;
    private Blueprint purchased;
    public TeleportTable(Stage stage, ImageButton exit, String[] textureNames,
                         TextureManager tm, GameMenuManager gameMenuManager, Skin skin) {
        super(stage,exit, textureNames, tm, gameMenuManager);

        this.skin = skin;
        this.draw();

    }


    /**
     * Updates the location label
     * @param text The new text for it
     */
    public void updateLocation(String text) {
        locationLabel.setText("LOCATION : " + text);
    }
    /**
     * Updates the teleport to label
     * @param text The new text for it
     */
    public void updateTeleportTo(String text) {
        teleportLabel.setText("TELEPORT TO : " + text);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void draw() {
        super.draw();
        baseTable = new Table();
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("blue_pill_table"));
        baseTable.setSize(600, 600 * 1346 / 1862f);
        baseTable.setPosition(Gdx.graphics.getWidth()/2f - baseTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - baseTable.getHeight()/2);
        baseTable.top();

        Label titleLabel = new Label(" BIOME COMPLETE ", skin, "green-pill");


        ImageButton teleport = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("green_teleport_button"));


        Image teleportImg = new Image(gameMenuManager.generateTextureRegionDrawableObject("teleporting_man"));
        Table labelTable = new Table();
        labelTable.setSize(300, 600 * 1346 / 1862f);

        baseTable.add(titleLabel).align(Align.center).colspan(2);
        baseTable.row();
        baseTable.add(teleportImg).width(300).left().expandY();
        baseTable.add(labelTable).width(300).right().expandY();
        baseTable.row();
        baseTable.setVisible(false);

        teleport.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMenuManager.hideOpened();
                portal.teleport(save);
                gameMenuManager.getQuestManager().nextQuest();

                if (save.getGameStage() >= 3) {
                    gameMenuManager.getQuestManager().setupEndGameScreen();
                }

                if (purchased != null) {
                    gameMenuManager.getMainCharacter().removeBlueprint(purchased);
                }
            }
        });

        locationLabel = new Label("LOCATION: ERR", skin, "white-text");
        locationLabel.setFontScale(0.8f);
        teleportLabel = new Label("TELEPORT TO: ERR", skin, "white-text");
        teleportLabel.setFontScale(0.8f);

        labelTable.add();
        labelTable.row();
        labelTable.add(locationLabel);
        labelTable.row();
        labelTable.add(teleportLabel);
        labelTable.row();
        labelTable.add(teleport).colspan(2).bottom().width(200).expand().right();

        stage.addActor(baseTable);
    }


    public void setSave(Save save) {
        this.save = save;
    }

    public void setPortal(AbstractPortal abstractPortal) {
        this.portal = abstractPortal;
    }

    public void setPurchased(Blueprint purchased) {
        this.purchased = purchased;
    }
}