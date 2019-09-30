package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.AbstractEntity;

import java.util.List;
import java.util.Map;

public class Tree extends StaticTree {

    protected static final String ENTITY_ID_STRING = "tree";

    public Tree(float col, float row, int renderOrder, Map<HexVector, String> texture) {
        super(col, row, renderOrder, texture);
        this.woodAmount = 15;
    }

    public Tree(Tile tile, boolean obstructed) {
        super(tile, obstructed, "tree" + nextTreeTexture);
        this.woodAmount = 15;

        Tree.nextTreeTexture = randomGen.nextInt(3) + 1;
        this.entityType = "Tree";
    }

    public Tree(SaveableEntityMemento memento) {
        super(memento);
        this.woodAmount = 15;
    }

    /**
     * The newInstance method implemented for the Tree class to allow for item
     * dispersal on game start up. This function is implemented with the
     * 
     * 
     * @return Duplicate rock tile with modified position.
     */
    @Override
    public Tree newInstance(Tile tile) {
        return new Tree(tile, this.isObstructed());
    }

    @Override
    public boolean equals(Object other) {

        if (!(other instanceof StaticTree)) {
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