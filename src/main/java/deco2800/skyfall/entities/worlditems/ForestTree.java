package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class ForestTree extends AbstractTree {

    protected static final String ENTITY_ID_STRING = "forest_tree";

    public ForestTree(Tile tile, boolean obstructed) {
        super(tile, obstructed, "tree" + nextTreeTexture);
        setupParams();

        ForestTree.nextTreeTexture = randomGen.nextInt(3) + 1;
    }

    public ForestTree(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.woodAmount = 15;
        this.entityType = "ForestTree";
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