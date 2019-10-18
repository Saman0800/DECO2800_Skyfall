package deco2800.skyfall.entities;

import deco2800.skyfall.animation.AnimationRole;

public class Camel extends VehicleEntity {
    private static final String BIOME = "desert";
    private boolean available = true;
    private boolean moving = false;
    private static final String VEHICLE = "camel";
    private static final int HEALTH = 10;

    public Camel(float col, float row, MainCharacter mc) {
        super(col, row);
        CHARACTER = "camel_character";
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
