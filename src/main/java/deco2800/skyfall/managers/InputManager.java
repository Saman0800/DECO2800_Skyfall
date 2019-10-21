package deco2800.skyfall.managers;

import com.badlogic.gdx.InputProcessor;
import deco2800.skyfall.observers.KeyDownObserver;
import deco2800.skyfall.observers.KeyUpObserver;
import deco2800.skyfall.observers.MouseMovedObserver;
import deco2800.skyfall.observers.ScrollObserver;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.observers.TouchDraggedObserver;
import deco2800.skyfall.observers.TouchUpObserver;
import deco2800.skyfall.util.Vector2;
import java.util.ArrayList;

/**
 * Created by woody on 30-Jul-17.
 */
public class InputManager extends AbstractManager implements InputProcessor {

	private ArrayList<KeyDownObserver> keyDownListeners = new ArrayList<>();

	private ArrayList<KeyUpObserver> keyUpListeners = new ArrayList<>();

	private ArrayList<TouchDownObserver> touchDownListeners = new ArrayList<>();

	private ArrayList<TouchUpObserver> touchUpListeners = new ArrayList<>();

	private ArrayList<TouchDraggedObserver> touchDraggedListeners = new ArrayList<>();

	private ArrayList<MouseMovedObserver> mouseMovedListeners = new ArrayList<>();

	private ArrayList<ScrollObserver> scrollListeners = new ArrayList<>();

	private Vector2 mousePosition = new Vector2(0, 0);

	/**
	 * Adds an observer to observe the keyDown action
	 * @param observer the observer to add
     */
	public void addKeyDownListener(KeyDownObserver observer) {
		keyDownListeners.add(observer);
	}

	/**
	 * Adds an observer to observe the keyUp action
	 * @param observer the observer to add
	 */
	public void addKeyUpListener(KeyUpObserver observer) {
		keyUpListeners.add(observer);
	}

	/**
	 * Adds an observer to observe the touchDown action
	 * @param observer the observer to add
	 */
	public void addTouchDownListener(TouchDownObserver observer) {
		touchDownListeners.add(observer);
	}

	@Override
	public boolean keyDown(int keycode) {
		for (KeyDownObserver observer : keyDownListeners) {
			observer.notifyKeyDown(keycode);
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		for (KeyUpObserver observer : keyUpListeners) {
			observer.notifyKeyUp(keycode);
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for (TouchDownObserver observer : touchDownListeners) {
			observer.notifyTouchDown(screenX, screenY, pointer, button);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for (TouchUpObserver observer : touchUpListeners) {
			observer.notifyTouchUp(screenX, screenY, pointer, button);
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		for (TouchDraggedObserver observer : touchDraggedListeners) {
			observer.notifyTouchDragged(screenX, screenY, pointer);
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		for (MouseMovedObserver observer : mouseMovedListeners) {
			observer.notifyMouseMoved(screenX, screenY);
		}

		mousePosition.setX(screenX);
		mousePosition.setY(screenY);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		for (ScrollObserver observer : scrollListeners) {
			observer.notifyScrolled(amount);
		}
		return true;
	}

	public Vector2 getMousePosition() {
		return mousePosition;
	}
}
