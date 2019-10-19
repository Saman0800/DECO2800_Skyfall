package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;

import java.util.Random;

public class Bone extends StaticEntity {

    protected static final Random randomGen = new Random(2);
    private static final String STRINGTEXTURE1 = "DSkull";
    private static final String STRINGTEXTURE2 = "DRibs";
    private static String nextTextureString = STRINGTEXTURE1;

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
    }

    public Bone(Tile tile, boolean obstructed) {
        super(tile, 2, Bone.nextTextureString, obstructed);
        setBoneParams();

    }

    private void setBoneParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "Bone";
    }

    /**
     * Gets the appropriate textrue based off the input value.
     * 
     * @param textureNum The number corresponding to the texture type.
     * @return The string of texture corresponding the input value.
     */
    public static String getNextTexture(int textureNum) {
        return textureNum == 0 ? STRINGTEXTURE1 : STRINGTEXTURE2;
    }
}