package deco2800.skyfall.graphics;

import static deco2800.skyfall.util.MathUtil.clamp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import deco2800.skyfall.graphics.types.Vec2;
import deco2800.skyfall.graphics.types.Vec3;
import deco2800.skyfall.util.SettingsFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that wraps a shader program Handles loading, compiling, disabling and
 * adding lighting specification
 */
public class ShaderWrapper {
    //location of shaders
    private static final String SHADER_LOCATION = "resources/shaders/";

    //maximum number of point lights, must be same as shader
    private static final int MAX_POINT_LIGHTS = 20;

    // default shader used if not active
    private boolean active = false;
    // links to shaderProgram, or ill-formed program on failure
    private ShaderProgram shaderProgram;

    // Ambient components
    private Vec3 ambientColour = new Vec3(0.0f);
    private float ambientIntensity = 0;

    // used for counting number of light points allocated
    private int pointLightCount = 0;
    // used for final pointLight debugging
    private int finalPointLightCount = 0;

    /**
     * Accessor for shaderProgram, mostly used for testing
     */
    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    /**
     * Loads and compiles a shader program
     * 
     * @param shaderName name of shader to use, resource will be <shaderName>.vert
     *                   and <shaderName>.frag in resources\shaders\
     */
    public ShaderWrapper(String shaderName) {
        // load shaders
        Logger logger = LoggerFactory.getLogger(ShaderWrapper.class);
        try {
            String vertexShader = Gdx.files.internal( SHADER_LOCATION + shaderName + ".vert").readString();
            String fragmentShader = Gdx.files.internal(SHADER_LOCATION + shaderName + ".frag").readString();
            shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
        } catch (GdxRuntimeException e) {
            logger.warn("\nShader source not found, check:");
            logger.warn(String.format("%s%s.vert", SHADER_LOCATION, shaderName));
            logger.warn(String.format("%s%s.frag", SHADER_LOCATION, shaderName));
            logger.warn("Extended shader disabled");
            return;
        }

        // Allows uniform variables to be in the fragment shader but not referenced in
        // the vertex
        ShaderProgram.pedantic = false;

        // A small log explaining how the shader compilation went
        logger.warn("\nShader program log:");
        logger.warn(getShaderProgram().getLog());
        if (getShaderProgram().isCompiled()) {
            logger.warn("Shader program compiled\n");

            SettingsFile gfxSettings = new SettingsFile("settings\\gfx.ini");
            active = (gfxSettings.get("s_use_e_shader", 1) != 0);
            gfxSettings.close();
        } else {
            logger.warn("Shader program failed to compile, reverting to default\n");
        }
    }

    /**
     * Begins shader program, order is important No error checking for proper order
     */
    public void begin() {
        if (getActive()) {
            getShaderProgram().begin();
        }
    }

    /**
     * ends shader program, order is important No error checking for proper order
     */
    public void end() {
        if (getActive()) {
            getShaderProgram().end();
        }
    }

    /**
     * Finalises all point lights and ambient components specified writes these
     * values to the shader then attaches to the batch Afterwards, resets for next
     * frame
     * 
     * @param batch the batch that requires the shader be attached to invoke before
     *              the render call
     */
    public void finaliseAndAttachShader(SpriteBatch batch) {
        if (getActive()) {
            getShaderProgram().setUniformi("numberOfPointLights", pointLightCount);
            finalPointLightCount = pointLightCount;
            pointLightCount = 0;
            batch.setShader(getShaderProgram());
        }
    }

    /**
     * Not an accurate count of current point lights Rather a final count of last
     * render finalisation's total rendered point lights will be zero with no active
     * shader
     * 
     * @return number of point lights being rendered after finalisation
     */
    public int getPointLightCount() {
        return finalPointLightCount;
    }

    /**
     * Sets the ambient component to be used in next scene rendered Needs to be set
     * each frame
     * 
     * @param color     vec3 of colour to set, as (r,g,b) with each component in
     *                  [0,1]
     * @param intensity intensity of light from [0,1], point light intensity will be
     *                  1-intensity
     */
    public void setAmbientComponent(Vec3 color, float intensity) {
        ambientColour = color.getClampedComponents(0.0f, 1.0f);
        ambientIntensity = clamp(intensity, 0.0f, 1.0f);
        if (getActive()) {
            getShaderProgram().setUniformf("sunStrength", ambientIntensity);
            getShaderProgram().setUniformf("sunColour", ambientColour.getX(), ambientColour.getY(), ambientColour.getZ());
        }
    }

    /**
     * Gets current ambient colour
     * 
     * @return in the form (r,g,b) with each component in [0,1]
     */
    public Vec3 getAmbientColour() {
        return ambientColour;
    }

    /**
     * Get current ambient intensity
     * 
     * @return current value
     */
    public float getAmbientIntensity() {
        return ambientIntensity;
    }

    /**
     * Adds a point light, must be done before every finaliseAndAttachShader, and
     * for each frame
     * 
     * @param pointLight The constructor for point light grantees a well formed
     *                   point light
     */
    public void addPointLight(PointLight pointLight) {
        if (getActive()) {
            if (pointLightCount >= MAX_POINT_LIGHTS) {
                return;
            }

            // creates the string for the target point light
            String target = "pointLights[" + pointLightCount + "]";

            Vec3 colour = pointLight.getColour();
            Vec2 position = pointLight.getPosition();

            // set the values of the uniform point lights in the shader
            getShaderProgram().setUniformf(target + ".colour", colour.getX(), colour.getY(), colour.getZ());
            getShaderProgram().setUniformf(target + ".position", position.getX(), position.getY());
            getShaderProgram().setUniformf(target + ".k", pointLight.getK());
            getShaderProgram().setUniformf(target + ".a", pointLight.getA());

            pointLightCount++;
        }
    }

    /**
     * If shader is being used return true, false implies default shader usage
     * 
     * @return usage of shader
     */
    public boolean getActive() {
        return active;
    }
}
