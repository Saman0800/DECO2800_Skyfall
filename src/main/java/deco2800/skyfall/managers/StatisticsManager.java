package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;

import java.util.*;

// TODO Figure out why "Cannot access MainCharacter"

public class StatisticsManager {

    // Character of which the statistics manager is being used for
    private MainCharacter character;

    // Map recording kills of different enemies
    private Map<EnemyEntity, Integer> kills;

    // Number of times main character has been killed
    private int deaths;

    public StatisticsManager(MainCharacter character) {
//        this.character = character;
        this.kills = new TreeMap<>();
        this.deaths = 0;
    }

    /**
     * Level up by 2 every 10 kills for the character
     */
    public void killsLevelUp() {
        if (this.getKills() % 10 == 0) {
//            this.character.changeLevel(2);
        }
    }

    /**
     * Lose level for every 5 deaths
     */
    public void deathLoseLevel() {
        if (deaths != 0 && deaths % 5 == 0) {
//            this.character.changeLevel(-2);
        }
    }

    /**
     * Levels up every time character picks up 10 weapons
     */
    public void weaponLevelUp() {
//        if (this.character.getWeaponManager().getNumWeapons % 10 == 0) {
//            this.character.changeLevel(2);
//        }
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
//        return this.character.getHealth();
        return 0;
    }
}
