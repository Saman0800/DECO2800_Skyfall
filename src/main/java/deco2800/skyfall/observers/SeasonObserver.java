package deco2800.skyfall.observers;

public interface SeasonObserver {

    /**
     * Notifies the observer when in-game season progresses to the next season
     * @param season The new season
     */
    void notifySeasonUpdate(String season);
}
