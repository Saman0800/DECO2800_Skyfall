package deco2800.skyfall.entities.structures;

import java.util.TreeMap;

public class TownCentreBuilding extends AbstractBuilding {

    private int maxHealth = 80;
    private int currentHealth;

    public TownCentreBuilding(float x, float y) {
        super(x, y);
        this.currentHealth = maxHealth;
        this.setTexture("buildingA");

        int constructionTime = 6;
        int xSize = 3;
        int ySize = 3;

        TreeMap<String, Integer> constructionCost = new TreeMap<String, Integer>();
    }

    @Override
    public void onTick(long i) {}
}
