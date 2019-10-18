package deco2800.skyfall.managers;

import deco2800.skyfall.buildings.*;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Blueprint;

import java.util.*;

public class QuestManager extends TickableManager {

    public static final String SWORD = "sword";
    public static final String SPEAR = "spear";
    public static final String STONE = "Stone";
    public static final String METAL = "Metal";
    // Current level of quest
    private int questLevel;

    // Required gold amount
    private int goldTotal;

    // Required wood amount
    private int woodTotal;

    // Required stone amount
    private int stoneTotal;

    // Required metal amount
    private int metalTotal;

    // Require sword amount
    private int swordTotal;

    // Require spear amount
    private int spearTotal;

    // Require axe amount
    private int axeTotal;

    // Require bow amount
    private int bowTotal;

    // Required buildings
    private List<BuildingType> buildingsTotal;

    // Level 1 buildings
    private List<BuildingType> levelOneBuildings = new ArrayList<>();

    // Level 2 buildings
    private List<BuildingType> levelTwoBuildings = new ArrayList<>();

    //Level 3 buildings
    private List<BuildingType> levelThreeBuildings = new ArrayList<>();

    // Player character
    private MainCharacter player;

    // Quest milestones achieved or not
    private boolean questSuccess;

    // Updates this every checkBuildings
    private int buildingsNum;

    // List of buildings to be placed
    private List<BuildingType> buildingsPlaced = new ArrayList<>();

    /**
     * Constructor, sets up beginning of game goals
     */
    public QuestManager() {
        this.questLevel = 1;
        questSuccess = false;
        buildingsTotal = new ArrayList<>();
        levelOneBuildings.add(BuildingType.CASTLE);
        player = MainCharacter.getInstance();
        setMilestones();
    }

    /**
     * Sets goals to be achieved at each level
     */
    private void setMilestones() {
        //inventory numbers need to be reset for player testing and actual game release
        //reduced for ease of testing in game
        switch (questLevel) {
            case 1 :
                setGoldTotal(10);
                setWoodTotal(10);
                setStoneTotal(10);
                setMetalTotal(10);
                setBuildingsTotal(levelOneBuildings);
                setWeaponTotal(SWORD, 2);
                setWeaponTotal("bow", 0);
                setWeaponTotal(SPEAR, 0);
                setWeaponTotal("axe", 0);
                break;
            case 2 :
                setGoldTotal(20);
                setWoodTotal(20);
                setStoneTotal(20);
                setMetalTotal(20);
                setBuildingsTotal(levelTwoBuildings);
                setWeaponTotal(SWORD, 3);
                setWeaponTotal("bow", 3);
                setWeaponTotal(SPEAR, 0);
                setWeaponTotal("axe", 0);
                break;
            case 3 :
                setGoldTotal(30);
                setWoodTotal(30);
                setStoneTotal(30);
                setMetalTotal(30);
                setBuildingsTotal(levelThreeBuildings);
                setWeaponTotal(SWORD, 4);
                setWeaponTotal("bow", 4);
                setWeaponTotal(SPEAR, 4);
                setWeaponTotal("axe", 4);
                break;
            default :
                break;
        }
    }

    /* Getters and Setters */

    /**
     * Sets quest level, updates milestones
     * @param level The level to be set
     */
    public void setQuestLevel(int level) {
        questLevel = level;
        setMilestones();
    }

    /**
     * Returns the current quest level
     * @return The current quest level
     */
    public int getQuestLevel() {
        return questLevel;
    }

    /**
     * Sets the required gold amount
     * @param amount The amount to be set
     */
    public void setGoldTotal(int amount) {
        goldTotal = amount;
    }

    /**
     * Returns the current required gold amount
     * @return The current required gold amount
     */
    public int getGoldTotal() {
        return goldTotal;
    }

    /**
     * Sets the required wood amount
     * @param amount The amount to be set
     */
    public void setWoodTotal(int amount) {
        woodTotal = amount;
    }

    /**
     * Returns the current required wood amount
     * @return The current required wood amount
     */
    public int getWoodTotal() {
        return woodTotal;
    }

    /**
     * Sets the required stone amount
     * @param amount The amount to be set
     */
    public void setStoneTotal(int amount) {
        stoneTotal = amount;
    }

    /**
     * Returns the current required stone amount
     * @return The current required stone amount
     */
    public int getStoneTotal() {
        return stoneTotal;
    }

    /**
     * Sets the required metal amount
     * @param amount The amount to be set
     */
    public void setMetalTotal(int amount) {
        metalTotal = amount;
    }

    /**
     * Returns the current required metal amount
     * @return The current required metal amount
     */
    public int getMetalTotal() {
        return metalTotal;
    }

    /**
     * Sets the required buildings to be constructed
     * @param buildings The buildings to be constructed
     */
    public void setBuildingsTotal(List<BuildingType> buildings) {
        buildingsTotal.clear();
        buildingsTotal.addAll(buildings);
    }

    /**
     * Returns the current required buildings
     * @return The current required buildings
     */
    public List<BuildingType> getBuildingsTotal() {
        return buildingsTotal;
    }

    /**
     * Sets the required weapons to be collected
     * @param weapon the weapon being set
     * @param amount the amount being set
     */
    public void setWeaponTotal(String weapon, int amount) {
        switch (weapon) {
            case SWORD:
                this.swordTotal = amount;
                break;
            case SPEAR:
                this.spearTotal = amount;
                break;
            case "bow":
                this.bowTotal = amount;
                break;
            case "axe":
                this.axeTotal = amount;
                break;
            default:
                break;
        }
    }

    /**
     * Get number of weapons to collect
     * @param weapon weapon to check
     * @return the number of weapons to be collected to complete quest
     */
    public int getWeaponsTotal(String weapon) {
        switch (weapon) {
            case SWORD:
                return this.swordTotal;
            case SPEAR:
                return this.spearTotal;
            case "bow":
                return this.bowTotal;
            case "axe":
                return this.axeTotal;
            default:
                break;
        }
        return 0;
    }

    /**
     * Checks if amount of gold in player inventory meets gold milestone amount
     * @return True if amount equals or exceeds milestone, False if not
     */
    public boolean checkGold() {
        int currentGold = player.getGoldPouchTotalValue();
        return (currentGold >= getGoldTotal());
    }

    /**
     * Checks if amount of wood in player inventory meets wood milestone amount
     * @return True if amount equals or exceeds milestone, False if not
     */
    public boolean checkWood() {
        int currentWood = player.getInventoryManager()
                .getAmount("Wood");
        return (currentWood >= getWoodTotal());
    }

    /**
     * Checks if amount of stone in player inventory meets stone milestone amount
     * @return True if amount equals or exceeds milestone, False if not
     */
    public boolean checkStone() {
        int currentStone = player.getInventoryManager()
                .getAmount(STONE);
        return (currentStone >= getStoneTotal());
    }

    /**
     * Checks if amount of metal in player inventory meets metal milestone amount
     * @return True if amount equals or exceeds milestone, False if not
     */
    public boolean checkMetal() {
        int currentMetal = player.getInventoryManager()
                .getAmount(METAL);
        return (currentMetal >= getMetalTotal());
    }

    /**
     * Adds a building to be built for quest
     * @param newBuilding building to be added
     */
    public void addBuilding(BuildingType newBuilding) {
        buildingsPlaced.add(newBuilding);
    }

    /**
     * Checks if all required buildings have been placed in the world
     * @return True if all buildings are placed, False if not
     */
    public boolean checkBuildings() {
        buildingsNum = buildingsPlaced.size();
        return buildingsPlaced.containsAll(buildingsTotal);
    }

    /**
     * Get the building number
     * @return buildingsNum
     */
    public int getBuildingsNum() {
        return buildingsNum;
    }

    /**
     * Check if the number of weapons to be collected has been collected
     * @param weapon weapon to check
     * @return true or false
     */
    public boolean checkWeapons(String weapon) {
        int currentWeapon = player.getInventoryManager().getAmount(weapon);

        return (currentWeapon >= getWeaponsTotal(weapon));
    }


    /**
     * Checks if milestones are met each game tick
     * @param i Game tick
     */
    @Override
    public void onTick(long i) {
        checkGold();
        checkStone();
        checkWood();
        checkMetal();
        checkBuildings();
        checkWeapons(SWORD);
        checkWeapons(SPEAR);
        checkWeapons("axe");
        checkWeapons("bow");

        if ((checkGold() && checkStone() && checkWood()
                && checkMetal() && checkBuildings()
                && checkWeapons(SWORD) && checkWeapons(SPEAR)
                && checkWeapons("axe") && checkWeapons("bow"))
                || (questSuccess) ) {
            questSuccess = true;
            // Other quest success stuff here, or quest success method
        }
    }

    /**
     * Access player for testing
     * @return main character entity
     */
    protected MainCharacter getPlayer() {
        return player;
    }

    /**
     * Whether the quest has finished
     * @return true of false
     */
    public boolean questFinished() {
        return questSuccess;
    }

    /**
     * Get Biome of character
     * @return String of biome
     */
    public String getBiome() {
        return GameManager.get().getWorld().getBiomes().get(0).getBiomeName().replace("_", " ").toUpperCase();
    }

    /**
     * Get the number of items for quest collected
     * @return amount of needed items collected
     */
    public int collectNum() {
        int amt = 0;

        if (checkWood()) {
            amt += 1;
        }

        if (checkStone()) {
            amt += 1;
        }

        if (checkMetal()) {
            amt += 1;
        }

        if (checkGold()) {
            amt += 1;
        }


        if (checkWeapons(SWORD) && swordTotal > 0) {
            amt += 1;
        }

        if (checkWeapons(SPEAR) && spearTotal > 0) {
            amt += 1;
        }

        if (checkWeapons("axe") && axeTotal > 0) {
            amt += 1;
        }

        if (checkWeapons("bow") && bowTotal > 0) {
            amt += 1;
        }

        return amt;
    }

    /**
     * Resets the current quest of the player
     */
    public void resetQuest() {
        // Get and reset the current level
        int currentLevel = this.getQuestLevel();
        this.setQuestLevel(currentLevel);

        // Get amount of building items in inventory
        int currentMetal = player.getInventoryManager()
                .getAmount(METAL);

        int currentWood = player.getInventoryManager()
                .getAmount("Wood");

        int currentStone = player.getInventoryManager()
                .getAmount(STONE);

        // Reset the inventory
        buildingsNum = 0;
        questSuccess = false;
        getPlayer().removeAllGold();
        getPlayer().getInventoryManager().dropMultiple(METAL, currentMetal);
        getPlayer().getInventoryManager().dropMultiple(STONE, currentStone);
        getPlayer().getInventoryManager().dropMultiple("Wood", currentWood);
        getPlayer().getInventoryManager().dropAll(SWORD);
        getPlayer().getInventoryManager().dropAll(SPEAR);
        getPlayer().getInventoryManager().dropAll("axe");
        getPlayer().getInventoryManager().dropAll("bow");
    }

    /**
     * See if a blueprint is learned
     * @return true or false
     */
    public boolean getBlueprintLearned() {
        Iterator<Blueprint> iter =
                getPlayer().getBlueprintsLearned().iterator();

        while (iter.hasNext()) {
            if (iter.next() instanceof AbstractPortal) {
                return true;
            }
        }

        return false;
    }

    public int weaponsNum() {
        return ((axeTotal > 0) ? 1 : 0) + ((swordTotal > 0) ? 1 : 0) + ((spearTotal > 0) ? 1 : 0) + ((bowTotal > 0) ? 1 : 0);
    }
}

    /*
    Stuff to be done:
    - track player inventory
    - track buildings placed in the world
    - confirm milestones have been met
    - reset inventory/health upon starting new level?
    - how to set quest level upon levelling up?
    - figure out how portal activation fits in with all this
     */
