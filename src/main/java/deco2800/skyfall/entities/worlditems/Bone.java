package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.HasHealth;

import java.util.Random;

public class Bone extends StaticEntity {

    protected static Random randomGen = new Random(2);
    private static String nextTextureString = "DSkull";

    protected static final String ENTITY_ID_STRING = "bone";

    public Bone(SaveableEntityMemento memento) {
        super(memento);
        setBoneParams();
    }

    public Bone() {
        super();
        this.setObjectName(ENTITY_ID_STRING);
        setBoneParams();
        this.setTexture(Bone.nextTextureString);

        Bone.nextTextureString = randomGen.nextInt() == 0 ? "DSkull" : "DRibs";
    }

    public Bone(Tile tile, boolean obstructed) {
        super(tile, 2, Bone.nextTextureString, obstructed);
        setBoneParams();
        Bone.nextTextureString = randomGen.nextInt() == 0 ? "DSkull" : "DRibs";
    }

    private void setBoneParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "Bone";
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }
}