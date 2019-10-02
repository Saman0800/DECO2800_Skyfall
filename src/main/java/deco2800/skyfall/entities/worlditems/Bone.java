package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.HasHealth;

import java.util.Random;

public class Bone extends StaticEntity {

    protected static Random randomGen = new Random();
    private static String nextTextureString = "DSkull";

    protected static final String ENTITY_ID_STRING = "bone";

    public Bone(SaveableEntityMemento memento) {
        super(memento);
    }

    public Bone() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public Bone(Tile tile, boolean obstructed) {
        super(tile, 2, Bone.nextTextureString, obstructed);
        this.setObjectName(ENTITY_ID_STRING);

        Bone.nextTextureString = randomGen.nextInt(2) == 0 ? "DSkull" : "DRibs";

        this.entityType = "Bone";
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }
}