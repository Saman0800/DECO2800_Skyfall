package deco2800.skyfall.managers;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.enemies.Abductor;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.entities.enemies.Heavy;
import deco2800.skyfall.entities.enemies.Medium;
import deco2800.skyfall.entities.enemies.Scout;
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
	 * @param unsentMessage the recieved message, needs to be /set_time*/
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

	/**
	 * Spawns enemy on player location
	 * @param unsentMessage the recieved message, must start /spawn_enemy
	 */
	private String handleSpawnEnemy(String unsentMessage) {
		String[] split = unsentMessage.split("@", 2);
		if (split.length != 2) {
			return "Invalid usauge";
		}
		Enemy enemyToSpawn = null;
		float row = MainCharacter.getInstance().getRow();
		float col = MainCharacter.getInstance().getCol();
		String biome = "Forest";
		switch (split[1]) {
			case "abductor":
				enemyToSpawn = new Abductor(col, row, 1.0f, biome);
				break;
			case "heavy":
				enemyToSpawn = new Heavy(col, row, 1.0f, biome);
				break;
			case "scout":
				enemyToSpawn = new Scout(col, row, 1.0f, biome);
				break;
			case "medium":
				enemyToSpawn = new Medium(col, row, 1.0f, biome);
				break;
			default:
				return "Invalid option for spawning";
		}
		GameManager.get().getWorld().addEntity(enemyToSpawn);
		return "Spawned " + split[1];
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
				handleCarriageReturn();
			} else {
				// Accept input
				if (character < '!' || character > 'z') {
					return;
				}
				unsentMessage += character;
			}
		}
	}

	/**
	 * Run when the carriage return character (\r) is inputted
	 */
	private void handleCarriageReturn() {
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
		} else if (unsentMessage.startsWith("/enemy")) {
			this.addMessage(handleSpawnEnemy(unsentMessage));
		}
		GameManager.get().getCamera().setPotate(false);
		unsentMessage = "";
	}
}
