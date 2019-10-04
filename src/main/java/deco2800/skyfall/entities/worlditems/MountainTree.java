package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.WoodCube;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

import java.util.Map;

public class MountainTree extends AbstractTree {

    protected static final String ENTITY_ID_STRING = "mountain tree";

    public MountainTree(float col, float row, int renderOrder, Map<HexVector, String> texture) {
        super(col, row, renderOrder, texture);
        this.woodAmount = 15;
    }

    public MountainTree(Tile tile, boolean obstructed) {
        super(tile, obstructed, "MTree" + MountainTree.nextTreeTexture);
        MountainTree.nextTreeTexture = randomGen.nextInt(3) + 1;
        this.woodAmount = 15;
        this.entityType = "MountainTree";
    }

    public MountainTree(SaveableEntityMemento memento) {
        super(memento);
        this.woodAmount = 15;
    }

    /**
     * The newInstance method implemented for the MountainTree class to allow for
     * item dispersal on game start up. This function is implemented with the
     * 
     * 
     * @return Duplicate rock tile with modified position.
     */
    @Override
    public MountainTree newInstance(Tile tile) {
        return new MountainTree(tile, this.isObstructed());
    }

    @Override
    public boolean equals(Object other) {

        if (!(other instanceof MountainTree)) {
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
        return super.hashCode(17);
    }
}