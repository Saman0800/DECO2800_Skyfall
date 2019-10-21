package deco2800.skyfall.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextureRegion.class)
public class AnimationManagerTest {
    @Mock
    Map<String, Animation<TextureRegion>> animationMap;

    @Mock
    TextureManager textureManager ;

    @InjectMocks
    AnimationManager animationManager = new AnimationManager(true);

    @Test
    public void getAnimationTest() {
        when(animationMap.containsKey("first")).thenReturn(true);
        when(animationMap.get("first")).thenReturn(null);

        animationManager.getAnimation("first");
        verify(animationMap).get("first");
    }


    @Test
    public void badGetAnimationTest() {
        Animation<TextureRegion> doesNotExist = animationManager.getAnimation("does not exist");
        assertEquals(doesNotExist, null);
    }



    @Test
    public void generateAnimationObjectTest() {
        Texture t  = mock(Texture.class);
        PowerMockito.mockStatic(TextureRegion.class);

        TextureRegion[][] textureRegions = new TextureRegion[2][2];
        textureRegions[0][0] = new TextureRegion();
        textureRegions[0][1] = new TextureRegion();
        textureRegions[1][0] = new TextureRegion();
        textureRegions[1][1] = new TextureRegion();
        when(textureManager.hasTexture("mock")).thenReturn(true);
        when(textureManager.getTexture("mock")).thenReturn(t);
        when(TextureRegion.split(t, 100, 100)).thenReturn(textureRegions);

        animationManager.generateAnimationObject("test", "mock", 100, 100, 1f/4f);
        verify(animationMap).put(eq("test"), anyObject());
    }


    @Test
    public void generateAnimationObjectBad() {
        when(textureManager.hasTexture("mock")).thenReturn(false);
        animationManager.generateAnimationObject("test", "mock", 100, 100, 1f/4f);
        verifyZeroInteractions(animationMap);
    }


    @Test
    public void getKeyFrameFromAnimationBadName() {
        assertEquals(animationManager.getKeyFrameFromAnimation("mock", 2), null);
    }

    @Test
    public void getKeyFrameFromAnimationBadIndex() {
        Animation<TextureRegion> animation = mock(Animation.class);
        TextureRegion[] region = new TextureRegion[2];
        region[0] = new TextureRegion();
        region[1] = new TextureRegion();


        when(animationMap.get("mock")).thenReturn(animation);
        when(animation.getKeyFrames()).thenReturn(region);
        //when(region.length).thenReturn(2);

        assertEquals(animationManager.getKeyFrameFromAnimation("mock", 3), null);
    }

    @Test
    public void getKeyFrameFromAnimation() {
        Animation<TextureRegion> animation = mock(Animation.class);
        TextureRegion[] region = new TextureRegion[2];
        region[0] = new TextureRegion();
        region[1] = new TextureRegion();



        when(animationMap.get("mock")).thenReturn(animation);
        when(animation.getKeyFrames()).thenReturn(region);


        try {
            //Hasn't returned null
            animationManager.getKeyFrameFromAnimation("mock", 1);
            fail();
        } catch (NullPointerException npe) {

        }


    }

}