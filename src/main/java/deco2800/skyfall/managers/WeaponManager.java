package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;

import java.util.*;

public class WeaponManager extends TickableManager {

    // Map of how many of each weapon is available
    private Map<String, Integer> weapons;

    // List of weapons being equipped
    private List<String> equipped;

    // Maximum number of equipped weapons
    private static final int MAX_EQUIPPED = 2;

    /**
     * Creates a new Weapon Manager and adds default items to the weapons map
     * Also initialises an empty equipped weapons list
     */
    public WeaponManager() {
        this.weapons = new HashMap<>();
        this.equipped = new ArrayList<>();
    }

    /**
     * Creates a new Weapon Manager from a given weapons map and equipped list
     */
    public WeaponManager(Map<String, Integer> weapons, List<String> equipped) {
        this.weapons = weapons;
        this.equipped = equipped;
    }

    /**
     * Adds a weapon to the weapons map
     * @param item weapon being added
     */
    public void pickUpWeapon(Weapon item) {
        if (weapons.containsKey(item.getName())) {
            weapons.replace(item.getName(), weapons.get(item.getName()),
                    weapons.get(item.getName()) + 1);

        } else {
            weapons.put(item.getName(), 1);
        }
    }

    /**
     * Removes a weapon from the weapons map
     * @param item weapon being dropped
     */
    public void dropWeapon(Weapon item) {
        if (!weapons.containsKey(item.getName())) {
            return;
        }
        if (weapons.get(item.getName()) - 1 == 0) {
            weapons.remove(item.getName(), weapons.get(item.getName()));
        } else {
            weapons.replace(item.getName(), weapons.get(item.getName()),
                    weapons.get(item.getName()) - 1);
        }
    }

    /**
     * Removes multiples of a weapon from the weapons map
     * @param item item weapon being dropped
     * @param amount amount to remove
     */
    public void dropWeapons(Weapon item, int amount) {
        if (!weapons.containsKey(item.getName())
                || amount > weapons.get(item.getName())) {
            return;
        }
        if (weapons.get(item.getName()) - amount == 0) {
            weapons.remove(item.getName(), weapons.get(item.getName()));
        } else {
            weapons.replace(item.getName(), weapons.get(item.getName()),
                    weapons.get(item.getName()) - amount);
        }
    }

    /**
     * Gets amount of a particular weapon in weapons map
     * @param item item weapon being queried
     * @return number of items in weapons map
     */
    public int getWeaponAmount(Weapon item) {
        if (!weapons.containsKey(item.getName())) {
            return 0;
        }
        return weapons.get(item.getName());
    }

    /**
     * Gets the total number of weapons in weapons map
     * @return number of total weapons
     */
    public int getNumWeapons() {
        List<Integer> weaponAmounts = new ArrayList<>(weapons.values());
        int weaponTotal = 0;

        for (Integer amount : weaponAmounts) {
            weaponTotal += amount;
        }

        return weaponTotal;
    }

    /**
     * Get a copy of the weapons map
     * Modifying the returned map shouldn't affect the internal state of class
     * @return weapons map
     */
    public Map<String, Integer> getWeapons() {
        return new HashMap<>(weapons);
    }

    /**
     * Attempts to equip a weapon from the weapons map
     * @param item weapon being equipped
     */
    public void equipWeapon(Weapon item) {
        if (weapons.containsKey(item.getName()) &&
                !equipped.contains(item.getName()) &&
                equipped.size() < MAX_EQUIPPED) {
            dropWeapon(item);
            equipped.add(item.getName());
        }
    }

    /**
     * Attempts to unequip a weapon and return it to the weapons map
     * @param item weapon being unequipped
     */
    public void unequipWeapon(Weapon item) {
        if (equipped.contains(item.getName())) {
            equipped.remove(item.getName());
            pickUpWeapon(item);
        }
    }

    /**
     * Checks whether if item is in equipped list
     * @param item to check
     * @return true if item is equipped, false otherwise
     */
    public boolean isEquipped(Weapon item) {
        return equipped.contains(item.getName());
    }

    /**
     * Gets the number of weapons currently equipped
     * @return number of items in equipped list
     */
    public int getNumEquipped() {
        return equipped.size();
    }

    /**
     * Get a copy of the equipped weapons list
     * Modifying the returned list shouldn't affect the internal state of class
     * @return equipped list
     */
    public List<String> getEquipped() {
        return new ArrayList<>(equipped);
    }

    @Override
    /**
     * Returns a string representation of the class showing amount of weapons
     * and equipped weapons
     */
    public String toString() {
        StringBuilder equippedString = new StringBuilder();

        if (this.getNumEquipped() == 0) {
            equippedString.append("No Weapons.");
        } else {
            for (int i = 0; i < MAX_EQUIPPED; i++) {
                equippedString.append(equipped.get(i));
                if (i < MAX_EQUIPPED - 1) {
                    equippedString.append(", ");
                } else {
                    equippedString.append(".");
                }
            }
        }

        return "Weapons: " + weapons.toString() + "\n" +
                "Equipped: " + equippedString.toString();
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }
}
