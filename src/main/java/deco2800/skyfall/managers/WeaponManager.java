package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;

import java.util.*;

public class WeaponManager extends TickableManager {

    // Map of how many of each weapon is available
    private Map<Weapon, Integer> weapons;

    // List of weapons being equipped
    private List<Weapon> equipped;

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
    public WeaponManager(Map<Weapon, Integer> weapons, List<Weapon> equipped) {
        this.weapons = weapons;
        this.equipped = equipped;
    }

    /**
     * Adds a weapon to the weapons map
     * @param item weapon being added
     */
    public void pickUpWeapon(Weapon item) {
        if (weapons.containsKey(item)) {
            weapons.replace(item, weapons.get(item),
                    weapons.get(item) + 1);
        } else {
            weapons.put(item, 1);
        }
    }

    /**
     * Removes a weapon from the weapons map
     * @param item weapon being dropped
     */
    public void dropWeapon(Weapon item) {
        if (!weapons.containsKey(item)) {
            return;
        }
        if (weapons.get(item) - 1 == 0) {
            weapons.remove(item, weapons.get(item));
        } else {
            weapons.replace(item, weapons.get(item),
                    weapons.get(item) - 1);
        }
    }

    /**
     * Removes multiples of a weapon from the weapons map
     * @param item item weapon being dropped
     * @param amount amount to remove
     */
    public void dropWeapons(Weapon item, int amount) {
        if (!weapons.containsKey(item) || amount > weapons.get(item)) {
            return;
        }
        if (weapons.get(item) - amount == 0) {
            weapons.remove(item, weapons.get(item));
        } else {
            weapons.replace(item, weapons.get(item),
                    weapons.get(item) - amount);
        }
    }

    /**
     * Gets amount of a particular weapon in weapons map
     * @param item item weapon being queried
     * @return number of items in weapons map
     */
    public int getWeaponAmount(Weapon item) {
        if (!weapons.containsKey(item)) {
            return 0;
        }
        return weapons.get(item);
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
    public Map<Weapon, Integer> getWeapons() {
        return new HashMap<>(weapons);
    }

    /**
     * Attempts to equip a weapon from the weapons map
     * @param item weapon being equipped
     */
    public void equipWeapon(Weapon item) {
        if (weapons.containsKey(item) && !equipped.contains(item) &&
                equipped.size() < MAX_EQUIPPED) {
            dropWeapon(item);
            equipped.add(item);
        }
    }

    /**
     * Attempts to unequip a weapon and return it to the weapons map
     * @param item weapon being unequipped
     */
    public void unequipWeapon(Weapon item) {
        if (equipped.contains(item)) {
            equipped.remove(item);
            pickUpWeapon(item);
        }
    }

    /**
     * Checks whether if item is in equipped list
     * @param item to check
     * @return true if item is equipped, false otherwise
     */
    public boolean isEquipped(Weapon item) {
        return equipped.contains(item);
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
    public List<Weapon> getEquipped() {
        return new ArrayList<>(equipped);
    }

    @Override
    public String toString() {
        List<String> weaponsNames = new ArrayList<>();
        List<Integer> weaponsAmounts = new ArrayList<>(weapons.values());
        Map<String, Integer> weaponString = new HashMap<>();
        StringBuilder equippedString = new StringBuilder();

        for (Weapon weapon: weapons.keySet()) {
            weaponsNames.add(weapon.getName());
        }

        for (int i = 0; i < weaponsAmounts.size(); i++) {
            weaponString.put(weaponsNames.get(i), weaponsAmounts.get(i));
        }

        for (int i = 0; i < MAX_EQUIPPED; i++) {
            equippedString.append(equipped.get(i).getName());
            if (i < MAX_EQUIPPED - 1){
                equippedString.append(", ");
            } else {
                equippedString.append(".");
            }
        }

        return "Weapons: " + weaponString.toString() + "\n" +
                "Equipped: " + equippedString.toString();
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }
}
