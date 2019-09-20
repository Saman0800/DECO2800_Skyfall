package deco2800.skyfall.util;

/**
 * A {@link FunctionalInterface} which represents an action to be taken which does not take parameters nor return a
 * result.
 */
@FunctionalInterface
public interface Action {
    /**
     * The action to be performed.
     */
    void perform();
}
