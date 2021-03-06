package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class MountainTree extends AbstractTree {

    protected static final String ENTITY_ID_STRING = "mountain_tree";

    public MountainTree(Tile tile, boolean obstructed) {
        super(tile, obstructed, "MTree" + MountainTree.nextTreeTexture);
        MountainTree.nextTreeTexture = randomGen.nextInt(3) + 1;
        setupParams();
    }

    public MountainTree(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.woodAmount = 15;
        this.entityType = "MountainTree";
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