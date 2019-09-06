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
    float m;
    float c;

    /**
     * The two time intervals that the current game time is in between.
     */
    TFTuple lowerTuple;
    TFTuple higherTuple;

    public LinearSpectralValue() {
        // This will create spectral value that has a constant output value of 1
        TFTuple startTuple = new TFTuple(0.0f, 1.0f);
        TFTuple endTuple = new TFTuple(24.0f, 1.0f);

        tupleSet = new TreeSet<>();

        lowerTuple = startTuple;
        higherTuple = endTuple;

        tupleSet.add(startTuple);
        tupleSet.add(endTuple);
        calcLinearCoeff();
    }

    public LinearSpectralValue(List<TFTuple> startTuples) {

        if (startTuples.size() == 0) {
            throw new IllegalArgumentException("There must be at least two starting tuples.");
        }

        Collections.sort(startTuples);
        addFillerTuples(startTuples);
        Collections.sort(startTuples);

        lowerTuple = startTuples.get(0);
        higherTuple = startTuples.get(1);

        tupleSet = new TreeSet<>(startTuples);
        calcLinearCoeff();
    }

    public LinearSpectralValue(List<TFTuple> startTuples, EnvironmentManager envirManag) {

        this(startTuples);
        this.envirManag = envirManag;

    }

    private void addFillerTuples(List<TFTuple> startTuples) {

        TFTuple startTFTuple = startTuples.get(0);
        TFTuple endTFTuple = startTuples.get(startTuples.size());

        if (Float.compare(startTFTuple.getHour(), 0.0f) == 0) {
            if (Float.compare(endTFTuple.getHour(), 24.0f) == 0) {
                startTuples.remove(endTFTuple);
            }

            startTuples.add(new TFTuple(24.0f, startTFTuple.getIntensity()));
        } else {
            // Figure out what the intensity values of the filler tuples should be
            float t1 = endTFTuple.getHour();
            float v1 = endTFTuple.getIntensity();
            float t2 = endTFTuple.getHour() + startTFTuple.getHour();
            float v2 = startTFTuple.getIntensity();

            float gradient = (t1 - t2) / (v1 - v2);
            float intercept = v1 - gradient * t1;

            startTuples.add(new TFTuple(0.0f, intercept));
            startTuples.add(new TFTuple(24.0f, intercept));
        }

        return;
    }

    /**
     * Calculates the coefficients for the linear interpolation using the lowerTuple
     * and higherTuples.
     */
    private void calcLinearCoeff() {

        m = (lowerTuple.getHour() - higherTuple.getHour()) / (lowerTuple.getIntensity() - higherTuple.getIntensity());
        c = lowerTuple.getIntensity() - m * lowerTuple.getHour();

        return;
    }

    private float calcIntensity(float time) {
        return m * time + c;
    }

    public float getIntensity(float time) {

        if (lowerTuple.getHour() <= time && time <= higherTuple.getHour()) {
            return calcIntensity(time);
        }

        // The interval need to be updated
        Iterator<TFTuple> tupleIter = tupleSet.iterator();

        while (tupleIter.hasNext()) {
            TFTuple nextTup = tupleIter.next();

            if (nextTup.getHour() <= time) {
                nextTup = lowerTuple;
                break;
            }
        }

        higherTuple = tupleIter.next();

        // Recalculate the linear coefficients
        calcLinearCoeff();

        return calcIntensity(time);
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