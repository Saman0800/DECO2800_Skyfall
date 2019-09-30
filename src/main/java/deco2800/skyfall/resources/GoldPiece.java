package deco2800.skyfall.resources;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;


/**
 * The civilisation's currency is Gold Pieces valued as either 5G,
 * 10G, 50G or 100G
 */
public class GoldPiece extends StaticEntity {

    private static final String ENTITY_ID_STRING = "gold_piece";

    // the value of the piece of gold (either 5G,10G,50G or 100G)
    private int value;

    // all possible gold piece values in a list
    public static final List<Integer> goldValues = Arrays.asList(5,10,50,100);

    // have the value of the first gold piece be equal to 5
    private static int nextValue = 5;

    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(MainCharacter.class);


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
            logger.info("You don't have enough Metal");
            System.out.println("Invalid piece of gold");
        }
    }

    /**
     * Creates a gold piece with a specific title, whether or not it is
     * obstructed and its value
     * @param tile the entity's ID (gold piece)
     * @param obstructed true or false depending on whether or not the object
     *                   is obstructed
     */
    public GoldPiece(Tile tile, boolean obstructed) {
        super(tile, 2, "goldPiece" + GoldPiece.nextValue, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        this.value = GoldPiece.nextValue;

        // generate a random index between 0 and 3
        int index = (int)(Math.random()*4);

        // update the gold piece value using the randomly generated index
        GoldPiece.nextValue = goldValues.get(index);

        this.entityType = "GoldPiece";
        changeCollideability(false);

    }

    /**
     *
     * @param memento
     */
    public GoldPiece (StaticEntityMemento memento){
        super(memento);
    }

    /**
     * Override the method provided in AbstractEntity
     * @param i
     */
    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }

    /**
     * The newInstance method implemented for the GoldPiece class to allow for item
     * dispersal on game start up.
     *
     * @return Duplicate goldPiece instance with modified position.
     */
    @Override
    public GoldPiece newInstance(Tile tile) {
        return new GoldPiece(tile, this.isObstructed());
    }
    /**
     * Returns the value of the piece of gold.
     * @return The gold piece's value (5G, 10G, 50G or 100G).
     */
    public Integer getValue(){
        return this.value;
    }





}
