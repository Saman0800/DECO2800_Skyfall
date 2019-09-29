package deco2800.skyfall.managers;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {

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
        setMilestones();
    }

    /**
     * Sets goals to be achieved at each level
     */
    private void setMilestones() {
        buildingsTotal.clear();
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
}
