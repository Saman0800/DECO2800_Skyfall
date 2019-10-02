package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class HeadsUpDisplay extends AbstractUIElement {
    interface UpdatePositionInterface {
        void updatePosition(Actor actor);
    }

    private final GameMenuManager gmm;
    private final Skin skin;
    private Map<String, AbstractUIElement> hudElements;
    private Map<Actor, UpdatePositionInterface> positionObjects;
    private Table leftHUDTable;
    private ImageButton location;
    private TextButton teleport;
    private boolean canTeleport = false;
    public HeadsUpDisplay(Stage stage, String[] textureNames, TextureManager tm,
                          Skin skin, GameMenuManager gmm,
                          Map<String, AbstractUIElement> hudElements) {
        super(stage, textureNames, tm);
        this.gmm = gmm;
        this.skin = skin;
        this.hudElements = hudElements;
        this.positionObjects = new HashMap<>();
        this.draw();
    }


    @Override
    public void updatePosition() {
        leftHUDTable.setPosition(gmm.getTopLeftX() + 30, gmm.getTopLeftY() - 3 * stage.getCamera().viewportHeight / 4);
        leftHUDTable.setHeight(stage.getCamera().viewportHeight / 2);
        location.setSize(stage.getCamera().viewportWidth / 6, stage.getCamera().viewportHeight / 6);
        positionObjects.forEach((actor, posObj) -> posObj.updatePosition(actor));
    }

    public void update() {
        super.update();
        hudElements.forEach((key, value) -> value.update());
        //TODO: (@Kausta) If can teleport enable the teleport button
        if (teleport != null && canTeleport) {
            teleport.getLabel().setColor(0f, 1f, 0f,1);
            teleport.setDisabled(true);
        } else {
            teleport.getLabel().setColor(0.25f, 0.25f, 0.25f,1);
            teleport.setDisabled(false);
        }

    }

    @Override
    public void draw() {
        ImageButton pause = new ImageButton(generateTextureRegionDrawableObject("pause"));
        pause.setSize(65, 65 * 146 / 207f);

        ImageButton settings = new ImageButton(generateTextureRegionDrawableObject("settings"));
        settings.setSize(65, 65 * 146 / 207f);

        ImageButton create = new ImageButton(generateTextureRegionDrawableObject("create_button"));
        create.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gmm.hideOpened();
                gmm.setPopUp("createTable");
            }
        });



        ImageButton collect = new ImageButton(generateTextureRegionDrawableObject("collect_button"));
        collect.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gmm.hideOpened();
                gmm.setPopUp("collectTable");
            }
        });

        teleport = new TextButton("TELEPORT", skin, "blue-pill");
        teleport.getLabel().setStyle(skin.get("blue-pill", Label.LabelStyle.class));
        teleport.getLabel().setAlignment(Align.center);
        teleport.getLabel().setFontScale(0.8f);
        teleport.getLabel().setColor(0.25f, 0.25f, 0.25f,1);
        teleport.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened(gmm);
                gmm.setPopUp("teleportTable");
            }
        });


        TextButton pauseT = new TextButton("PAUSE", skin, "blue-pill");
        pauseT.getLabel().setStyle(skin.get("blue-pill", Label.LabelStyle.class));
        pauseT.getLabel().setAlignment(Align.center);
        pauseT.getLabel().setFontScale(0.8f);
        pauseT.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened(gmm);
                gmm.setPopUp("pauseTable");
            }
        });


        TextButton helpT = new TextButton("HELP", skin, "blue-pill");
        helpT.getLabel().setStyle(skin.get("blue-pill", Label.LabelStyle.class));
        helpT.getLabel().setAlignment(Align.center);
        helpT.getLabel().setFontScale(0.8f);
        helpT.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened(gmm);
                gmm.setPopUp("helpTable");
            }
        });

        location = new ImageButton(generateTextureRegionDrawableObject("location_button"));
        location.setSize(200, 200);
        location.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened(gmm);
                gmm.setPopUp("locationTable");
            }
        });
        stage.addActor(location);


        positionObjects.put(location, (Actor actor) -> actor.setPosition(gmm.getBottomLeftX() + stage.getCamera().viewportWidth / 1024, gmm.getBottomLeftY() + stage.getCamera().viewportHeight / 1024));

        leftHUDTable = new Table();
        leftHUDTable.setDebug(true);
        leftHUDTable.setWidth(200);
        leftHUDTable.add(collect).width(200).height(100);
        leftHUDTable.row();
        leftHUDTable.add(create).width(200).height(100);
        leftHUDTable.row();
        leftHUDTable.add(teleport).expandY().fillX();
        leftHUDTable.row();
        leftHUDTable.add(pauseT).expandY().fillX();
        leftHUDTable.row();
        leftHUDTable.add(helpT).expandY().fillX();
        leftHUDTable.row();
        stage.addActor(leftHUDTable);

    }
}