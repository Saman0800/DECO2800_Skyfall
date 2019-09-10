package deco2800.skyfall.resources;

import deco2800.skyfall.entities.AbstractEntity;

/**
 * The civilisation's currency is Gold Pieces valued as either 5G,
 * 10G, 50G or 100G
 */
public class GoldPiece extends AbstractEntity {

    // the value of the piece of gold (either 5G,10G,50G or 100G)
    public int value;

    /**
     * Creates a gold piece with a particular value.
     * @param value The value of the piece of gold, either 5G, 10G, 50G or 100G.
     */
    public GoldPiece(int value){
        // if the GoldPiece is of value of 5,10,50 or 100
        if (value == 5 || value == 10 || value == 50 || value == 100){
            this.value = value;
        } else {
            System.out.println("Invalid piece of gold");
        }

        changeCollideability(false);
    }
    /**
     * Returns the value of the piece of gold.
     * @return The gold piece's value (5G, 10G, 50G or 100G).
     */
    public Integer getValue(){
        return this.value;
    }

    /**
     * Override the method provided in AbstractEntity
     * @param i
     */
    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }
}
