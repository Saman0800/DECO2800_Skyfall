package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.Rock;
import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.managers.InventoryManager;

/***
 * A Pick Axe item. Pick Axe is a manufacturd resource. It can harvest a rock.
 */
public class PickAxe extends ManufacturedResources implements Item {

    /***
     * Create a Pick Axe with the name Pick Axe.
     *
     * @param owner the owner of the inventory.
     * @param position the position of the Pick Axe.
     * @param name the name of the item which is Pick Axe.
     */
    public PickAxe (AgentEntity owner, HexVector position, String name) {
        super(owner, position, name);
        this.name="Pick Axe";
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
     * rock to that inventory. Decreases the rock health.
     * @param rockToFarm the rock to be farmed
     */
    public void farmRock(Rock rockToFarm) {
        int i;

        //temporary  inventory. this will change to the player inventory later.
        InventoryManager ownerInventory = new InventoryManager();

        for (i = 0; i < rockToFarm.getHealth()/10; i++) {
            ownerInventory.inventoryAdd(new Stone());
            rockToFarm.setHealth(rockToFarm.getHeight()-10);
        }

    }

    /**
     * Returns the item description
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "This item can be constructed using stone and wood. " +
                "It can farm stone from biomes.";
    }
}
