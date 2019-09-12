package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.resources.Item;
import java.util.HashMap;
import java.util.Map;

/***
 * A Hatchet item. Hatchet is a manufacturd resource. It can harvest a tree.
 */
public class Hatchet extends ManufacturedResources implements Item, Blueprint {

    private Map<String, Integer> allRequirements;
    private static boolean blueprintLearned = false;

    /***
     * Create a Hatecht with the name Hatchet
     *
     * @param owner the owner of the inventory.
     * @param position the position of the Hatchet.
     */
    public Hatchet(MainCharacter owner, HexVector position) {
        super(owner, position);
        this.name = "Hatchet";
    }

    /***
     * Create a Hatecht with the name Hatchet
     *
     * @param owner the owner of the inventory.
     */

    public Hatchet(MainCharacter owner) {
        super(owner);
        this.name = "Hatchet";
    }

    /***
     * Create a Hatecht with the name Hatchet with no parameters.
     */
    public Hatchet() {
        this.name = "Hatchet";
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
     * A getter method to check if it's carryable.
     * @return true if carryable, false otherwise.
     */

    /**
    * Creates a string representation Hatchet
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
     * Harvests a tree. and adding the collected
     * wood to the owner inventory. decreased the woodAmount of a tree. Once
     * a tree has no more wood, removes the tree from the world.
     * @param treeToFarm the tree to be farmed
     */
    public void farmTree(Tree treeToFarm) {
            if (treeToFarm.getWoodAmount() == 0) {
                System.out.println("This tree has no more wood");
                GameManager.get().getWorld().removeEntity(treeToFarm);

            } else {
                GameManager.getManagerFromInstance(InventoryManager.class).add(new Wood());
                treeToFarm.decreaseWoodAmount();
            }
        }

    /**
     * Returns the item description
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "This item is similar to an axe. It can be used to " + "cut down trees and retrieve wood.";
    }

    @Override
    public int getRequiredWood() {
        return 25;
    }

    /**
     * Returns the number of stones required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredStone() {
        return 10;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredMetal() {
        return 0;
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
        allRequirements.put("Wood", 25);
        allRequirements.put("Stone", 10);
        allRequirements.put("Metal", 0);

        return allRequirements;
    }

    /**
     * a getter method to check if a player has learned the blueprint
     *
     * @return true if the player has learned the blueprint.
     */
    @Override
    public boolean isBlueprintLearned() {

        return blueprintLearned;
    }

    @Override
    public void use(HexVector position){
        for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {
            if (entity instanceof Tree) {
                if (position.distance(entity.getPosition()) <= 1.5) {
                    this.farmTree((Tree) entity);
                }
            }
        }

    }
}
