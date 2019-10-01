package deco2800.skyfall.managers;

import deco2800.skyfall.entities.MainCharacter;

import java.util.ArrayList;
import java.util.List;

public class QuestManager extends AbstractManager{

    //Current level of quest
    private int questLevel;

    //Required gold amount
    private int goldTotal;

    //Required wood amount
    private int woodTotal;

    //Required stone amount
    private int stoneTotal;

    //Required metal amount
    private int metalTotal;

    //Required buildings
    private List<String> buildingsTotal;

    //Level 1 buildings
    private List<String> levelOneBuildings = new ArrayList<>();

    //Level 2 buildings
    private List<String> levelTwoBuildings = new ArrayList<>();

    //Player character
    private MainCharacter player;

    /**
     * Constructor, sets up beginning of game goals
     */
    public QuestManager() {
        this.questLevel = 1;
        buildingsTotal = new ArrayList<>();
        levelOneBuildings.add("Cabin");
        levelOneBuildings.add("WatchTower");
        levelTwoBuildings.add("WatchTower");
        levelTwoBuildings.add("StorageUnit");
        levelTwoBuildings.add("TownCentre");
        player = MainCharacter.getInstance();
        setMilestones();
    }

    /**
     * Sets goals to be achieved at each level
     */
    private void setMilestones() {
        switch (questLevel) {
            case 1 :
                setGoldTotal(300);
                setWoodTotal(50);
                setStoneTotal(50);
                setMetalTotal(30);
                setBuildingsTotal(levelOneBuildings);
                break;
            case 2 :
                setGoldTotal(600);
                setWoodTotal(100);
                setStoneTotal(100);
                setMetalTotal(60);
                setBuildingsTotal(levelTwoBuildings);
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
    public void setBuildingsTotal(List<String> buildings) {
        buildingsTotal.clear();
        buildingsTotal.addAll(buildings);
    }

    /**
     * Returns the current required buildings
     * @return The current required buildings
     */
    public List<String> getBuildingsTotal() {
        return buildingsTotal;
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
                .getAmount("Stone");
        return (currentStone >= getStoneTotal());
    }

    /**
     * Checks if amount of metal in player inventory meets metal milestone amount
     * @return True if amount equals or exceeds milestone, False if not
     */
    public boolean checkMetal() {
        int currentMetal = player.getInventoryManager()
                .getAmount("Metal");
        return (currentMetal >= getMetalTotal());
    }
}
