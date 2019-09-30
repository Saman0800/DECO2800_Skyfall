package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.entities.Harvestable;
import deco2800.skyfall.entities.WoodCube;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import java.util.Map;

public abstract class StaticTree extends StaticEntity implements Tickable, Harvestable {

    protected static final Logger LOG = LoggerFactory.getLogger(Tree.class);
    protected int woodAmount; // amount of wood that each tree has

    protected static Random randomGen = new Random();
    protected static int nextTreeTexture = 1;
    protected static final String ENTITY_ID_STRING = "abstract_tree";

    public StaticTree(SaveableEntityMemento memento) {
        super(memento);
    }

    public StaticTree() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public StaticTree(float col, float row, int renderOrder, Map<HexVector, String> texture) {
        super(col, row, renderOrder, texture);
    }

    public StaticTree(Tile tile, boolean obstructed, String image) {
        super(tile, 5, image, obstructed);
    }

    /**
     * Animates the trees on every game tick
     *
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        // Do nothing on tick
    }

    @Override
    public List<AbstractEntity> harvest(Tile tile) {
        return harvest(tile, this.woodAmount);
    }

    public List<AbstractEntity> harvest(Tile tile, int maxWoodCount) {
        Random random = new Random();
        int dropCount = random.nextInt(maxWoodCount);
        List<AbstractEntity> drops = new ArrayList<>();
        for (int i = 0; i < dropCount; i++) {
            drops.add(new WoodCube(getCol(), getRow()));
        }

        return drops;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        Tree otherTree = (Tree) other;
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
    public int hashCode(int treePrime) {
        final float prime = 31;
        float result = 1;
        result = (result + super.getCol()) * prime;
        result = (result + super.getRow()) * prime;
        result = (result + super.getHeight()) * prime;
        return (int) result;
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