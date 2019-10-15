package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;


import java.util.ArrayList;

public class Tutorial extends AbstractUIElement {
    private final GameMenuManager gameMenuMan;
    private final GameManager gameMan;
    private String currentTut;
    private ArrayList<String> tutList = new ArrayList<>();
    private int count;

    public Tutorial(Stage s, GameMenuManager gameMenuMan, GameManager gameMan) {
        stage = s;
        this.gameMenuMan = gameMenuMan;
        this.gameMan = gameMan;
        this.count = 0;

        this.tutList.add("story");
        this.tutList.add("your_mission");
        this.tutList.add("tutorial_1");
        this.tutList.add("tutorial_2");
        this.tutList.add("tutorial_3");
        this.tutList.add("tutorial_4");
        this.tutList.add("tutorial_5");

        this.draw();
    }

    @Override
    public void updatePosition() {
        /*empty because of reasons*/
    }

    public String getTutPage(int x) {
        // increment some variable every time click next on the tutorial pages
        // have corresponding in the list to cycle through properly

        if ((x >= 0) && (x < 7)) {
            this.currentTut = tutList.get(x);
        } else {
            // Out of range
            // Maybe throw an IndexOutOfBounds error
        }
        return currentTut;
    }

    @Override
    public void draw() {
        String direction = "next_back_button"; // defined as a constant to reduce code smell
        // if in tutorial world then display
        if (gameMan.isTutorial) {
            // display the current tutorial page
            Image current = new Image(gameMenuMan.generateTextureRegionDrawableObject(getTutPage(count)));
            current.setFillParent(true);
            stage.addActor(current);

            // add next button if current is tutList[0-5]
            if ((0 <= count) && (count < 6)) {
                ImageButton next = new ImageButton(gameMenuMan.generateTextureRegionDrawableObject(direction));
                next.setPosition(1070, 10);

                stage.addActor(next);

                next.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        // If pressed, then increment
                        count += 1;
                        draw();
                    }
                });
            }

            // Add back button if current is tutList[1-6]
            if ((0 < count) && (count < 7)) {
                // Home button is larger than the next and back buttons. Use different button
                ImageButton back = new ImageButton(gameMenuMan.generateTextureRegionDrawableObject(direction));
                back.setPosition(10, 10);
                stage.addActor(back);

                back.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        // If pressed, then increment
                        count -= 1;
                        draw();
                    }
                });
            }

            // if current = tutorial5 then have toHome button not next button
            if ((count == 6)) {
                ImageButton home = new ImageButton(gameMenuMan.generateTextureRegionDrawableObject("home_button"));
                home.setPosition(1010, 10);
                stage.addActor(home);

                home.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        // If pressed, go to main menu screen
                        takeHome();
                        // NOT WORKING
                        // will go to home screen but music continues to play and when try to start game, it breaks
                    }
                });
            }
        }
    }

    public void takeHome() {
        ((Game) Gdx.app.getApplicationListener()).setScreen(gameMenuMan.getGame().getMainMenuScreen());
//        gameMenuMan.getGame().create();

    }
}
