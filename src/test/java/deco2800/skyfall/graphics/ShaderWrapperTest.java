package deco2800.skyfall.graphics;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import deco2800.skyfall.graphics.types.Vec2;
import deco2800.skyfall.graphics.types.Vec3;
import java.util.Random;
import java.util.Vector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**Tests the shader wrapper*/
public class ShaderWrapperTest {
    //mocking shaders requires some sort of local source code
    String shaderCode = "Draw gfx or something;";

    //location of shaders
    final String SHADER_LOCATION = "resources/shaders/";

    //shader wrapper to test
    ShaderWrapper sw = null;

    //mocked shader program
    ShaderProgram sp = null;

    //boolean used to test shader state between begin and end -> true
    boolean shaderInBetween = false;

    //used to check shader program settings last uniforms
    Vector<String> lastUniformTarget;
    Vector<Integer> lastUniformInt;
    Vector<Float> lastUniformFloat;
    Vector<Vec2> lastUniformFloat2;
    Vector<Vec3> lastUniformFloat3;

    //sets catches
    private void resetCatches() {
        lastUniformTarget.clear();
        lastUniformInt.clear();
        lastUniformFloat.clear();
        lastUniformFloat2.clear();
        lastUniformFloat3.clear();
    }

    @Before
    public void setup() {
        Gdx.files = mock(Files.class);
        Gdx.gl20 = mock(GL20.class);

        when(Gdx.files.internal(SHADER_LOCATION + "bad" + ".frag"))
                .thenReturn(FileHandle.tempFile(shaderCode));
        when(Gdx.files.internal(SHADER_LOCATION + "bad" + ".vert"))
                .thenReturn(FileHandle.tempFile(shaderCode));

        sw = new ShaderWrapper("bad");
        assertEquals(sw.getActive(), false);

        //mocking the opengl shader compilation pipeline is way too difficult
        //instead we will act as if compilation went well
        sw = Mockito.spy(sw);
        when(sw.getActive()).thenReturn(true);
        assertEquals(sw.getActive(), true);

        //shader program is malformed, given its compilation
        //will need to mock that
        sp = mock(ShaderProgram.class);

        //set these methods to check shader state
        doAnswer(invocation -> shaderInBetween = true).when(sp).begin();
        doAnswer(invocation -> shaderInBetween = false).when(sp).end();

        //set up previous value catches
        lastUniformTarget = new Vector<>();
        lastUniformInt = new Vector<>();
        lastUniformFloat = new Vector<>();
        lastUniformFloat2 = new Vector<>();
        lastUniformFloat3  = new Vector<>();;

        //set these methods to set up uniform values, but catch these values
        doAnswer(
                invocation -> {
                    lastUniformTarget.add((String)invocation.getArguments()[0]);
                    lastUniformInt.add((int)invocation.getArguments()[1]);
                    return 0;
                }
         ).when(sp).setUniformi(anyString(), anyInt());

        doAnswer(
                invocation -> {
                    lastUniformTarget.add((String)invocation.getArguments()[0]);
                    lastUniformFloat.add((float)invocation.getArguments()[1]);
                    return 0;
                }
        ).when(sp).setUniformf(anyString(), anyFloat());

        doAnswer(
                invocation -> {
                    lastUniformTarget.add((String)invocation.getArguments()[0]);
                    lastUniformFloat2.add( new Vec2(
                            (float)invocation.getArguments()[1],
                            (float)invocation.getArguments()[2]
                    ) );
                    return 0;
                }
        ).when(sp).setUniformf(anyString(), anyFloat(), anyFloat());

        doAnswer(
                invocation -> {
                    lastUniformTarget.add((String)invocation.getArguments()[0]);
                    lastUniformFloat3.add( new Vec3(
                            (float)invocation.getArguments()[1],
                            (float)invocation.getArguments()[2],
                            (float)invocation.getArguments()[3]
                    ) );
                    return 0;
                }
        ).when(sp).setUniformf(anyString(), anyFloat(), anyFloat(), anyFloat());

        when(sw.getShaderProgram()).thenReturn(sp);
    }

    /**
     * Tests rendering cycles, attempting to simulate rendering process as closely as possible
     */
    @Test
    public void testRenderCycle() {
        resetCatches();
        SpriteBatch sb = mock(SpriteBatch.class);
        doNothing().when(sb).setShader(isA(ShaderProgram.class));

        PointLight a = new PointLight(new Vec2(1.0f, 1.0f), new Vec3(0.5f, 0.5f, 0.5f), 4, 2);
        PointLight b = new PointLight(new Vec2(10.0f, 10.0f), new Vec3(0.7f, 0.2f, 0.3f), 8, 7);

        //set inbetween to false
        shaderInBetween = false;
        assertEquals(shaderInBetween, false);

        //begin shader pass
        sw.begin();

        //shader inbetween
        assertEquals(shaderInBetween, true);

        //add a single point light
        sw.addPointLight(a);

        //verify the values set at correct target
        assertEquals("pointLights[0].colour", lastUniformTarget.get(0));
        assertEquals("pointLights[0].position", lastUniformTarget.get(1));
        assertEquals("pointLights[0].k", lastUniformTarget.get(2));
        assertEquals("pointLights[0].a", lastUniformTarget.get(3));

        //verify correct values are being set within the shader
        assertEquals(lastUniformFloat3.get(0).getX(), 0.5, 0.01f);
        assertEquals(lastUniformFloat3.get(0).getY(), 0.5, 0.01f);
        assertEquals(lastUniformFloat3.get(0).getZ(), 0.5, 0.01f);
        assertEquals(lastUniformFloat2.get(0).getX(), 1.0f, 0.01f);
        assertEquals(lastUniformFloat2.get(0).getY(), 1.0f, 0.01f);
        assertEquals(lastUniformFloat.get(0), 4.0f, 0.01f);
        assertEquals(lastUniformFloat.get(1), 2.0f, 0.01f);

        //add ambient
        sw.setAmbientComponent(new Vec3(0.5f, 0.6f, 0.7f), 0.8f);

        //check shader is set to correct target
        assertEquals("sunStrength", lastUniformTarget.get(4));
        assertEquals("sunColour", lastUniformTarget.get(5));

        //check values
        assertEquals(lastUniformFloat.get(2), 0.8f, 0.01f);
        assertEquals(lastUniformFloat3.get(1).getX(), 0.5, 0.01f);
        assertEquals(lastUniformFloat3.get(1).getY(), 0.6, 0.01f);
        assertEquals(lastUniformFloat3.get(1).getZ(), 0.7, 0.01f);

        //finalise and push changes
        sw.finaliseAndAttachShader(sb);

        //check values set
        assertEquals((int)lastUniformInt.get(0), 1);
        assertEquals(sw.getPointLightCount(), 1);

        //end shader
        sw.end();

        //shader inbetween is over
        assertEquals(shaderInBetween, false);

        //correct ambient light is set
        Vec3 ambient = sw.getAmbientColour();
        assertEquals(ambient.getX(), 0.5f, 0.01f);
        assertEquals(ambient.getY(), 0.6f, 0.01f);
        assertEquals(ambient.getZ(), 0.7f, 0.01f);
        assertEquals(sw.getAmbientIntensity(), 0.8f, 0.01f);


        //Need to test that the shader wrapper can transition to a new frame with different
        //lights/ light counts/ light order

        resetCatches();

        //begin shader pass
        sw.begin();

        //shader inbetween
        assertEquals(shaderInBetween, true);

        //add both point lights, in different order
        sw.addPointLight(b);
        sw.addPointLight(a);

        //verify the values set at correct target
        assertEquals("pointLights[0].colour", lastUniformTarget.get(0));
        assertEquals("pointLights[0].position", lastUniformTarget.get(1));
        assertEquals("pointLights[0].k", lastUniformTarget.get(2));
        assertEquals("pointLights[0].a", lastUniformTarget.get(3));
        assertEquals("pointLights[1].colour", lastUniformTarget.get(4));
        assertEquals("pointLights[1].position", lastUniformTarget.get(5));
        assertEquals("pointLights[1].k", lastUniformTarget.get(6));
        assertEquals("pointLights[1].a", lastUniformTarget.get(7));

        //verify correct values are being set within the shader
        assertEquals(lastUniformFloat3.get(0).getX(), 0.7f, 0.01f);
        assertEquals(lastUniformFloat3.get(0).getY(), 0.2f, 0.01f);
        assertEquals(lastUniformFloat3.get(0).getZ(), 0.3f, 0.01f);
        assertEquals(lastUniformFloat2.get(0).getX(), 10.0f, 0.01f);
        assertEquals(lastUniformFloat2.get(0).getY(), 10.0f, 0.01f);
        assertEquals(lastUniformFloat.get(0), 8.0f, 0.01f);
        assertEquals(lastUniformFloat.get(1), 7.0f, 0.01f);

        assertEquals(lastUniformFloat3.get(1).getX(), 0.5, 0.01f);
        assertEquals(lastUniformFloat3.get(1).getY(), 0.5, 0.01f);
        assertEquals(lastUniformFloat3.get(1).getZ(), 0.5, 0.01f);
        assertEquals(lastUniformFloat2.get(1).getX(), 1.0f, 0.01f);
        assertEquals(lastUniformFloat2.get(1).getY(), 1.0f, 0.01f);
        assertEquals(lastUniformFloat.get(2), 4.0f, 0.01f);
        assertEquals(lastUniformFloat.get(3), 2.0f, 0.01f);

        //Also want to change changing ambients
        sw.setAmbientComponent(new Vec3(0.2f, 0.3f, 0.4f), 0.2f);

        //check shader is set to correct target
        assertEquals("sunStrength", lastUniformTarget.get(8));
        assertEquals("sunColour", lastUniformTarget.get(9));

        //check values
        assertEquals(lastUniformFloat.get(4), 0.2f, 0.01f);
        assertEquals(lastUniformFloat3.get(2).getX(), 0.2, 0.01f);
        assertEquals(lastUniformFloat3.get(2).getY(), 0.3, 0.01f);
        assertEquals(lastUniformFloat3.get(2).getZ(), 0.4, 0.01f);

        //finalise and push changes
        sw.finaliseAndAttachShader(sb);

        //check values set
        assertEquals((int)lastUniformInt.get(0), 2);
        assertEquals(sw.getPointLightCount(), 2);
    }

    /**
     * Test if the shader will stop when it reaches max ints
     */
    @Test
    public void testOverflow() {
        Random r = new Random();
        sw.begin();

        //add a couple of lights
        for (int i = 0; i < 1000; i++) {
            PointLight a = new PointLight(
                    new Vec2(r.nextFloat(), r.nextFloat()),
                    new Vec3(r.nextFloat(), r.nextFloat(), r.nextFloat()),
                    r.nextFloat(),
                    r.nextFloat()
            );
            sw.addPointLight(a);
        }

        //should should hit max (20)
        SpriteBatch sb = mock(SpriteBatch.class);
        sw.finaliseAndAttachShader(sb);
        assertEquals(sw.getPointLightCount(), 20);
    }
}
