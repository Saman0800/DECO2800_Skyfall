package deco2800.skyfall.util.lightinghelpers;

import deco2800.skyfall.managers.EnvironmentManager;

public abstract class SpectralValue {

    /**
     * An environment manager that we can query the time for.
     */
    protected EnvironmentManager envirManag = null;

    /**
     * @param time A concrete time (not nesseccarily related to the game).
     * @return Yields an intensity based off a concrete time.
     */
    public abstract float getIntensity(float time);

    /**
     * @return Yields an intensity based off the in-game time (usually queried
     * from an Environment manager).
     */
    public float getIntensity() {

        float returnValue = 0.0f;

        try {
            returnValue = getIntensity(envirManag.getHourDecimal());
        } catch (NullPointerException npe) {
            throw new IllegalStateException("Cannot use getIntensity when environment manager is not set");
        }
        return returnValue;
    }
}