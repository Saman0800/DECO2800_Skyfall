package deco2800.skyfall.util.lightinghelpers;

import deco2800.skyfall.managers.EnvironmentManager;

public class FunctionalSpectralValue extends SpectralValue {

    /**
     * A function that maps a 24 hour time to a light intensity.
     * The default map for the FunctionalSpectralValue class always returns
     * a light intensity of 1.
     */
    private IntensityFunction intensityFunction = (float x) -> 1.0f;

    public FunctionalSpectralValue() {
        // Do nothing, we already have a default intensityFunction picked out
    }

    public FunctionalSpectralValue(IntensityFunction intensityFunction) {
        this.intensityFunction = intensityFunction;
    }

    public FunctionalSpectralValue(IntensityFunction intensityFunction, EnvironmentManager envirManag) {
        this(intensityFunction);
        this.envirManag = envirManag;
    }

    public float getIntensity(float time) {
        return intensityFunction.intensityMap(time);
    };

    public float getIntensity() {

        float returnValue = 0.0f;

        try {
            returnValue = getIntensity(envirManag.getHourDecimal());
        } catch (NullPointerException NPE) {
            throw new IllegalStateException("Cannot use getIntensity when environment manager is not set");
        }
        return returnValue;
    };
}