package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

import java.util.Map;

public class ForestTree extends AbstractTree {

    protected static final String ENTITY_ID_STRING = "forest_tree";

    public ForestTree(float col, float row, int renderOrder, Map<HexVector, String> texture) {
        super(col, row, renderOrder, texture);
        this.woodAmount = 15;
    }

    public ForestTree(Tile tile, boolean obstructed) {
        super(tile, obstructed, "tree" + nextTreeTexture);
        this.woodAmount = 15;

        ForestTree.nextTreeTexture = randomGen.nextInt(3) + 1;
        this.entityType = "ForestTree";
    }

    public ForestTree(SaveableEntityMemento memento) {
        super(memento);
        this.woodAmount = 15;
    }

    /**
     * The newInstance method implemented for the ForestTree class to allow for item
     * dispersal on game start up. This function is implemented with the
     * 
     * 
     * @return Duplicate rock tile with modified position.
     */
    @Override
    public ForestTree newInstance(Tile tile) {
        return new ForestTree(tile, this.isObstructed());
    }

    @Override
    public boolean equals(Object other) {

        if (!(other instanceof ForestTree)) {
            return false;
        }

        return super.equals(other);
    }

    /**
     * Gets the hashCode of the tree
     *
     * @return the hashCode of the tree
     */
    @Override
    public int hashCode() {
        return super.hashCode(31);
    }
}