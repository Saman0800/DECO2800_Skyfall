package deco2800.skyfall.buildings;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Blueprint;

public class MountainPortal extends AbstractPortal implements Blueprint {

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     */
    public MountainPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setTexture("portal_mountain");
        this.setNext("volcanic_mountains");
        this.entityType = "MountainPortal";
        this.name = "mountainPortal";
        this.currentBiome = "mountain";
        blueprintLearned = false;
    }

    public void unlocknext(MainCharacter character) {
        super.unlocknext(character, getNext());
    }

}
