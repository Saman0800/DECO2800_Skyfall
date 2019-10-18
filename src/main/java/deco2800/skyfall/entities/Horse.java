package deco2800.skyfall.entities;

import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;

public class Horse extends VehicleEntity {

    private static final String BIOME = "forest";
    private boolean available = true;
    private boolean moving = false;
    private static final String VEHICLE = "horse_images";
    private static final int HEALTH = 10;

    public Horse(float col, float row, MainCharacter mc) {
        super(col, row);
        CHARACTER = "horse_character";
        this.mc = mc;
        this.setTexture(VEHICLE);
        this.setObjectName(VEHICLE);
        this.setHeight(1);
        this.setAvailable(available);
        this.setHealth(HEALTH);
        this.setDirectionTextures();
        this.configureAnimations();
    }

    public String getBiome() {
        return BIOME;
    }

    public String getVehicleType() {
        return VEHICLE;
    }

    public boolean getMove() {
        return this.moving;
    }

    /**
     * Sets the animations.
     */

    public void configureAnimations() {

        // Walk animation
        addAnimations(AnimationRole.MOVE, Direction.DEFAULT,
                new AnimationLinker("tigerFront", AnimationRole.MOVE, Direction.DEFAULT, true, true));
    }

    public void setDirectionTextures() {
        // Do nothing for the time being.
    }
}
