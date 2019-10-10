package deco2800.skyfall.managers;

import deco2800.skyfall.buildings.AbstractPortal;
import deco2800.skyfall.buildings.BuildingEntity;
import deco2800.skyfall.buildings.BuildingType;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Blueprint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestManager extends TickableManager{

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
    private List<BuildingType> buildingsTotal;

    //Level 1 buildings
    private List<BuildingType> levelOneBuildings = new ArrayList<>();

    //Level 2 buildings
    private List<BuildingType> levelTwoBuildings = new ArrayList<>();

    //Player character
    private MainCharacter player;

    //Quest milestones achieved or not
    private boolean questSuccess;

    //Updates this every checkBuildings
    private int buildingsNum;

    private List<BuildingType> buildingsPlaced = new ArrayList<>();
    /**
     * Constructor, sets up beginning of game goals
     */
    public QuestManager() {
        this.questLevel = 1;
        questSuccess = false;
        buildingsTotal = new ArrayList<>();
        levelOneBuildings.add(BuildingType.CASTLE);
        //levelOneBuildings.add("WatchTower");
        player = MainCharacter.getInstance();
        setMilestones();
    }

    /**
     * Sets goals to be achieved at each level
     */
    private void setMilestones() {
        switch (questLevel) {
            case 1 :
                setGoldTotal(1);
                setWoodTotal(1);
                setStoneTotal(1);
                setMetalTotal(1);
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

    public void addBuilding(BuildingType newBuilding) {
        buildingsPlaced.add(newBuilding);
    }
/*
     * Checks if all required buildings have been placed in the world
     * @return True if all buildings are placed, False if not
     */
    public boolean checkBuildings() {
        return buildingsPlaced.containsAll(buildingsTotal);
    }


    public int getBuildingsNum() {
        return buildingsNum;
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

        if ((checkGold() && checkStone() && checkWood() &&
        checkMetal() && checkBuildings()) || (questSuccess) ) {
            questSuccess = true;
            //Other quest success stuff here, or quest success method
        }
    }


    /**
     * Access player for testing
     * @return main character entity
     */
    protected MainCharacter getPlayer() {
        return player;
    }

    public boolean questFinished() {
        return questSuccess;
    }

    public String getBiome() {
        return GameManager.get().getWorld().getBiomes().get(0).getBiomeName().replaceAll("_", " ").toUpperCase();
    }

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
                .getAmount("Metal");

        int currentWood = player.getInventoryManager()
                .getAmount("Wood");

        int currentStone = player.getInventoryManager()
                .getAmount("Stone");

        // Reset the inventory
        buildingsNum = 0;
        questSuccess = false;
        getPlayer().removeAllGold();
        getPlayer().getInventoryManager().dropMultiple("Metal", currentMetal);
        getPlayer().getInventoryManager().dropMultiple("Stone", currentStone);
        getPlayer().getInventoryManager().dropMultiple("Wood", currentWood);
    }


    public boolean getBlueprintLearned() {
        Iterator<Blueprint> iter = getPlayer().getBlueprintsLearned().iterator();
        while (iter.hasNext()) {
            if (iter.next() instanceof AbstractPortal) {
                return true;
            }
        }
        return  false;
    }
}
