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

    public void begin() {
        if (active) {
            shaderProgram.begin();
        }
    }

    public void end() {
        if (active) {
            shaderProgram.end();
        }
    }

    public void finaliseAndAttachShader(SpriteBatch batch) {
        if (active) {
            shaderProgram.setUniformi("numberOfPointLights", pointLightCount);
            finalPointLightCount = pointLightCount;
            pointLightCount = 0;
            batch.setShader(shaderProgram);
        }
    }

    public int getPointLightCount() {
        return finalPointLightCount;
    }

    public void setAmbientComponent(vec3 color, float intensity) {
        ambientColour = color.getClampedComponents(0.0f, 1.0f);
        ambientIntensity = clamp(intensity, 0.0f, 1.0f);;
        if (active) {
            shaderProgram.setUniformf("sunStrength", intensity);
            shaderProgram.setUniformf("sunColour", color.x, color.y, color.z);
        }
    }

    public vec3 getAmbientColour() {
        return ambientColour;
    }

    public float getAmbientIntensity() {
        return ambientIntensity;
    }

    /**
     *
     * @param pointLight The constructor for point light grantees a well formced point light
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
