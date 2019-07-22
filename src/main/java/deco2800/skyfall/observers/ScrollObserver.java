package deco2800.skyfall.observers;

/**
 * Created by woody on 30-Jul-17.
 */
@FunctionalInterface
public interface ScrollObserver {

	/**
	 * Notifies the observes of a scroll action
	 * @param amount the amount scrolled
     */
	void notifyScrolled(int amount);

}
