package deco2800.skyfall.graphics;

import deco2800.skyfall.graphics.types.vec2;
import deco2800.skyfall.graphics.types.vec3;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointLightTest {
    @Test
    public void testConstructor() {
        PointLight testLight
                = new PointLight(new vec2(0.0f,0.0f), new vec3(0.5f, 0.5f, 0.5f), 0.5f, 0.5f);

        assertEquals(0, testLight.getPosition().x, 0.1f);
        assertEquals(0, testLight.getPosition().y, 0.1f);

        assertEquals(0.5f, testLight.getColour().x, 0.1f);
        assertEquals(0.5f, testLight.getColour().y, 0.1f);
        assertEquals(0.5f, testLight.getColour().z, 0.1f);

        assertEquals(0.5f, testLight.getA(), 0.1f);
        assertEquals(0.5f, testLight.getK(), 0.1f);
    }

    @Test
    public void testMutator() {
        PointLight testLight
                = new PointLight(new vec2(0,0), new vec3(0.5f, 0.5f, 0.5f), 0.5f, 0.5f);

        assertEquals(0, testLight.getPosition().x, 0.1f);
        assertEquals(0, testLight.getPosition().y, 0.1f);

        assertEquals(0.5f, testLight.getK(), 0.1f);

        testLight.setPosition(new vec2(1.0f, 1.0f));
        testLight.setKValue(0.7f);

        assertEquals(1.0f, testLight.getPosition().x, 0.1f);
        assertEquals(1.0f, testLight.getPosition().y, 0.1f);

        assertEquals(0.7f, testLight.getK(), 0.1f);
    }
}
