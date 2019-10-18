package deco2800.skyfall.entities;

import deco2800.skyfall.animation.AnimationRole;

public abstract class VehicleEntity extends Peon {

    private int health;

    private boolean beAttacked = false;

    private boolean available = true;

    protected MainCharacter mc;

    protected static String CHARACTER;

    public VehicleEntity(float col, float row) {
        this.setRow(row);
        this.setCol(col);
    }

    public VehicleEntity(float row, float col, String textureName, int health) {
        super(row, col, 0, textureName, health);
        this.setTexture(textureName);
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        if (mc != null) {
            float colDistance = mc.getCol() - this.getCol();
            float rowDistance = mc.getRow() - this.getRow();

            if ((colDistance * colDistance + rowDistance * rowDistance) < 4) {

                setTexture(CHARACTER);
                setObjectName(CHARACTER);

            } else {
                this.setCurrentState(AnimationRole.NULL);
            }
        }

    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBeAttecked(boolean attacked) {
        this.beAttacked = attacked;
    }

    public boolean beAttacked() {
        return this.beAttacked;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}
