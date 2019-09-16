package deco2800.skyfall.entities.pets;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.Harvestable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.worlds.Tile;

import java.util.ArrayList;
import java.util.List;

public class LizardHome extends AbstractPet implements Animatable, Harvestable {
    MainCharacter mc;

    /**
     * Generate a lizard with a default settings
     *
     * @param col coordinate x
     * @param row coordinate y
     * @param mc  main chanracter
     */
    public LizardHome(float col, float row, MainCharacter mc) {
        super(col, row);
        this.setTexture("lizardHome");
        this.setObjectName("lizardHome");
        this.setHeight(1);
        this.setHealth(10);
        this.setLevel(1);
        this.setSpeed(1);
        this.setArmour(1);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
        this.setCurrentDirection(Direction.SOUTH);
        this.setCurrentState(AnimationRole.NULL);
    }

    @Override
    public void onTick(long i) {
    }

    /**
     * Animation settings
     */
    @Override
    public void configureAnimations() {

    }

    /**
     * Texture settings
     */
    @Override
    public void setDirectionTextures() {


    }


    /**
     * cut this tree and mines 1 health from tree health
     */
    public void cutlizardHomeTree() {
        this.setHealth(this.getHealth() - 1);
    }

    /**
     * when tree is harvested then drop a lizard
     *
     * @param tile The tile which was harvested.
     * @return a pet
     */
    @Override
    public List<AbstractEntity> harvest(Tile tile) {
        List<AbstractEntity> abstractEntityList = new ArrayList<>();
        Lizard lizard = new Lizard(0, 4, mc);
        lizard.setDomesticated(false);
        abstractEntityList.add(lizard);
        return abstractEntityList;
    }
}
