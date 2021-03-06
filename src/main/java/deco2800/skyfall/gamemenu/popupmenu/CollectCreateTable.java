package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import deco2800.skyfall.buildings.BuildingType;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.QuestManager;
import deco2800.skyfall.managers.TextureManager;
import java.util.List;


/**
 * Doubles for the both the Collect Button and Create Button
 */
public class CollectCreateTable extends AbstractPopUpElement {

    private final String type;
    private final QuestManager qm;
    private Skin skin;
    private TextButton complete;
    private Table labelTable;

    private String collect = "collect";
    private static final String SWORD = "sword";
    private static final String AXE = "axe";
    private static final String SPEAR = "spear";
    private static final String BOW = "bow";
    private static final String WHITE_TEXT = "white-text";

    private Label labelGold;
    private Label labelMetal;
    private Label labelStone;
    private Label labelWood;
    private Label labelSword;
    private Label labelSpear;
    private Label labelAxe;
    private Label labelBow;

    public CollectCreateTable(Stage stage, ImageButton exit, String[] textureNames,
                              TextureManager tm, GameMenuManager gameMenuManager,
                              Skin skin, String type) {
        super(stage, exit, textureNames, tm, gameMenuManager);

        this.skin = skin;
        this.type = type;
        this.qm  = gameMenuManager.getQuestManager();
        complete = new TextButton("  COMPLETED!  ", skin);
        complete.getLabel().setStyle(skin.get("green-pill",
                Label.LabelStyle.class));
        complete.getLabel().getStyle().fontColor = Color.BLACK;

        labelTable = new Table();
        this.draw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        setComplete();
        updateText();
    }

    public void setComplete() {
        if (checkComplete()) {
            complete.setVisible(true);
        } else {
            complete.setVisible(false);
        }
    }

    /**
     * Updates the text of the widget
     */
    public void updateText() {
        labelTable.clear();
        if (type.equals(collect)) {
            populateItems();
            populateWeapons();
        } else {
            List<BuildingType> buildingsTotal = qm.getBuildingsTotal();

            for (BuildingType entry :  buildingsTotal) {
                String currentText  = String.format("1 x %s", entry.toString());
                labelTable.add(new Label(currentText, skin, WHITE_TEXT)).left();
                labelTable.row();
            }
        }
    }

    /**
     * Checks if all the resources have been collected
     * @return True if all resources have been collected
     */
    private boolean checkComplete() {
        if (type.equals("collect")) {
            return (qm.checkGold() && qm.checkMetal() &&
                    qm.checkStone() && qm.checkWood() &&
                    qm.checkWeapons(SWORD) && qm.checkWeapons(SPEAR) &&
                    qm.checkWeapons(AXE) && qm.checkWeapons(BOW));
        } else {
            return qm.checkBuildings() || qm.questFinished();
        }
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


        Label titleLabel;
        if (type.equals(collect)) {
            titleLabel = new Label(" COLLECT ", skin,  "title-pill");
        } else {
            titleLabel = new Label(" CREATE ", skin,  "title-pill");
        }

        baseTable.add(titleLabel);
        baseTable.row();
        baseTable.add(labelTable);
        baseTable.row();
        baseTable.add(complete).bottom().width(200).expand();
        baseTable.setVisible(false);
        stage.addActor(baseTable);

    }

    private void populateItems() {
        String whiteText = WHITE_TEXT;
        String format = "%d x %s";

        String currentText  = String.format(format, qm.getGoldTotal(), "Gold");
        Color color;

        // Gold label
        if (qm.checkGold() || qm.questFinished()) {
            color = Color.GREEN;
        } else {
            color = Color.WHITE;
        }
        labelGold = new Label(currentText, skin, whiteText);
        labelGold.setColor(color);
        labelTable.add(labelGold).left();
        labelTable.row();

        // Metal label
        if (qm.checkMetal() || qm.questFinished()) {
            color = Color.GREEN;
        } else {
            color = Color.WHITE;
        }
        currentText  = String.format(format, qm.getMetalTotal(), "Metal");
        labelMetal = new Label(currentText, skin, whiteText);
        labelMetal.setColor(color);
        labelTable.add(labelMetal).left();
        labelTable.row();

        // Stone label
        if (qm.checkStone() || qm.questFinished()) {
            color = Color.GREEN;
        } else {
            color = Color.WHITE;
        }
        currentText  = String.format(format, qm.getStoneTotal(), "Stone");
        labelStone = new Label(currentText, skin, whiteText);
        labelStone.setColor(color);
        labelTable.add(labelStone).left();
        labelTable.row();

        // Wood label
        if (qm.checkWood() || qm.questFinished()) {
            color = Color.GREEN;
        } else {
            color = Color.WHITE;
        }
        currentText  = String.format(format, qm.getWoodTotal(), "Wood");
        labelWood = new Label(currentText, skin, whiteText);
        labelWood.setColor(color);
        labelTable.add(labelWood).left();
        labelTable.row();


    }

    private void populateWeapons() {
        // Collect weapon labels

        String currentText;
        Color color;
        String whiteText = WHITE_TEXT;
        String format = "%d x %s";

        if ((qm.checkWeapons(SWORD) && qm.checkWeapons(SPEAR) &&
                qm.checkWeapons(AXE) && qm.checkWeapons(BOW)) || qm.questFinished()) {
            color = Color.GREEN;
        } else {
            color = Color.WHITE;
        }

        if (qm.getWeaponsTotal(SWORD) > 0) {
            currentText  = String.format(format, qm.getWeaponsTotal(SWORD),
                    "Sword");
            labelSword = new Label(currentText, skin, whiteText);
            labelSword.setColor(color);
            labelTable.add(labelSword).left();
            labelTable.row();
        }

        if (qm.getWeaponsTotal(SPEAR) > 0) {
            currentText  = String.format(format, qm.getWeaponsTotal(SPEAR),
                    "Spear");
            labelSpear = new Label(currentText, skin, whiteText);
            labelSpear.setColor(color);
            labelTable.add(labelSpear).left();
            labelTable.row();
        }

        if (qm.getWeaponsTotal(AXE) > 0) {
            currentText  = String.format(format, qm.getWeaponsTotal(AXE),
                    "Axe");
            labelAxe = new Label(currentText, skin, whiteText);
            labelAxe.setColor(color);
            labelTable.add(labelAxe).left();
            labelTable.row();
        }

        if (qm.getWeaponsTotal(BOW) > 0) {
            currentText  = String.format(format, qm.getWeaponsTotal(BOW),
                    "Bow");
            labelBow = new Label(currentText, skin, whiteText);
            labelBow.setColor(color);
            labelTable.add(labelBow).left();
            labelTable.row();
        }
    }


    public Label getLabelGold() {
        return labelGold;
    }

    public Label getLabelMetal() {
        return labelMetal;
    }

    public Label getLabelStone() {
        return labelStone;
    }

    public Label getLabelWood() {
        return labelWood;
    }

    public Label getLabelSword() {
        return labelSword;
    }

    public Label getLabelSpear() {
        return labelSpear;
    }

    public Label getLabelAxe() {
        return labelAxe;
    }

    public Label getLabelBow() {
        return labelBow;
    }

    public TextButton getComplete() {
        return complete;
    }
}
