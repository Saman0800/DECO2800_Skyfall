package deco2800.skyfall.observers;

public interface DayNightObserver {

    /**
     * Notifies the observer when day changes to night or vice versa
     * @param isDay Whether it is day or not (True if it is daytime, False if it is night time)
     */
    void notifyDayNightUpdate(boolean isDay);
}
