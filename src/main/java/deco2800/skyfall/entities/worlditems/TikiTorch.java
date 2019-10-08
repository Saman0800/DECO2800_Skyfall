package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.graphics.HasPointLight;
import deco2800.skyfall.graphics.types.vec2;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.graphics.types.*;
import deco2800.skyfall.graphics.*;
import java.util.Random;

public class TikiTorch extends StaticEntity implements HasPointLight {

    private static final String ENTITY_ID_STRING = "tiki_torch";
    private static Random randomGen = new Random();
    private static EnvironmentManager environmentManager = GameManager.getManagerFromInstance(EnvironmentManager.class);
    PointLight entityPointLight;
    private float randomPointLightPeriod;

    public TikiTorch() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public TikiTorch(Tile tile, boolean obstructed) {
        super(tile, 2, "tikitorch", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        // Set up the point light for this entity
        randomPointLightPeriod = randomGen.nextFloat() * 150 + 100;
        pointLightSetUp();
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }

    /**
     * The newInstance method implemented for the ForestMushroom class to allow for
     * item dispersal on game start up.
     * 
     * @return Duplicate grass instance with modified position.
     */
    @Override
    public TikiTorch newInstance(Tile tile) {
        return new TikiTorch(tile, this.isObstructed());
    }

    @Override
    public void pointLightSetUp() {
        int[] entityCoord = getRenderCentre();
        float gametime = environmentManager.getHourDecimal();
        float kValue = (float) Math.sin(randomPointLightPeriod * gametime) * 0.15f + 1.2f;
        this.entityPointLight = new PointLight(new vec2(entityCoord[0], entityCoord[1]),
                new vec3(1.0f, 0.729f, 0.3372f), kValue, 0.5f);
    }

    @Override
    public void updatePointLight() {

        // Update the k value of the point light each tick
        float gametime = environmentManager.getHourDecimal();
        float kValue = (float) Math.sin(randomPointLightPeriod * gametime) * 0.15f + 1.2f;
        this.entityPointLight.setKValue(kValue);

        return;
    }

    @Override
    public PointLight getPointLight() {
        return this.entityPointLight;
    }
}