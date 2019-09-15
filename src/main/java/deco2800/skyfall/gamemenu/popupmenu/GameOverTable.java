package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.SkyfallGame;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.mainmenu.MainMenuScreen;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.resources.ManufacturedResources;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static deco2800.skyfall.managers.GameMenuManager.generateTextureRegionDrawableObject;

public class GameOverTable extends AbstractPopUpElement{
    private Skin skin;
    private Table gameOverTable;
    ManufacturedResources selectedItem = null;
    InventoryTable inventoryTable;
    GameMenuManager gameMenuManager;


    public GameOverTable(Stage stage, ImageButton exit,
                         String[] textureNames, TextureManager tm,
                         GameMenuManager gameMenuManager, Skin skin) {
        super(stage, exit, textureNames,tm , gameMenuManager);
        this.skin = skin;
        this.gameMenuManager = gameMenuManager;
        this.draw();
    }

    @Override
    public void hide() {
        super.hide();
//        System.out.println("Hiding gameover table");
        gameOverTable.setVisible(false);
    }

    @Override
    public void show() {
        super.show();
//        System.out.println("Showing gameover table");
        gameOverTable.setVisible(true);
    }

    @Override
    public void updatePosition() {
        super.updatePosition();
    }

    @Override
    public void draw() {
        super.draw();
//        System.out.println("Drawing GAMEOVERTABLE");
        gameOverTable = new Table();
        gameOverTable.top();
//        buildingTable.setDebug(true);
        gameOverTable.setSize(500, 500 * 1346 / 1862f);
        gameOverTable.setPosition(Gdx.graphics.getWidth()/2f - gameOverTable.getWidth()/2,
                (Gdx.graphics.getHeight() + 160) / 2f - gameOverTable.getHeight()/2);
        gameOverTable.setBackground(generateTextureRegionDrawableObject("pop up screen"));

        Image gameOver = new Image(generateTextureRegionDrawableObject("build"));
        gameOverTable.add(gameOver).width(125).height(125).padTop(-30);
        gameOverTable.row();

        Label gameOverText = new Label("GAME OVER", skin, "white-text");
        gameOverTable.add(gameOverText).expandY();
        gameOverTable.row();

        TextButton retry = new TextButton("RETRY", skin, "game");
        gameOverTable.add(retry).padBottom(15).width(450);
        gameOverTable.row();

        retry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                gameMenuManager.getMainCharacter().changeHealth(10);
            }
        });

        TextButton toHome = new TextButton("HOME", skin, "game");
        gameOverTable.add(toHome).padBottom(15).width(450);
        gameOverTable.row();

//        toHome.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                hide();
//                SkyfallGame newGame = new SkyfallGame();
//                newGame.create();
//            }
//        });

        gameOverTable.setVisible(false);
        stage.addActor(gameOverTable);
    }

}



