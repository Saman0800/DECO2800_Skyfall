package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
        positionObjects.forEach((actor, posObj) -> posObj.updatePosition(actor));
    }

    public void update() {
        super.update();
        hudElements.forEach((key, value) -> value.update());
    }

    @Override
    public void draw() {
        ImageButton build = new ImageButton(generateTextureRegionDrawableObject("build"));
        build.setSize(219 * 0.55f, 207 * 0.55f);
        stage.addActor(build);

        ImageButton pause = new ImageButton(generateTextureRegionDrawableObject("pause"));
        pause.setSize(65, 65 * 146 / 207f);
        stage.addActor(pause);


        ImageButton settings = new ImageButton(generateTextureRegionDrawableObject("settings"));
        settings.setSize(65, 65 * 146 / 207f);
        stage.addActor(settings);


        ImageButton create = new ImageButton(generateTextureRegionDrawableObject("create_button"));
        create.scaleBy(0.5f, 0.5f);
        stage.addActor(create);


        TextButton teleport = new TextButton(" TELEPORT ", skin, "blue-pill2");
        teleport.getLabel().setStyle(skin.get("blue-pill", Label.LabelStyle.class));
        teleport.getLabel().setAlignment(Align.center);
        stage.addActor(teleport);


        positionObjects.put(build, (Actor actor) -> actor.setPosition(gmm.getBottomLeftX(), gmm.getBottomLeftY()));
        positionObjects.put(settings, (Actor actor) -> actor.setPosition(gmm.getTopLeftX() + 30, gmm.getTopLeftY() - 130));
        positionObjects.put(pause, (Actor actor) -> actor.setPosition(gmm.getTopLeftX() + 100, gmm.getTopLeftY() - 130));
        positionObjects.put(create, (Actor actor) -> actor.setPosition(gmm.getTopLeftX() + 30, gmm.getTopLeftY() - 250));
        positionObjects.put(teleport, (Actor actor) -> actor.setPosition(gmm.getTopLeftX() + 30, gmm.getTopLeftY() - 310));
    }
}
