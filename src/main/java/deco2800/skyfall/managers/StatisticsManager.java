package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;

import java.util.*;

public class StatisticsManager  extends TickableManager {

    // Character of which the statistics manager is being used for
    public MainCharacter character;

    // Map recording kills of different enemies
    private Map<EnemyEntity, Integer> kills;

    // Amount of experience the character has from doing various things in
    // the game, when character gets enough experience they'll level up
    private int experience;

    // The amount of experience needed to level up
    private int experienceCap;

    // Number of times main character has been killed
    private int deaths;

    // Amount of in-game currency owned by the character
    private int money;

    public StatisticsManager(MainCharacter character) {
        this.character = character;
        this.kills = new HashMap<>();
        this.experience = 0;
        this.experienceCap = 20;
        this.deaths = 0;
        this.money = 0;
    }

    /**
     * Increases experience of character due to certain achievements such as
     * collecting weapons, inventory, money and getting kills
     */
    public void gainExperience() {
        if (this.character.getWeaponManager().getNumWeapons() % 10 == 0) {
            experience += 10;
        }

        if (this.character.getInventoryManager().getTotalAmount() % 10 == 0) {
            experience += 10;
        }

        if (this.getKills() % 10 == 0) {
            experience += 10;
        }

        if (this.getMoney() % 20 == 0) {
            experience += 10;
        }
    }

    /**
     * Decreases experience of character due sustained deaths
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
     * Level ups up if experience has reached experienceCap
     */
    public void LevelUp() {
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
    public int getAmountKilled(EnemyEntity enemy) {
        return kills.get(enemy);
    }

    /**
     * Add a killed enemy to the map
     * @param enemy enemy being recorded
     */
    public void recordKill(EnemyEntity enemy) {
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
        return this.character.getHealth();
    }

    /**
     * Gets the level of character
     * @return level of character
     */
    public int getLevel() {
        return this.character.getLevel();
    }

    /**
     * Gets the amount of times the character has dies
     * @return the amount of deaths of the character
     */
    public int getDeaths() {
        return this.deaths;
    }

    /**
     * Gets the amount of money the character has
     * @return the amount of money the character has
     */
    public int getMoney() {
        return this.money;
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }
}
