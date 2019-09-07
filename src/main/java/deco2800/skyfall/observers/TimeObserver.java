package deco2800.skyfall.observers;

public interface TimeObserver {

    /**
     * Notifies the observer when in-game time progresses one hour
     * @param i The current hour
     */
    void notifyTimeUpdate(long i);
}
