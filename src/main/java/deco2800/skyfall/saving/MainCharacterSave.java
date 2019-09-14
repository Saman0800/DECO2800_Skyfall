package deco2800.skyfall.saving;

import deco2800.skyfall.entities.MainCharacter;

public class MainCharacterSave {

    private int saveID;
    private int mainCharacterID;

    private int foodLevel;
    // TODO see if foodAccum shoudl be stored as well

    private String inventory;
    private String weapons;

    public MainCharacterSave(MainCharacter character, SaveInfo saveInfo) {
        this.saveID = saveInfo.getSaveID();
        this.mainCharacterID = character.getSaveID();
        this.foodLevel = character.getFoodLevel();

        this.inventory = character.getInventoryManager().toString();
        this.weapons = character.getWeaponManager().toString();
    }

}
