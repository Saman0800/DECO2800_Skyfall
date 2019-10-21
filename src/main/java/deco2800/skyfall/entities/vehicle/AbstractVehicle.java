package deco2800.skyfall.entities.vehicle;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Peon;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;

public abstract class AbstractVehicle extends Peon implements ICombatEntity, Animatable, Item {

    private int health;
    private boolean isTaken;
    private String name;
    private boolean isHurt = false;

    public AbstractVehicle(float col, float row, String texturename) {
        super(row, col, 0.2f, texturename, 10);
        this.name = texturename;
        this.isTaken = false;
        this.setHealth(health);
        this.setTexture(texturename);
    }

    public String getName() {
        return this.name;
    }

    /**
     * Get the health of the vehicle
     *
     * @return health of pet
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setting the health to a specific value
     *
     * @param health the amount of health being sett.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    public boolean underUsing() {
        return isTaken;
    }

    // Animatable interface methods
    @Override
    public void configureAnimations() {
        // No configure animations yet.
    }

    @Override
    public void setDirectionTextures() {
        // No Direction Textures set yet.
    }

    // Item interface methods
    @Override
    public String getSubtype() {
        return "vehicle";
    }

    @Override
    public boolean isCarryable() {
        return true;
    }

    @Override
    public boolean isEquippable() {
        return true;
    }

    @Override
    public HexVector getCoords() {
        return this.position;
    }

    @Override
    public boolean isExchangeable() {
        return true;
    }

    @Override
    public void use(HexVector position) {
        // not yet implemented.
    }

    // ICombatEntity interface methods
    @Override
    public void dealDamage(MainCharacter mc) {
        // no damage dealt.
    }

    @Override
    public boolean canDealDamage() {
        return false;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public void takeDamage(int damage) {
        setGetHurt(true);
        health -= damage;
    }

    public boolean getHurt() {
        return isHurt;
    }

    public void setGetHurt(boolean isHurt) {
        this.isHurt = isHurt;
    }

    @Override
    public int[] getResistanceAttributes() {
        return new int[0];
    }

}
