package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.Tree;
import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.inventory.Inventory;


/***
 * A Hatchet item. Hatchet is a manufacturd resource. It can harvest a tree.
 */
public class Hatchet extends ManufacturedResources implements Item {

    /***
     * Create a Hatecht with the name Hatchet
     *
     * @param owner the owner of the inventory.
     * @param position the position of the Hatchet.
     * @param name the name of the item which is Hatchet.
     */
    public Hatchet(AgentEntity owner, HexVector position, String name) {
        super(owner, position, name);
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
    public void farmTree(Tree treeToFarm) {
        int i;

        //temporary  inventory. this will change to the player inventory later.
        Inventory ownerInventory = new Inventory();

        for (i = 0; i < treeToFarm.getWoodAmount(); i++) {
            ownerInventory.inventoryAdd(new Wood());
            treeToFarm.decreaseWoodAmount();
        }

    }
}
