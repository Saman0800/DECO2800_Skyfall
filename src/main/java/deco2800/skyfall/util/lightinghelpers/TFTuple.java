package deco2800.skyfall.util.lightinghelpers;

/**
 * The Time Double Tuple (TFTuple) is a helper class that acts as a 
 * tuple which holds the intensity that the brightness value should be at 
 * a particular time (within the game).
 */
public class TFTuple implements Comparable<TFTuple> {

    /**
     * The in-game decimal 24-hour time.
     */
    private float time;

    /**
     * The intensity of the brightness value for that corressponding time,
     * between 0 and 1.
     */
    private float intensity;

    public TFTuple() {
        this.time = 0.0f;
        this.intensity = 1.0f;
    }

    public TFTuple(float time, float intensity) {

        if (time < 0 || time > 24) {
            throw new ArithmeticException("The time must be an integer between 0 and 24");
        } else {
            this.time = time;
        }

        if (intensity < 0.0 || intensity > 1.0) {
            throw new ArithmeticException("The intensity value must be between 0.0 and 1.0.");
        } else {
            this.intensity = intensity;
        }
    }

    /**
     * @return Returns the time value of this tuple.
     */
    float getHour() {
        return this.time;
    }

    /**
     * @return Returns the intensity value of this tuple.
     */
    float getIntensity() {
        return this.intensity;
    }

    @Override
    public int compareTo(TFTuple tup) {
        return Float.compare(this.time, tup.getHour());
    }
}