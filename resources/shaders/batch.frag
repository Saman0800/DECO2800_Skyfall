#version 330 core

/*
Uses a 2 component lighting model

Ambient light is cast by the sun
A portion of the light will be from this
And this portion will be modulated by sun colour

Point lights are casted by entities
The remaining portion will be from this
Strength of point light at a fragment will be maximum point light
Fragment colour will be the weighted averages of lights
*/

//stores a single point light emission
struct PointLight {
    //colour emitted by the light caster
    vec3 colour;
    //position of light in world space
    vec2 position;
    //light strength scalar
    float k;
    //light horizontal dispersion constant
    float a;
};


#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

in vec4 worldSpacePos;

//* Component from sun
//Portion of lighting done by sun
uniform float sunStrength;
//Colour of sunlight
uniform vec3 sunColour;

//* Component from point lights
//Maximum number of lights supported
#define MAX_POINT_LIGHTS 10
//number of lights to render
uniform int numberOfPointLights;
//data on Point Lights
uniform PointLight pointLights[MAX_POINT_LIGHTS];

//calculates the strength of a point light with the current point
float calculatePointLightStrength(int i) {
    //World coordinates are very small so scale
    float d = distance(pointLights[i].position, worldSpacePos.xy)/100;
    float a = pointLights[i].a;
    float k = pointLights[i].k;
    return k/(a*d*d + 1.0f);
}

//simple method to get light colour based on index
vec3 calculatePointLightColour(int i) {
    return pointLights[i].colour;
}

void main() {
    //query texture for colour of fragment
    vec4 texColor = texture2D(u_texture, v_texCoords).rgba;
    
    //discard transparent fragments
    //transparency is not supported for rendering
    //outside of pure transparency (with transparency less that 0.1)
    if (texColor.a < 0.1f) {
        discard;
    }

    //* Calculating ambient component is easy
    //Ambient component is modulated by the colour of the sun
    //This works because sunColour components are in [0,1]
    vec3 ambientComponent = sunColour * texColor.rgb;

    //* Point lights need to be evaluated, not so easy (or good performance)
    //strength of point light
    float fragmentPointLightStrength = 0;
    //used for normalising lighting
    float sumPointLightStrength = 0.001f;
    //will need to be normalised
    vec3 pointLightComponent = vec3(0);


    for (int i = 0; i < numberOfPointLights; i++) {
        float currentStrength = calculatePointLightStrength(i);
        vec3 currentColour = calculatePointLightColour(i);

        //choose the highest point light strength, totally physical
        fragmentPointLightStrength = max(fragmentPointLightStrength, currentStrength);
        //add weighted light component and sum total weight
        pointLightComponent += currentStrength * currentColour;
        sumPointLightStrength += currentStrength;
    }

    //do the normalisation
    pointLightComponent = fragmentPointLightStrength*(pointLightComponent/sumPointLightStrength) * texColor.rgb;

    gl_FragColor = vec4(
        //Add the two components together
        sunStrength * ambientComponent + (1 - sunStrength) * pointLightComponent,
        1.0
    );
}