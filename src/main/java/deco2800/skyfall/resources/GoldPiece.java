package deco2800.skyfall.resources;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;


/**
 * The civilisation's currency is Gold Pieces valued as either 5G,
 * 10G, 50G or 100G
 */
public class GoldPiece extends StaticEntity {

    private static final String ENTITY_ID_STRING = "gold_piece";


    // the value of the piece of gold (either 5G,10G,50G or 100G)
    public int value;



    /**
     * Creates a gold piece with a particular value.
     * @param value The value of the piece of gold, either 5G, 10G, 50G or 100G.
     */
    public GoldPiece(int value){
        this.setObjectName(ENTITY_ID_STRING);
        changeCollideability(false);

        // if the GoldPiece is of value of 5,10,50 or 100
        if (value == 5 || value == 10 || value == 50 || value == 100){
            this.value = value;
        } else {
            System.out.println("Invalid piece of gold");
        }
            }

    /**
     * Creates a gold piece with a specific title, whether or not it is
     * obstructed and its value
     * @param tile the entity's ID (gold piece)
     * @param obstructed true or false depending on whether or not the object
     *                   is obstructed
     * @param value the value of the gold piece being constructed
     */
    public GoldPiece(Tile tile, boolean obstructed, int value) {
        super(tile, 2, "goldPiece" + value, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        this.value = value;
        changeCollideability(false);
    }


    /**
     * The newInstance method implemented for the GoldPiece class to allow for item
     * dispersal on game start up.
     *
     * @return Duplicate goldPiece instance with modified position.
     */
    @Override
    public GoldPiece newInstance(Tile tile) {
        return new GoldPiece(tile, this.isObstructed(), value);
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
