package deco2800.skyfall.util.lightinghelpers;

/**
 * The Time Double Tuple (TDTuple) is a helper class that acts as a 
 * tuple which holds the intensity that the brightness value should be at 
 * a particular hour (within the game).
 */
public class TDTuple {

    /**
     * The in-game hour, in 24-hour time.
     */
    private int hour;

    /**
     * The intensity of the brightness value for that corressponding hour,
     * between 0 and 1.
     */
    private double intensity;

    public TDTuple() {
        this.hour = 0;
        this.intensity = 1.0;
    }

    public TDTuple(int hour, double intensity) {

        if (hour < 0 || hour > 23) {
            throw new ArithmeticException("The hour must be an integer between 0 and 24");
        } else {
            this.hour = hour;
        }

        if (intensity < 0.0 || intensity > 1.0) {
            throw new ArithmeticException("The intensity value must be between 0.0 and 1.0.");
        } else {
            this.intensity = intensity;
        }
    }

    /**
     * @return Returns the hour value of this tuple.
     */
    int getHour() {
        return this.hour;
    }

    /**
     * @return Returns the intensity value of this tuple.
     */
    double getIntensity() {
        return this.intensity;
    }
}