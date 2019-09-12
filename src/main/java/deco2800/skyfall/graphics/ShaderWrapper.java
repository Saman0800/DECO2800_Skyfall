package deco2800.skyfall.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import deco2800.skyfall.graphics.types.vec2;
import deco2800.skyfall.graphics.types.vec3;
import deco2800.skyfall.util.SettingsFile;

import static deco2800.skyfall.util.MathUtil.clamp;

/**
 * A class that wraps a shader program
 * Handles loading, compiling, disabling and adding lighting specification
 */
public class ShaderWrapper {
    //default shader used if not active
    boolean active = false;
    //links to shaderProgram, or ill-formed program on failure
    ShaderProgram shaderProgram;

    //Ambient components
    vec3 ambientColour = new vec3(0.0f);
    float ambientIntensity = 0;

    //used for counting number of light points allocated
    int pointLightCount = 0;
    //used for final pointLight debuging
    int finalPointLightCount = 0;

    /**
     * Loads and compiles a shader program
     * @param shaderName name of shader to use, resource will be <shaderName>.vert and <shaderName>.frag
     *                   in resources\shaders\
     */
    public ShaderWrapper(String shaderName) {
        //load shaders
        String vertexShader = Gdx.files.internal("resources\\shaders\\" + shaderName + ".vert").readString();
        String fragmentShader = Gdx.files.internal("resources\\shaders\\" + shaderName + ".frag").readString();
        shaderProgram = new ShaderProgram(vertexShader, fragmentShader);

        //Allows uniform variables to be in the fragment shader but not referenced in the vertex
        shaderProgram.pedantic = false;

        //A small log explaining how the shader compilation went
        System.out.println("\nShader program log:");
        System.out.print(shaderProgram.getLog());
        if (shaderProgram.isCompiled()) {
            System.out.println("Shader program compiled\n");

            SettingsFile gfxSettings = new SettingsFile("settings\\gfx.ini");
            active = (gfxSettings.get("s_use_e_shader", 1) != 0);
            gfxSettings.close();
        }
        else {
            System.out.println("Shader program failed to compile, reverting to default\n");
        }
    }

    /**
     * Begins shader program, order is important
     * No error checking for proper order
     */
    public void begin() {
        if (active) {
            shaderProgram.begin();
        }
    }

    /**
     * ends shader program, order is important
     * No error checking for proper order
     */
    public void end() {
        if (active) {
            shaderProgram.end();
        }
    }

    /**
     * Finalises all point lights and ambient components specified
     * writes these values to the shader then attaches to the batch
     * Afterwards, resets for next frame
     * @param batch the batch that requires the shader be attached to
     *              invoke before the render call
     */
    public void finaliseAndAttachShader(SpriteBatch batch) {
        if (active) {
            shaderProgram.setUniformi("numberOfPointLights", pointLightCount);
            finalPointLightCount = pointLightCount;
            pointLightCount = 0;
            batch.setShader(shaderProgram);
        }
    }

    /**
     * will be zero with no active shader
     * @return number of point lights being rendered
     */
    public int getPointLightCount() {
        return finalPointLightCount;
    }

    /**
     * Sets the ambient component to be used in next scene rendered
     * Needs to be set each frame
     * @param color vec3 of colour to set, as (r,g,b) with each component in [0,1]
     * @param intensity intensity of light from [0,1], point light intensity will be 1-intensity
     */
    public void setAmbientComponent(vec3 color, float intensity) {
        ambientColour = color.getClampedComponents(0.0f, 1.0f);
        ambientIntensity = clamp(intensity, 0.0f, 1.0f);;
        if (active) {
            shaderProgram.setUniformf("sunStrength", ambientIntensity);
            shaderProgram.setUniformf("sunColour", ambientColour.x, ambientColour.y, ambientColour.z);
        }
    }

    /**
     * Gets current ambient colour
     * @return in the form (r,g,b) with each component in [0,1]
     */
    public vec3 getAmbientColour() {
        return ambientColour;
    }

    /**
     * Get current ambient intensity
     * @return current value
     */
    public float getAmbientIntensity() {
        return ambientIntensity;
    }

    /**
     * Adds a point light, must be done before every finaliseAndAttachShader, and for each frame
     * @param pointLight The constructor for point light grantees a well formed point light
     */
    public void addPointLight(PointLight pointLight) {
        if (active) {
            //creates the string for the target point light
            String target = "pointLights[" + Integer.toString(pointLightCount)  + "]";

            vec3 colour = pointLight.getColour();
            vec2 position = pointLight.getPosition();

            //set the values of the uniform point lights in the shader
            shaderProgram.setUniformf(target + ".colour", colour.x, colour.y, colour.z);
            shaderProgram.setUniformf(target + ".position", position.x, position.y);
            shaderProgram.setUniformf(target + ".k", pointLight.getK());
            shaderProgram.setUniformf(target + ".a", pointLight.getA());

            pointLightCount++;
        }
    }

    /**
     * If shader is being used return true, false implies default shader usage
     * @return usage of shader
     */
    public boolean getActive() {
        return active;
    }
}
