package deco2800.skyfall.entities;

import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Horse extends VehicleEntity {

    private static final String BIOME = "forest";
    private MainCharacter mc;
    private boolean available = true;
    private boolean moving = false;
    private static final String VEHICLE = "horse_images";
    private static final String CHARACTER = "horse_character";
    private static final int HEALTH = 10;

    private final Logger logger =
            LoggerFactory.getLogger(Horse.class);

    public Horse(float col, float row, MainCharacter mc) {
        super(col,row);
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

    @Override
    public void onTick(long i) {
        super.onTick(i);
        if (mc != null) {
            float colDistance = mc.getCol() - this.getCol();
            float rowDistance = mc.getRow() - this.getRow();

            if ((colDistance * colDistance + rowDistance * rowDistance) < 4){


                setTexture(CHARACTER);
                setObjectName(CHARACTER);


            } else {
                this.setCurrentState(AnimationRole.NULL);
            }
        } else {
            logger.info("Main Character is null");
        }

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
                new AnimationLinker("tigerFront",
                        AnimationRole.MOVE, Direction.DEFAULT, true, true));
    }

    public void setDirectionTextures() {


    }
}
