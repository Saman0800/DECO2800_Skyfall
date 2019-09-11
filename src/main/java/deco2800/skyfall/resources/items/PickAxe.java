package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.resources.Item;

import java.util.HashMap;
import java.util.Map;

/***
 * A Pick Axe item. Pick Axe is a manufacturd resource. It can harvest a rock.
 */
public class PickAxe extends ManufacturedResources implements Item, Blueprint {

    private Map<String, Integer> allRequirements;
    private boolean blueprintLearned = false;

    /***
     * Create a Pick Axe with the name Pick Axe.
     *
     * @param owner the owner of the inventory.
     * @param position the position of the Pick Axe.
     */
    public PickAxe(MainCharacter owner, HexVector position) {
        super(owner, position);
        this.name = "Pick Axe";
    }

    /***
     * Create a Pick Axe with only one owner parameter.
     *
     * @param owner the owner of the inventory.
     */
    public PickAxe(MainCharacter owner) {
        super(owner);
        this.name = "Pick Axe";
    }

    /***
     * Create a Pick Axe no parameter.
     */
    public PickAxe() {
        this.name = "Pick Axe";
    }

    /**
     * A getter method for the name of the item
     * @return The name of the item
     */
    @Override
    public String getName() {

        return this.name;
    }

    /**
     * A getter method for the subtype of the item.
     * @return The name of the subtype.
     */
    @Override
    public String getSubtype() {

        return super.subtype;
    }

    /**
     * A getter method to the position of the item.
     * @return the position of the hatchet.
     */
    @Override
    public HexVector getCoords() {
        return this.position;
    }

    /**
     * Creates a string representation Pick Axe.
     * @return hatchet name and it's subtype.
     */
    @Override
    public String toString() {

        return "" + subtype + ":" + name;
    }

    /**
     * Checks if an item is exchangeable.
     * @return true or false.
     */
    @Override
    public Boolean isExchangeable() {
        return true;
    }

    /**
     * Harvests a rock. Currently making an inventory and adding the collected
     * rock and metal to that inventory. Decreases the rock health.
     * @param rockToFarm the rock to be farmed
     */
    public void farmRock(Rock rockToFarm) {

        if (rockToFarm.getHealth() == 0) {
            System.out.println("This rock has nothing left to offer");
            GameManager.get().getWorld().removeEntity(rockToFarm);

        }

        else {
            owner.getInventoryManager().add(new Stone());

            //lowering the possibility of gaining metal
            double x = (int) (Math.random() * ((1 - 0) + 1));

            if (x == 1) {
                owner.getInventoryManager().add(new Metal());
            }

            rockToFarm.setHealth(rockToFarm.getHealth() - 10);
        }

    }

    /**
     * Returns the item description
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "This item can be constructed using stone and wood. " + "It can farm stone from biomes.";
    }

    /**
     * Returns the number of wood required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredWood() {
        return 50;
    }

    /**
     * Returns the number of stones required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredStone() {
        return 30;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredMetal() {
        return 10;
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
        allRequirements.put("Wood", 50);
        allRequirements.put("Stone", 30);
        allRequirements.put("Metal", 10);

        return allRequirements;
    }

    @Override
    public void use(){

    }

}
