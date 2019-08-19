package deco2800.skyfall.entities.structures;

public class TownCentreBuilding extends AbstractBuilding {

    private int maxHealth = 80;
    private int currentHealth;

    public TownCentreBuilding(float x, float y) {
        super(x, y);
        this.currentHealth = maxHealth;
    }

    @Override
    public void onTick(long i) {}
}
