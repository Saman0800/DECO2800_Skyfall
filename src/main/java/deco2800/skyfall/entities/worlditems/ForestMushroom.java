package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.graphics.HasPointLight;
import deco2800.skyfall.graphics.types.Vec2;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.graphics.types.*;
import deco2800.skyfall.graphics.*;
import java.util.Random;

public class ForestMushroom extends StaticEntity implements HasPointLight {

    private static final String ENTITY_ID_STRING = "forest_mushrooms";
    private static Random randomGen = new Random();
    private static int nextImage = 1;
    PointLight entityPointLight;

    public ForestMushroom() {
        setupParams();
        this.setTexture("mushrooms" + nextImage);

    }

    public ForestMushroom(Tile tile, boolean obstructed) {
        super(tile, 2, "mushrooms" + nextImage, obstructed);

        // Set up the point light for this entity
        pointLightSetUp();
        setupParams();
    }

    public ForestMushroom(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    @Override
    public void onTick(long i) {

    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "ForestMushroom";
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

    @Override
    public void pointLightSetUp() {
        int[] entityCoord = getRenderCentre();
        this.entityPointLight = new PointLight(new Vec2(entityCoord[0], entityCoord[1]), new Vec3(0.17f, 0.98f, 0.31f),
                3.0f, 5.0f);
    }

    @Override
    public void updatePointLight() {
        // All the values will always stay constant for the mushrooms.
        return;
    }

    @Override
    public PointLight getPointLight() {
        return this.entityPointLight;
    }
}