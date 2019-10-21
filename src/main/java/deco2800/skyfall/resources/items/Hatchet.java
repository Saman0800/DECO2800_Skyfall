package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.util.HexVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/***
 * A Hatchet item. Hatchet is a manufacturd resource. It can harvest a tree.
 */
public class Hatchet extends ManufacturedResources implements Blueprint {

    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(Hatchet.class);

    //Used for farming sound
    private static final String CUT_TREE = "cut_tree";

    private SoundManager sm;

    /**
     * initialises the requirements
     */
    private void init() {
        this.name = "Hatchet";
        allRequirements = new HashMap<>();
        allRequirements.put("Wood", 3);
        allRequirements.put("Stone", 3);
        allRequirements.put("Metal", 0);
        description = "hatchet";
        carryable = true;
        sm = GameManager.getManagerFromInstance(SoundManager.class);
    }

    /***
     * Create a Hatecht with the name Hatchet with no parameters.
     */
    public Hatchet() {
        init();
    }

    /**
     * Returns the number of wood required for the item.
     *
     * @return The name of the item
     */
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
     * Checks if an item is exchangeable.
     * 
     * @return true or false.
     */
    @Override
    public boolean isExchangeable() {
        return true;
    }

    /**
     * Harvests a tree. and adding the collected wood to the owner inventory.
     * decreased the woodAmount of a tree. Once a tree has no more wood, removes the
     * tree from the world.
     * 
     * @param treeToFarm the tree to be farmed
     */
    public void farmTree(AbstractTree treeToFarm) {
        if (treeToFarm.getWoodAmount() == 0) {
            logger.info("This tree has no more wood");
            GameManager.get().getWorld().removeEntity(treeToFarm);

        } else {
            sm.playSound(CUT_TREE);
            GameManager.getManagerFromInstance(InventoryManager.class).add(new Wood());
            treeToFarm.decreaseWoodAmount();

            // lowering the possibility of gaining sand
            double y = Math.random();

            if (y >= 0.8) {
                GameManager.getManagerFromInstance(InventoryManager.class).add(new Vine());
            }
        }
    }

    @Override
    public int getCost() {
        return 20;
    }

    @Override
    public void use(HexVector position) {
        for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {
            if (entity instanceof AbstractTree && position.distance(entity.getPosition()) <= 1.5 ) {
                    this.farmTree((AbstractTree) entity);
            }
        }
        this.decreaseDurability();

        String message = String.format("Durability: %d", this.getDurability());
        logger.warn(message);
    }


}
