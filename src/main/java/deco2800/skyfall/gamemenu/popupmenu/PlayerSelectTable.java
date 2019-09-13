package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class PlayerSelectTable extends AbstractPopUpElement{

    private Skin skin;
    private Table playerSelectTable;
    private int currentCharacter;
    private MainCharacter mainCharacter;



    public PlayerSelectTable(Stage stage, ImageButton exit,
                      String[] textureNames, TextureManager tm,
                      GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.skin = skin;
        this.draw();
        mainCharacter = gameMenuManager.getMainCharacter();
    }

    @Override
    public void hide() {
        super.hide();
        System.out.println("Hiding player select table");
        playerSelectTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
        System.out.println("Showing player select table");
        playerSelectTable.setVisible(true);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    @Override
    public void draw() {
        super.draw();
        System.out.println("Drawing PLAYERSELECTTABLE");
        playerSelectTable = new Table();
//        playerSelectTable.setDebug(true);
        playerSelectTable.setSize(600, 600 * 1346 / 1862f);
        playerSelectTable.setPosition(Gdx.graphics.getWidth()/2f - playerSelectTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - playerSelectTable.getHeight()/2);
        playerSelectTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));

        Table infoBar = new Table();
        infoBar.setBackground(generateTextureRegionDrawableObject("game menu bar"));

        Label text = new Label("PLAYER SELECT", skin, "black-text");
        infoBar.add(text);

        playerSelectTable.add(infoBar).width(550).height(550f * 180 / 1756).padTop(20).colspan(5).padBottom(20);
        playerSelectTable.row();

        int arrowWidth = 60;

        //height = width
        ImageButton leftArrow = new ImageButton(generateTextureRegionDrawableObject("left_arrow"));

        playerSelectTable.add(leftArrow).width(arrowWidth).height(arrowWidth).expandY();

        Table[] characterTables = new Table[3];

        for (int i = 0; i < 3; i++) {
            Table characterTable = new Table();
//            characterTable.setDebug(true);
            characterTables[i] = characterTable;
        }

        float characterTableWidth = (550 - arrowWidth * 2) / 3f;

        updateCharacters(characterTables, characterTableWidth);

        for (int i = 0; i < 3; i++) {
            playerSelectTable.add(characterTables[i]).width(characterTableWidth).height(600f * 1346 / 1862 - 550f * 180 / 1756 - 40 - 30 - 200f*138/478).spaceLeft(5).spaceRight(5);
        }

        ImageButton rightArrow = new ImageButton(generateTextureRegionDrawableObject("right_arrow"));
        playerSelectTable.add(rightArrow).width(arrowWidth).height(arrowWidth).expandY();

        leftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentCharacter = (currentCharacter + gameMenuManager.NUMBEROFCHARACTERS - 1) % gameMenuManager.NUMBEROFCHARACTERS;
                updateCharacters(characterTables, characterTableWidth);
            }
        });

        rightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentCharacter = (currentCharacter + 1) % gameMenuManager.NUMBEROFCHARACTERS;
                updateCharacters(characterTables, characterTableWidth);
            }
        });

        playerSelectTable.row().padTop(20).padBottom(10);

        ImageButton select = new ImageButton(generateTextureRegionDrawableObject("select"));
        playerSelectTable.add(select).width(200).height(200 * select.getHeight() /select.getWidth()).expandX().colspan(5);

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

        playerSelectTable.setVisible(false);
        stage.addActor(playerSelectTable);
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
                TextureRegionDrawable characterTexture = generateTextureRegionDrawableObject(texture);
                Image character = new Image(characterTexture);
                characterTable.add(character).expandY().width(characterTableWidth * 0.8f).height(characterTableWidth * 0.8f * character.getHeight() / character.getWidth());
            } catch (NullPointerException e) {
                characterTable.add().expandY();
                //DO NOTHING
            }
        }
    }
}

