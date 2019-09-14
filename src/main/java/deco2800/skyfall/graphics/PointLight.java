package deco2800.skyfall.graphics;

import deco2800.skyfall.graphics.types.vec2;
import deco2800.skyfall.graphics.types.vec3;

/**
 * Represents all information required for a point light
 * A point light is a light that has a position and emits in a circle around it
 * Adding lights to the game can be done by tying it to entities through the api
 */
public class PointLight {
    //world space position of light emitter
    vec2 position;
    //colour emitted by the light caster
    vec3 colour;
    //light strength scalar
    float k;
    //light horizontal dispersion constant
    float a;

    /**
     * Creates a point light
     * @param position x,y position in world space
     * @param colour rgb colour, from [0,1] for each component
     * @param k scaling constant, lower values will result in lower overall strength
     * @param a horizontal spread constant, lower values spread light out further
     */
    public PointLight(vec2 position, vec3 colour, float k, float a) {
        this.position = position;
        this.colour = colour;
        this.k = k;
        this.a = a;
    }

    /**
     * @return current position of light in world space
     */
    public vec2 getPosition() {
        return position;
    }

    /**
     * @param position the new position of the point light in world space
     */
    public void setPosition(vec2 position) {
        this.position = position;
    }

    /**
     * Sets a new k value for the point light.
     */
    public void setKValue(float newK) {
        this.k = newK;
    }

    /**
     * @return returns colour of light, each component is from [0,1]
     */
    public vec3 getColour() {
        return colour;
    }

    /**
     * @return gets `k` constant
     */
    public float getK() {
        return k;
    }

    /**
     * @return gets `a` constant
     */
    public float getA() {
        return a;
    }
}
