package deco2800.skyfall.entities.pets;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.entities.MainCharacter;

public class IceWhitebear extends AbstractPet implements Animatable {
    MainCharacter mc;

    public IceWhitebear(float col, float row, MainCharacter mc) {
        super(col, row);
        this.setTexture("icewhitebear");
        this.setObjectName("icewhitebear");
        this.setHealth(3);
        this.setLevel(1);
        this.setSpeed(1);
        this.mc = mc;
    }

    @Override
    public void onTick(long i) {
        // Do nothing for the time being.
    }

    @Override
    public void configureAnimations() {
        // Do nothing for the time being.
    }

    @Override
    public void setDirectionTextures() {
        // Do nothing for the time being.
    }

    public void destoryice() {
        this.setHealth(this.getHealth() - 1);
    }
}
