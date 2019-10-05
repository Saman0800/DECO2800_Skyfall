package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class SwampTree extends AbstractTree {

    protected static final String ENTITY_ID_STRING = "swamp_tree";

    public SwampTree(Tile tile, boolean obstructed) {
        super(tile, obstructed, "sTree" + nextTreeTexture);
        setupParams();

        SwampTree.nextTreeTexture = randomGen.nextInt(3) + 1;
    }

    public SwampTree(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.woodAmount = 15;
        this.entityType = "SwampTree";
    }

    /**
     * The newInstance method implemented for the SwampTree class to allow for item
     * dispersal on game start up. This function is implemented with the
     * 
     * 
     * @return Duplicate rock tile with modified position.
     */
    @Override
    public SwampTree newInstance(Tile tile) {
        return new SwampTree(tile, this.isObstructed());
    }

    @Override
    public boolean equals(Object other) {

        if (!(other instanceof SwampTree)) {
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
        return super.hashCode(29);
    }
}