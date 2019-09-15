package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.saving.AbstractMemento;
import deco2800.skyfall.saving.SaveException;
import deco2800.skyfall.saving.Saveable;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.Harvestable;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.WoodCube;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DesertCacti extends StaticEntity implements Tickable, Harvestable {
    private int woodAmount; // amount of wood that each tree has

    private static Random randomGen = new Random();
    private static int nextImage = 1;

    public DesertCacti(float col, float row, int renderOrder, Map<HexVector, String> texture) {
        super(col, row, renderOrder, texture);
        this.setTexture("");
        this.woodAmount = 3;
    }

    public DesertCacti(Tile tile, boolean obstructed) {
        super(tile, 5, "DCactus" + nextImage, obstructed);
        nextImage = randomGen.nextInt(4) + 1;
        this.woodAmount = 3;
    }

    /**
     * The newInstance method implemented for the DesertCacti class to allow for item
     * dispersal on game start up. This function is implemented with the
     * 
     * 
     * @return Duplicate rock tile with modified position.
     */
    @Override
    public DesertCacti newInstance(Tile tile) {
        return new DesertCacti(tile, this.isObstructed());
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof DesertCacti)) {
            return false;
        }
        DesertCacti otherTree = (DesertCacti) other;
        if (this.getCol() != otherTree.getCol() || this.getRow() != otherTree.getRow()) {
            return false;
        }

        return this.getHeight() == otherTree.getHeight();
    }

    /**
     * Gets the hashCode of the tree
     *
     * @return the hashCode of the tree
     */
    @Override
    public int hashCode() {
        final float prime = 101;
        float result = 1;
        result = (result + super.getCol()) * prime;
        result = (result + super.getRow()) * prime;
        result = (result + super.getHeight()) * prime;
        return (int) result;
    }

    /**
     * Animates the trees on every game tick
     *
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
    }

    @Override
    public List<AbstractEntity> harvest(Tile tile) {
        Random random = new Random();

        int dropCount = random.nextInt(15);
        List<AbstractEntity> drops = new ArrayList<>();
        for (int i = 0; i < dropCount; i++) {
            drops.add(new WoodCube(getCol(), getRow()));
        }

        return drops;
    }

    /***
     * A getter method to for woodAmount.
     *
     * @return woodAmount.
     */
    public int getWoodAmount() {
        return woodAmount;
    }

    /***
     * A method to decrease wood.
     */
    public void decreaseWoodAmount() {
        woodAmount--;
    }

}