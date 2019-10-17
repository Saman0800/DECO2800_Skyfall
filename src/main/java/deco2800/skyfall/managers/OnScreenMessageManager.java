package deco2800.skyfall.managers;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.handlers.KeyboardManager;
import deco2800.skyfall.observers.KeyTypedObserver;

import java.util.ArrayList;
import java.util.List;

public class OnScreenMessageManager extends AbstractManager implements KeyTypedObserver {
	private List<String> messages = new ArrayList<>();
	boolean isTyping = false;
	String unsentMessage = "";

	/**handles a set time invocation
	 * Silently fails if something goes wrong
	 * @param unsentMessage the recieved message, needs to be set_time*/
	private String handleSetTime(String unsentMessage) {
		String[] split = unsentMessage.split("@", 3);
		if (split.length != 3) {
			return "Invalid usauge";
		}
		int min;
		int hours;

		try {
			hours = Integer.parseInt(split[1]);
			min = Integer.parseInt(split[2]);
		}
		catch (NumberFormatException e) {
			return "Invalid hour/min";
		}
		GameManager.get().getManager(EnvironmentManager.class).setTime(hours, min);
		return "Time set";
	}

	public OnScreenMessageManager() {
		GameManager.get().getManager(KeyboardManager.class).registerForKeyTyped(this);
	}

	public List<String> getMessages() {
		return messages;
	}

	public void addMessage(String message) {
		if (messages.size() > 20) {
			messages.remove(0);
		}
		messages.add(message);
	}

	public boolean isTyping() {
		return isTyping;
	}

	public String getUnsentMessage() {
		if (unsentMessage.equals("")) {
			return "Type your message";
		}
		return unsentMessage;
	}

	@Override
	public void notifyKeyTyped(char character) {
		if (character == 't' && !isTyping) {
			isTyping = true;
			GameManager.get().getCamera().setPotate(true);
			return;
		}

		if (isTyping) {
			if (character == '`') {
				isTyping = false;
				GameManager.get().getCamera().setPotate(false);
			} else if (character == '\b') {
				if (unsentMessage.length() > 0) {
					unsentMessage = unsentMessage.substring(0, unsentMessage.length() - 1); // Backspace
				}
			} else if (character == '\r') {
				isTyping = false;
				if (unsentMessage.startsWith("/duck")) { // enable GOD mode
					for (int i = 0; i < 1000; ++i) {
						GameManager.get().getWorld().addEntity(new MainCharacter(0f, 0f, 0.05f, "DUCK",1));
					}

				} else	if (unsentMessage.startsWith("/1")) { // enable GOD mode
					GameManager.get().getWorld().addEntity(new MainCharacter(0f, 0f, 0.05f,"GOD",10000000));
				} else if (unsentMessage.startsWith("/inventory")) {
					// Display inventory in the console
					this.addMessage(String.format(GameManager.getManagerFromInstance(InventoryManager.class).toString()));
				}  else	if (unsentMessage.startsWith("/set_time")) { // set time, as set_time@hh@mm
					this.addMessage(handleSetTime(unsentMessage));
				}
				GameManager.get().getCamera().setPotate(false);
				unsentMessage = "";
			} else {
				// Accept input
				if (character < '!' || character > 'z') {
					return;
				}
				unsentMessage += character;
			}
		}
	}
}
