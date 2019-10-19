package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;


/**
 * A class for player select table pop up.
 */
public class PlayerSelectTable extends AbstractPopUpElement{

    private Skin skin;
    private int currentCharacter;
    private MainCharacter mainCharacter;


    /**
     * Constructs a player select table.
     *
     * @param stage Current stage.
     * @param exit Exit button if it has one.
     * @param textureNames Names of the textures.
     * @param tm Current texture manager.
     * @param gameMenuManager Current game menu manager.
     * @param skin Current skin.
     */
    public PlayerSelectTable(Stage stage, ImageButton exit,
                      String[] textureNames, TextureManager tm,
                      GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.skin = skin;
        this.draw();
        mainCharacter = gameMenuManager.getMainCharacter();
    }


    /**
     * {@inheritDoc}
     * Draw player select table.
     */
    @Override
    public void draw() {
        super.draw();
        baseTable = new Table();
        baseTable.setSize(600, 600 * 1346 / 1862f);
        baseTable.setPosition(Gdx.graphics.getWidth()/2f - baseTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - baseTable.getHeight()/2);
        baseTable.setBackground(gameMenuManager.generateTextureRegionDrawableObject("pop up screen"));

        Table infoBar = new Table();
        infoBar.setBackground(gameMenuManager.generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("PLAYER SELECT", skin, "black-text");
        infoBar.add(text);

        baseTable.add(infoBar).width(550).height(550f * 180 / 1756).padTop(20).colspan(5).padBottom(20);
        baseTable.row();

        int arrowWidth = 60;

        //height = width
        ImageButton leftArrow = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("left_arrow"));

        baseTable.add(leftArrow).width(arrowWidth).height(arrowWidth).expandY();

        Table[] characterTables = new Table[3];

        for (int i = 0; i < 3; i++) {
            Table characterTable = new Table();
            characterTables[i] = characterTable;
        }

        float characterTableWidth = (550 - arrowWidth * 2) / 3f;

        updateCharacters(characterTables, characterTableWidth);

        for (int i = 0; i < 3; i++) {
            baseTable.add(characterTables[i]).width(characterTableWidth).height(600f * 1346 / 1862 - 550f * 180 / 1756 - 40 - 30 - 200f*138/478).spaceLeft(5).spaceRight(5);
        }

        ImageButton rightArrow = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("right_arrow"));
        baseTable.add(rightArrow).width(arrowWidth).height(arrowWidth).expandY();

        leftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentCharacter = (currentCharacter + GameMenuManager.NUMBEROFCHARACTERS - 1) % GameMenuManager.NUMBEROFCHARACTERS;
                updateCharacters(characterTables, characterTableWidth);
            }
        });

        rightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentCharacter = (currentCharacter + 1) % GameMenuManager.NUMBEROFCHARACTERS;
                updateCharacters(characterTables, characterTableWidth);
            }
        });

        baseTable.row().padTop(20).padBottom(10);

        ImageButton select = new ImageButton(gameMenuManager.generateTextureRegionDrawableObject("select"));
        baseTable.add(select).width(200).height(200 * select.getHeight() /select.getWidth()).expandX().colspan(5);

        select.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String texture = gameMenuManager.getCharacters()[currentCharacter];
                if (texture != null) {
                    mainCharacter.changeTexture(texture);
                    hide();
                }
            }
        });

        baseTable.setVisible(false);
        stage.addActor(baseTable);
    }

    /**
     * Updates the characters displayed on the pop up screen.
     *
     * @param characterTables Array of Tables for Images of character to add on.
     * @param characterTableWidth Width of each characterTable.
     */
    private void updateCharacters(Table characterTables[], float characterTableWidth) {
        for (int i = currentCharacter; i < currentCharacter + 3; i++) {
            Table characterTable = characterTables[i - currentCharacter];
            characterTable.clearChildren();
            Label characterName = new Label("CHARACTER\nNAME", skin, "white-label");
            characterName.setAlignment(Align.center);
            characterName.setFontScaleX(0.5f);
            characterName.setFontScaleY(0.5f);
            characterTable.add(characterName).top();
            characterTable.row();
            String texture = gameMenuManager.getCharacters()[(i + gameMenuManager.NUMBEROFCHARACTERS - 1) % gameMenuManager.NUMBEROFCHARACTERS];
            try {
                TextureRegionDrawable characterTexture = gameMenuManager.generateTextureRegionDrawableObject(texture);
                Image character = new Image(characterTexture);
                characterTable.add(character).expandY().width(characterTableWidth * 0.8f).height(characterTableWidth * 0.8f * character.getHeight() / character.getWidth());
            } catch (NullPointerException e) {
                characterTable.add().expandY();
            }
        }
    }
}

