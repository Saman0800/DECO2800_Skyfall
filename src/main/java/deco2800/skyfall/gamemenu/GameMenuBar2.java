package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    //Current item selected in inventory user interface
    private String quickAccessSelected;

    public GameMenuBar2(Stage stage, String[] textureNames, TextureManager tm, Skin skin, GameMenuManager gmm) {
        super(stage, textureNames, tm);
        this.gmm = gmm;
        this.skin = skin;
        this.draw();
    }

    @Override
    public void updatePosition() {
        quickAccessPanel.setPosition(gmm.getTopRightX() - 170, gmm.getTopRightY() - 570);
        //t.setHeight(stage.getCamera().viewportHeight / 2);
        sideBar.setPosition(gmm.getTopRightX() - 180, gmm.getTopRightY() - 440);
        build.setPosition(gmm.getBottomRightX() - 170, gmm.getBottomRightY());
    }

    @Override
    public void draw() {
        setQuickAccessPanel();

        build = new ImageButton(generateTextureRegionDrawableObject("build"));
        build.setSize(150, 295f / 316 * 150);
        build.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened(gmm);
                gmm.setPopUp("buildingTable");
            }
        });

        stage.addActor(build);
    }

    public void setQuickAccessPanel(){
        quickAccessPanel = new Table().top().left();
        quickAccessPanel.setBackground(GameMenuManager.generateTextureRegionDrawableObject("quickaccess_bg"));
        quickAccessPanel.setSize(150, 490);
        setQuickAccessItems();
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = generateTextureRegionDrawableObject("quickaccess_button_bg");
        buttonStyle.down = generateTextureRegionDrawableObject("quickaccess_button_bg");
        buttonStyle.font = skin.getFont("game-font");
        buttonStyle.fontColor = skin.getColor("navy");

//        TextButton equip = new TextButton("EQUIP", buttonStyle);
//        equip.getLabel().setFontScale(0.8f);
        ImageButton equip = new ImageButton(generateTextureRegionDrawableObject("equip_qa"));
        quickAccessPanel.add(equip).width(130).height(50).padTop(5).padLeft(10);
        quickAccessPanel.row();
        ImageButton remove = new ImageButton(generateTextureRegionDrawableObject("remove_qa"));
//        TextButton remove = new TextButton("REMOVE", buttonStyle);
//        remove.getLabel().setFontScale(0.8f);
        quickAccessPanel.add(remove).width(130).height(50).padTop(5).padLeft(10);

        sideBar = new ImageButton(generateTextureRegionDrawableObject("quickaccess_side_bar"));
        sideBar.setSize(35, 360); //TODO: reduce chunkiness
        sideBar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideOpened(gmm);
                gmm.setPopUp("inventoryTable");
            }
        });

        stage.addActor(quickAccessPanel);
        stage.addActor(sideBar);
    }

    public void removeQuickAccessPanel(){
        quickAccessPanel.remove();
        sideBar.remove();
    }

    /**
     * Sets the items in the quick access inventory
     */
    public void setQuickAccessItems(){
        Map<String, Integer> quickAccess = gmm.getInventory().getQuickAccess();

        int size = 80;

        String[] weapons = {"axe", "box", "spear", "sword"};

        float sideBarWidth = 35;

        for (Map.Entry<String, Integer> entry : quickAccess.entrySet()) {
            Image selected = new Image(generateTextureRegionDrawableObject("selected"));
            selected.setName(entry.getKey() + "-qaSelected");
            selected.setSize((float) size + 15, (float) size + 15);
            selected.setPosition((float)-7.5, (float)-7.5);
            selected.setVisible(false);

            String weaponName = entry.getKey();
            for (String weapon : weapons) {
                if (weapon.equals(entry.getKey())) {
                    weaponName = entry.getKey() + "_tex";
                }
            }
            Table iconCell = new Table();
            iconCell.setName("iconCell");
            ImageButton icon = new ImageButton(generateTextureRegionDrawableObject(weaponName + "_inv"));
            icon.setName(entry.getKey());

            icon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if(quickAccessSelected != icon.getName()){
                        quickAccessSelected = icon.getName();
                    }else{
                        quickAccessSelected = null;
                    }

                    Actor selected = stage.getRoot().findActor(icon.getName() + "-qaSelected");

                    if(selected.isVisible()){
                        selected.setVisible(false);
                        //setButtonsActive(false);

                    }else{
                        for(Actor actor: quickAccessPanel.getChildren()){
                            if(actor.getName() != null && actor.getName().equals("iconCell") && actor instanceof Table){
                                Table iconCell = (Table) actor;

                                for(Actor iconActor: iconCell.getChildren()){
                                    String name = iconActor.getName();
                                    if(name != null && name.contains("-qaSelected")){
                                        iconActor.setVisible(false);
                                    }
                                }
                            }
                        }

                        selected.setVisible(true);

                        //setButtonsActive(true);

                    }
                }
            });

            icon.setSize(size, size);
            iconCell.addActor(selected);
            iconCell.addActor(icon);
            quickAccessPanel.add(iconCell).width(size).height(size).padTop(10).padLeft(20 + sideBarWidth/2);
            Label num = new Label(entry.getValue().toString(), skin, "white-label");
            num.setFontScale(0.4f);
            quickAccessPanel.add(num).top().left().padLeft(-20).padTop(5);
            quickAccessPanel.row();
        }
    }

}
