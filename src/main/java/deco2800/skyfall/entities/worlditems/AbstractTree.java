package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import java.util.Map;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTree extends StaticEntity implements Tickable {

    protected static final Logger LOG = LoggerFactory.getLogger(AbstractTree.class);
    protected int woodAmount; // amount of wood that each tree has

    protected static Random randomGen = new Random();
    protected static int nextTreeTexture = 1;
    protected static final String ENTITY_ID_STRING = "abstract_tree";

    public AbstractTree(SaveableEntityMemento memento) {
        super(memento);
    }

    public AbstractTree() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
    }

    public AbstractTree(float col, float row, int renderOrder, Map<HexVector, String> texture) {
        super(col, row, renderOrder, texture);
    }

    public AbstractTree(Tile tile, boolean obstructed, String image) {
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
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof AbstractTree)) {
            return false;
        }

        AbstractTree otherTree = (AbstractTree) other;
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

        if (!isObstructed()) {
            result *= -1;
        }

        return (int) result;
    }

    public int hashCode() {
        return hashCode(4733);
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