package deco2800.skyfall.util.lightinghelpers;

import java.util.*;

import deco2800.skyfall.managers.EnvironmentManager;

public class LinearSpectralValue extends SpectralValue {

    /**
     * A set that will contain all of the time TFTuple values to linearly
     * interpolate between.
     */
    private TreeSet<TFTuple> tupleSet;

    /**
     * Values for storing the coefficients of the linear function y = mx + c.
     */
    private float m;
    private float c;

    /**
     * The two time intervals that the current game time is in between.
     */
    private TFTuple lowerTuple;
    private TFTuple higherTuple;

    public LinearSpectralValue() {
        // This will create spectral value that has a constant output value of 1
        TFTuple startTuple = new TFTuple(0.0f, 1.0f);
        TFTuple endTuple = new TFTuple(24.0f, 1.0f);

        this.m = 0.0f;
        this.c = 1.0f;

        tupleSet = new TreeSet<>();

        lowerTuple = startTuple;
        higherTuple = endTuple;

        tupleSet.add(startTuple);
        tupleSet.add(endTuple);
    }

    public LinearSpectralValue(List<TFTuple> startTuples) {

        if (startTuples.size() == 0) {
            throw new IllegalArgumentException("There must be at least two starting tuples.");
        }

        Collections.sort(startTuples);
        addFillerTuples(startTuples);

        tupleSet = new TreeSet<>(startTuples);
        setLowHigh(0.0f);
        calcLinearCoeff();
    }

    public LinearSpectralValue(List<TFTuple> startTuples, EnvironmentManager envirManag) {

        this(startTuples);
        this.envirManag = envirManag;

    }

    /**
     * Adds in tuples at HOURS 0 and 24 to assist with interpolation.
     * 
     * @param startTuples User input tuples
     */
    private void addFillerTuples(List<TFTuple> startTuples) {

        TFTuple startTFTuple = startTuples.get(0);
        TFTuple endTFTuple = startTuples.get(startTuples.size() - 1);

        if (Float.compare(startTFTuple.getHour(), 0.0f) == 0) {
            if (Float.compare(endTFTuple.getHour(), 24.0f) == 0) {
                startTuples.remove(endTFTuple);
            }

            startTuples.add(new TFTuple(24.0f, startTFTuple.getIntensity()));
        } else {
            // Figure out what the intensity values of the filler tuples should be
            float t1 = endTFTuple.getHour();
            float v1 = endTFTuple.getIntensity();
            float t2 = endTFTuple.getHour() + startTFTuple.getHour() + 1;
            float v2 = startTFTuple.getIntensity();

            float gradient = (v1 - v2) / (t1 - t2);
            float intercept = v1 - gradient * t1;

            startTuples.add(new TFTuple(0.0f, gradient * 24.0f + intercept));
            startTuples.add(new TFTuple(24.0f, gradient * 24.0f + intercept));
        }

        return;
    }

    /**
     * Calculates the coefficients for the linear interpolation using the lowerTuple
     * and higherTuples.
     */
    private void calcLinearCoeff() {

        this.m = (lowerTuple.getIntensity() - higherTuple.getIntensity())
                / (lowerTuple.getHour() - higherTuple.getHour());
        this.c = lowerTuple.getIntensity() - this.m * lowerTuple.getHour();

        return;
    }

    /**
     * A helper method that calculates the light intensity with the current
     * values for the gradient and y-intercept
     * 
     * @param time The time for which the intensity is requested.
     * @return The intensity for the given time.
     */
    private float calcIntensity(float time) {
        return this.m * time + this.c;
    }

    /**
     * Sets the appropriate high and low tuples from the tupleSet for the given
     * time value.
     * 
     * @param time The time for which the intensity is being requested.
     */
    private void setLowHigh(float time) {

        // The interval need to be updated
        Iterator<TFTuple> tupleIter = tupleSet.iterator();

        TFTuple prevTup = tupleIter.next();
        TFTuple nextTup = null;
        while (tupleIter.hasNext()) {
            nextTup = tupleIter.next();

            if (prevTup.getHour() <= time && time <= nextTup.getHour()) {
                lowerTuple = prevTup;
                break;
            }

            prevTup = nextTup;
        }

        higherTuple = nextTup;

    }

    /**
     * Evalutes the intensity by linearly interpolating between the two TFTuples
     * the input time sits between.
     * 
     * @param time The time for which the intensity is requested.
     * @return The intensity corresponding to the requested time value.
     */
    public float getIntensity(float time) {

        if (lowerTuple.getHour() <= time && time <= higherTuple.getHour()) {
            return calcIntensity(time);
        }

        // Update the high and low TFTuples
        setLowHigh(time);
        // Recalculate the linear coefficients
        calcLinearCoeff();

        return calcIntensity(time);
    };
}