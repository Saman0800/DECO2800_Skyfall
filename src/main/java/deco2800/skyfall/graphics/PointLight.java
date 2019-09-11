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

    public PointLight(vec2 position, vec3 colour, float k, float a) {
        this.position = position;
        this.colour = colour;
        this.k = k;
        this.a = a;
    }

    public vec2 getPosition() {
        return position;
    }

    public void setPosition(vec2 position) {
        this.position = position;
    }

    public vec3 getColour() {
        return colour;
    }
    public float getK() {
        return k;
    }
    public float getA() {
        return a;
    }
}
