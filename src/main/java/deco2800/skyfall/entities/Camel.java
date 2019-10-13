package deco2800.skyfall.entities;

import deco2800.skyfall.animation.AnimationRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Camel extends VehicleEntity {
    private static final String BIOME = "desert";
    private MainCharacter mc;
    private boolean available = true;
    private boolean moving = false;
    private static final String VEHICLE = "camel";
    private static final String CHARACTER = "camel_character";
    private static final int HEALTH = 10;

    private final Logger logger = LoggerFactory.getLogger(Camel.class);

    public Camel(float col, float row, MainCharacter mc) {
        super(col, row);
        this.mc = mc;
        this.setTexture(VEHICLE);
        this.setObjectName(VEHICLE);
        this.setHeight(1);
        this.setAvailable(available);
        this.setHealth(HEALTH);
    }

    public Camel(float col, float row) {
        super(col, row);
        this.setTexture(CHARACTER);
        this.setObjectName(CHARACTER);
        this.setHeight(1);
    }

    public Camel(float row, float col, String textureName, int damage) {
        super(row, col, textureName, damage);
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

            if ((colDistance * colDistance + rowDistance * rowDistance) < 4) {

                // Let main character get onto vehicle
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

    public boolean checkAvailable(String bomie) {
        if (!bomie.equals(BIOME)) {
            available = false;
        } else {
            available = true;
        }
        return available;
    }
}
