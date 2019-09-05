package deco2800.skyfall.util.lightinghelpers;

import deco2800.skyfall.managers.EnvironmentManager;

public class FunctionalSpectralValue extends SpectralValue {

    /**
     * A funcion that maps a 24 hour time to a light intensity.
     * The default map for the FunctionalSpectralValue class always returns
     * a light intensity of 1.
     */
    private IntensityFunction intensityFunction = (double x) -> 1.0;

    FunctionalSpectralValue() {
        // Do nothing, we already have a default intensityFunction picked out
    }

    FunctionalSpectralValue(IntensityFunction intensityFunction) {
        this.intensityFunction = intensityFunction;
    }

    FunctionalSpectralValue(IntensityFunction intensityFunction, EnvironmentManager envirManag) {
        this(intensityFunction);
        this.envirManag = envirManag;
    }

    public double getIntensity() {

        double returnValue = 0.0;

        try {
            returnValue = intensityFunction.intensityMap(envirManag.getHourDecimal());
        } catch (NullPointerException NPE) {
            throw new IllegalStateException("Cannot use getIntensity when environment manager is not set");
        }
        return returnValue;
    };

    public double getIntensity(double time) {
        return intensityFunction.intensityMap(time);
    };
}