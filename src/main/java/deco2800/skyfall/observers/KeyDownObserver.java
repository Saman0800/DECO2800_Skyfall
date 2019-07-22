package deco2800.skyfall.observers;

/**
 * Created by woody on 30-Jul-17.
 */
@FunctionalInterface
public interface KeyDownObserver {

	/**
	 * notifies the observer of a key being pressed down
	 * @param keycode the key being pressed
     */
	void notifyKeyDown(int keycode);


}
