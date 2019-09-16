package deco2800.skyfall.entities;

import deco2800.skyfall.util.HexVector;

import java.util.Random;

/**
 * Wood material entity dropped by harvesting a tree.
 *
 * When on the ground it wiggles around randomly.
 */
public class WoodCube extends AbstractEntity implements Collectable {

    // used for finding a target location for the cube to randomly wiggle towards
    private static final int RANGE = 100;

    // how violently should the cube wiggle
    private static final float VELOCITY = 0.01f;

    public WoodCube(float col, float row) {
        super(col, row, 2);
        this.setTexture("woodcube");
        this.setHeight(1);
        this.setObjectName("WoodCube");
    }


    public void onTick(long i) {
        // a little wiggle to disperse the cubes
        // this would ideally be replaced with slowly moving up and down as
        // most games do
        Random random = new Random();
        int targetX = random.nextInt(RANGE) - (RANGE / 2);
        int targetY = random.nextInt(RANGE) - (RANGE / 2);
        HexVector newPosition = new HexVector(targetX, targetY);
        position.moveToward(newPosition, VELOCITY);
    }
}
