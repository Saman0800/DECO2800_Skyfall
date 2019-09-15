package deco2800.skyfall.resources;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.util.HexVector;

/**
 * The civilisation's currency is Gold Pieces valued as either 5G,
 * 10G, 50G or 100G
 */
public class GoldPiece extends AbstractEntity implements Item{

    // the value of the piece of gold (either 5G,10G,50G or 100G)
    public int value;

    /**
     * Create a gold piece with a random value at col,row
     * @param col coordinate x
     * @param row coordinate y
     */
    public GoldPiece(float col, float row) {
        super(col, row, 2);
        this.setTexture("gold");
        this.setHeight(1);
        this.setObjectName("gold");
        value=(int)(Math.random()*10)+1;
    }

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

    @Override
    public String getName() {
        return getObjectName();
    }

    @Override
    public String getSubtype() {
        return getName();
    }

    @Override
    public Boolean isCarryable() {
        return true;
    }

    @Override
    public HexVector getCoords() {

        return new HexVector(this.getCol(),this.getRow());
    }

    @Override
    public Boolean isExchangeable() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Sky fall world currency";
    }
}
