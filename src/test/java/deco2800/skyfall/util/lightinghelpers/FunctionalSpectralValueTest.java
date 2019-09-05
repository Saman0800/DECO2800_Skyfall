package deco2800.skyfall.util.lightinghelpers;

import deco2800.skyfall.managers.EnvironmentManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EnvironmentManager.class })
public class FunctionalSpectralValueTest {

    @Mock
    private EnvironmentManager mockEnvironManag;

    private FunctionalSpectralValue spectralVal;

    @Before
    public void Setup() {

        mockEnvironManag = mock(EnvironmentManager.class);
        mockStatic(EnvironmentManager.class);
        when(mockEnvironManag.getHourDecimal()).thenReturn(4.5);

    }

    @Test
    public void testEmpty() {
        spectralVal = new FunctionalSpectralValue();

        assertEquals(1.0, spectralVal.getIntensity(0.5), 0.0001);
        assertEquals(1.0, spectralVal.getIntensity(23.0), 0.0001);
    }

    @Test
    public void testFunctionOnly() {

        IntensityFunction intensityFunction = (double x) -> 2 * x;
        spectralVal = new FunctionalSpectralValue(intensityFunction);

        assertEquals(1.0, spectralVal.getIntensity(0.5), 0.0001);
        assertEquals(46.0, spectralVal.getIntensity(23.0), 0.0001);

        try {
            // Try getting the intensity without passing in a Environment Manager
            spectralVal.getIntensity();
            fail();
        } catch (IllegalStateException ISE) {
            // Do nothing, this is expected behavior
        } catch (Exception E) {
            fail("An Illegal state exception should have been thrown: " + E.getLocalizedMessage());
        }
    }

    @Test
    public void testFunctionManager() {

        IntensityFunction intensityFunction = (double x) -> 3 * x;
        spectralVal = new FunctionalSpectralValue(intensityFunction, mockEnvironManag);

        assertEquals(6.0, spectralVal.getIntensity(2.0), 0.0001);
        assertEquals(13.5, spectralVal.getIntensity(), 0.0001);
    }
}