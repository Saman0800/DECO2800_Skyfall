package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.entities.Tree;
import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.managers.InventoryManager;
import org.lwjgl.Sys;

import java.lang.Math;



/***
 * A Hatchet item. Hatchet is a manufacturd resource. It can harvest a tree.
 */
public class Hatchet extends ManufacturedResources implements Item {

    /***
     * Create a Hatecht with the name Hatchet
     *
     * @param owner the owner of the inventory.
     * @param position the position of the Hatchet.
     */
    public Hatchet(PlayerPeon owner, HexVector position) {
        super(owner, position);
        this.name="Hatchet";
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
     * Harvests a tree. Currently making an inventory and adding the collected
     * wood to that inventory. decreased the woodAmount of a tree.
     * @param treeToFarm the tree to be farmed
     */
    public void farmTree(Tree treeToFarm, InventoryManager ownerInventory) {

        if (treeToFarm.getWoodAmount() == 0) {
            System.out.println("This tree has no more wood");

        } else {
            ownerInventory.inventoryAdd(new Wood());
            treeToFarm.decreaseWoodAmount();
        }
    }

}
