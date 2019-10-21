package deco2800.skyfall.util.lightinghelpers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import deco2800.skyfall.managers.EnvironmentManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EnvironmentManager.class })
public class LinearSpectralValueTest {

    @Mock
    private EnvironmentManager mockEnvironManag;

    private LinearSpectralValue spectralVal;

    @Before
    public void Setup() {

        mockEnvironManag = mock(EnvironmentManager.class);
        mockStatic(EnvironmentManager.class);
        when(mockEnvironManag.getHourDecimal()).thenReturn(6.0f);

    }

    @Test
    public void testEmpty() {
        spectralVal = new LinearSpectralValue();

        assertEquals(1.0f, spectralVal.getIntensity(0.5f), 0.0001);
        assertEquals(1.0f, spectralVal.getIntensity(23.0f), 0.0001);
    }

    @Test
    public void testListConstructor1() {

        TFTuple tuple1H = new TFTuple(1.0f, 0.0f);
        TFTuple tuple2H = new TFTuple(2.0f, 1.0f);
        TFTuple tuple3H = new TFTuple(3.0f, 0.5f);
        TFTuple tuple23H = new TFTuple(23.0f, 1.0f);

        List<TFTuple> tupleList = new ArrayList<>();
        tupleList.add(tuple1H);
        tupleList.add(tuple3H);
        tupleList.add(tuple2H);
        tupleList.add(tuple23H);

        spectralVal = new LinearSpectralValue(tupleList);

        assertEquals(0.5f, spectralVal.getIntensity(1.5f), 0.01);
        assertEquals(0.25f, spectralVal.getIntensity(1.25f), 0.01);
        assertEquals(0.5f, spectralVal.getIntensity(0.0f), 0.01);
        assertEquals(0.25f, spectralVal.getIntensity(0.5f), 0.01);
        assertEquals(1.0f, spectralVal.getIntensity(2.0f), 0.01);
        assertEquals(0.75f, spectralVal.getIntensity(2.5f), 0.01);
        assertEquals(0.75f, spectralVal.getIntensity(23.5f), 0.01);
        assertEquals(0.5f, spectralVal.getIntensity(0.0f), 0.01);
    }

    @Test
    public void testEnvironConstructor1() {

        TFTuple tuple0H = new TFTuple(0.0f, 0.1f);
        TFTuple tuple12H = new TFTuple(12.0f, 0.9f);

        List<TFTuple> tupleList = new ArrayList<>();
        tupleList.add(tuple0H);
        tupleList.add(tuple12H);

        spectralVal = new LinearSpectralValue(tupleList, mockEnvironManag);

        assertEquals(0.1f, spectralVal.getIntensity(0.0f), 0.01);
        // This will happen at time 6
        assertEquals(0.5f, spectralVal.getIntensity(), 0.01);
        assertEquals(0.9f, spectralVal.getIntensity(12.0f), 0.01);
        assertEquals(0.5f, spectralVal.getIntensity(18.0f), 0.01);
    }
}