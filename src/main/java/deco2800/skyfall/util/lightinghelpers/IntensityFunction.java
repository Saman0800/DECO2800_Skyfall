package deco2800.skyfall.util.lightinghelpers;

/**
 * This interface is used to give a light intensity for a corresponding time
 * value.
 */
public interface IntensityFunction {
    /**
     * This function is used to prescribe a intensity value to a time value.
     * 
     * @return Returns value between 0 and 1 that represents a light intensity.
     */
    public double intensityMap(double gameTime);

}