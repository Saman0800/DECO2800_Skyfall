package deco2800.skyfall.entities.pets;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.worlds.Tile;

import java.util.ArrayList;
import java.util.List;

public class LizardHome extends AbstractPet implements Animatable {
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
        this.setHealth(10);
        this.setLevel(1);
        this.setSpeed(1);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
        this.setCurrentDirection(Direction.SOUTH);
    }

    @Override
    public void onTick(long i) {
        // Do nothing for now.
    }

    /**
     * Animation settings
     */
    @Override
    public void configureAnimations() {
        // Do nothing for now.
    }

    /**
     * Texture settings
     */
    @Override
    public void setDirectionTextures() {
        // Do nothing for now.
    }

    /**
     * cut this tree and mines 1 health from tree health
     */
    public void cutlizardHomeTree() {
        this.setHealth(this.getHealth() - 1);
    }

}
