package deco2800.skyfall.util.lightinghelpers;

import deco2800.skyfall.managers.EnvironmentManager;

public abstract class SpectralValue {

    /**
     * An environment manager that we can query the time for.
     */
    protected EnvironmentManager envirManag = null;

    /**
     * @return Yields an intensity based off the in-game time (usually queried
     * from an Environment manager).
     */
    abstract public float getIntensity();

    /**
     * @param time A concrete time (not nesseccarily related to the game).
     * @return Yields an intensity based off a concrete time.
     */
    abstract public float getIntensity(float time);
}