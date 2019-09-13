package deco2800.skyfall.resources.items;

import com.badlogic.gdx.Game;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.ManufacturedResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/***
 * A research table item. the player can use a research table to buy blueprints
 * with which the player could create items and build bases
 */
public class ResearchTable extends ManufacturedResources implements Blueprint,
        Item {

    //the gold cost to buy a blueprint
    private  int goldCost;

    private final Logger logger = LoggerFactory.getLogger(MainCharacter.class);

    //a map of all the required resources to create this item
    private Map<String, Integer> allRequirements;

    //the player inventory
    private InventoryManager playerInvenotry;

    //the required resources to create a research table
    private Map<String, Integer> requiredResources;

    //a list of all items that can be created and need blueprint
    private List<String> creatablesList;

    /***
     * constructor for a research table
     *
     * @param owner the owner of the research table.
     */
    public ResearchTable(MainCharacter owner){
        super(owner);

        creatablesList = new ArrayList<>();
        creatablesList.addAll(Arrays.asList("Hatchet","Pick Axe","Cabin","StorageUnit","TownCentre",
                "Fence","SafeHouse","WatchTower","Castle"));
        playerInvenotry = GameManager.getManagerFromInstance(InventoryManager.class);
        goldCost = 25;
    }

    /**
     * a getter method for a list of all items that can be created and need
     * blueprint
     *
     * @return  a list of creatable items
     */
    public List<String> getCreatableItems() {
        return this.creatablesList;
    }

    /***
     * this method is called when the player wants to buy a blueprint from
     * the research table. The method first checks if the player has enough gold
     * if yes, adds the blueprint to the blueprintsLearned list of the player
     * and deducts the required gold.
     */
    public void buyBlueprint(){

        if (owner.getGoldPouchTotalValue()<getGoldCost()){
            logger.info("You don't have enough gold to buy a blueprint");

        } else {

            String newBlueprint = this.getCreatableItems().get(0);
            owner.getBlueprintsLearned().add(newBlueprint);
            this.getCreatableItems().remove(0);
            owner.removeGold(goldCost);
            }
    }

    public int getGoldCost(){
        return this.goldCost;
    }

    /**
     * Returns a map of the name of the required resource and
     * the required number of each resource to create the item.
     *
     * @return a hashamp of the required resources and their number.
     */
    @Override
    public Map<String, Integer> getAllRequirements() {
        allRequirements = new HashMap<>();
        allRequirements.put("Wood", 100);
        allRequirements.put("Stone", 50);
        allRequirements.put("Metal", 30);
        return allRequirements;
    }

    /**
     * Returns whether or not the item can be exchanged
     *
     * @return True if the item can be exchanged, false otherwise
     */
    @Override
    public Boolean isExchangeable() {
        return true;
    }

    /**
     * Returns the name of the Manufactured Resource.
     *
     * @return The the name of the Item.
     */
    @Override
    public String getName() {
        return "Research Table";
    }

    /**
     * Returns the item description
     *
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "A research table is used to retrieve blueprints in exchange " +
                "of resources or gold";
    }

    /**
     * Returns the number of wood required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredWood() {
        return 100;
    }

    /**
     * Returns the number of stones required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredStone() {
        return 50;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredMetal() {
        return 30;
    }

    /**
     * a getter method to check if a player has learned the blueprint
     *
     * @return true if the player has learned the blueprint.
     */
    @Override
    public boolean isBlueprintLearned() {
        return super.isBlueprintLearned();
    }

    /**
     * changes the boolean blueprintLearned to true.
     */
    @Override
    public void toggleBlueprintLearned() {
        super.toggleBlueprintLearned();
    }

}
