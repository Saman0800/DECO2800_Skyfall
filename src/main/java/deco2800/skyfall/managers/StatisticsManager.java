package deco2800.skyfall.managers;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.enemies.Enemy;
import java.util.HashMap;
import java.util.Map;

public class StatisticsManager  extends TickableManager {

    // Character of which the statistics manager is being used for
    private MainCharacter character;

    // Map recording kills of different enemies
    private Map<Enemy, Integer> kills;

    // Amount of experience the character has from doing various things in
    // the game, when character gets enough experience they'll level up
    private int experience;

    // The amount of experience needed to level up
    private int experienceCap;

    public StatisticsManager(MainCharacter character) {
        this.character = character;
        this.kills = new HashMap<>();
        this.experience = 0;
        this.experienceCap = 20;
    }

    /**
     * Gets the character for the statistics manager
     * @return the character being managed
     */
    public MainCharacter getCharacter() {
        return this.character;
    }

    /**
     * Gets the inventory manager for character
     * @return the character's inventory manager
     */
    public InventoryManager getInventory() {
        return this.getCharacter().getInventoryManager();
    }

    /**
     * Increases experience of character due to certain achievements such as
     * collecting weapons, inventory, money and getting kills
     */
    public void gainExperience() {
        // Every 10 inventory items collected by the character will give them
        // 10 experience points
        if (this.getInventory().getTotalAmount() != 0 &&
                this.getInventory().getTotalAmount() % 10 == 0) {
            experience += 10;
        }

        // Every 10 enemies killed by the character will given them 10
        // experience points
        if (this.getKills() != 0 && this.getKills() % 10 == 0) {
            experience += 10;
        }

        // Every 100 gold collected by the character will given them 10
        // experience points
        if (this.getMoney() != 0 && this.getMoney() % 100 == 0) {
            experience += 10;
        }
    }

    /**
     * Decreases experience by 5 of character due every sustained 5 deaths
     */
    public void loseExperience() {
        if (this.getDeaths() != 0 && this.getDeaths() % 5 == 0) {
            experience -= 5;
        }
    }

    /**
     * Gets the current experience gained by the character
     * @return current experience of the character
     */
    public int getExperience() {
        return this.experience;
    }

    /**
     * Gets the amount of experience needed to level up
     * @return amount of experience needed to level up
     */
    public int getExperienceCap() {
        return this.experienceCap;
    }

    /**
     * Level ups up if experience has reached experienceCap
     */
    public void levelUp() {
        if (this.getExperience() >= experienceCap) {
            this.experience -= experienceCap;
            experienceCap += 20;
            this.character.changeLevel(1);
        }
    }

    /**
     * Lose level if experience is less than 0
     */
    public void loseLevel() {
        if (getExperience() < 0) {
            this.experience = 0;
            this.character.changeLevel(-1);
        }
    }

    /**
     * Gets the level of character
     * @return level of character
     */
    public int getLevel() {
        return this.character.getLevel();
    }

    /**
     * Gets the total number of enemies killed by character
     * @return number of kills
     */
    public int getKills() {
        int numKills = 0;

        for (Integer kill : kills.values()) {
            numKills += kill;
        }

        return numKills;
    }

    /**
     * Get the number of kills for a certain enemy
     * @param enemy enemy being queried
     * @return number of killed enemy
     */
    public int getAmountKilled(Enemy enemy) {
        return kills.get(enemy);
    }

    /**
     * Add a killed enemy to the map
     * @param enemy enemy being recorded
     */
    public void recordKill(Enemy enemy) {
        if (!kills.containsKey(enemy)) {
            kills.put(enemy, 1);
        } else {
            kills.replace(enemy, kills.get(enemy) + 1);
        }
    }

    /**
     * Gets the health of character
     * @return health of character
     */
    public int getHealth() {
        return this.getCharacter().getHealth();
    }

    /**
     * Gets the amount of times the character has dies
     * @return the amount of deaths of the character
     */
    public int getDeaths() {
        return this.getCharacter().getDeaths();
    }

    /**
     * Gets the amount of money the character has
     * @return the amount of money the character has
     */
    public int getMoney() {
        return this.getCharacter().getGoldPouchTotalValue();
    }

    public int getMana() {
        return this.getCharacter().getMana();
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }
}
