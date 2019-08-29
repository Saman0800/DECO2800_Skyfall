package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Rock;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.resources.Item;

/***
 * A Pick Axe item. Pick Axe is a manufacturd resource. It can harvest a rock.
 */
public class PickAxe extends ManufacturedResources implements Item {

    /***
     * Create a Pick Axe with the name Pick Axe.
     *
     * @param owner the owner of the inventory.
     * @param position the position of the Pick Axe.
     */
    public PickAxe (MainCharacter owner, HexVector position) {
        super(owner, position);
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

        if (rockToFarm.getHealth()==0){
            System.out.println("This rock has no more stone");
        }

        else {
            owner.getInventoryManager().inventoryAdd(new Stone());
            rockToFarm.setHealth(rockToFarm.getHealth()-10);
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
