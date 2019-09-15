package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import java.util.Random;

public class ForestMushroom extends StaticEntity {

    private static final String ENTITY_ID_STRING = "forest_mushrooms";
    private static Random randomGen = new Random();
    private static int nextImage = 1;

    public ForestMushroom() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public ForestMushroom(Tile tile, boolean obstructed) {
        super(tile, 2, "mushrooms" + nextImage, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        nextImage = randomGen.nextInt(2) + 1;
        this.entityType = "ForestMushroom";
    }


    public ForestMushroom (StaticEntityMemento memento){
        super(memento);
    }

    @Override
    public void onTick(long i) {

    }

    /**
     * The newInstance method implemented for the ForestMushroom class to allow for
     * item dispersal on game start up.
     * 
     * @return Duplicate grass instance with modified position.
     */
    @Override
    public ForestMushroom newInstance(Tile tile) {
        return new ForestMushroom(tile, this.isObstructed());
    }

}