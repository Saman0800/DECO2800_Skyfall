package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
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

    /***
     * Create a Hatecht with the name Hatchet
     *
     * @param owner    the owner of the inventory.
     * @param position the position of the Hatchet.
     */
    public Hatchet(MainCharacter owner, HexVector position) {
        super(owner, position);
        init();
    }

    private void init() {
        this.name = "Hatchet";
        allRequirements = new HashMap<>();
        allRequirements.put("Wood", 25);
        allRequirements.put("Stone", 10);
        allRequirements.put("Metal", 0);
        description = "hatchey";
        carryable = true;
    }

    /***
     * Create a Hatecht with the name Hatchet with no parameters.
     */
    public Hatchet() {
        init();
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
            SoundManager.playSound(CUT_TREE);
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
        logger.warn("Durability: %d", this.getDurability());
    }

}
