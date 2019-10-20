package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.resources.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

import java.util.HashMap;

/***
 * A Pick Axe item. Pick Axe is a manufacturd resource. It can harvest a rock.
 */
public class PickAxe extends ManufacturedResources implements Item, Blueprint {

    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(PickAxe.class);

    //Used for farming sound
    private static final String COLLECT_STONE = "collect_stone";

    Random random = new Random();

    private void init() {
        this.name = "Pick Axe";
        allRequirements = new HashMap<>();
        allRequirements.put("Wood", 10);
        allRequirements.put("Stone", 10);
        allRequirements.put("Metal", 4);
        description = "This item can be constructed using stone and wood. " + "\n" + "It can farm stone from biomes.";
        carryable = true;
    }

    /***
     * Create a Pick Axe no parameter.
     */
    public PickAxe() {
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
     * Harvests a rock. Currently making an inventory and adding the collected rock
     * and metal to that inventory. Decreases the rock health.
     * 
     * @param rockToFarm the rock to be farmed
     */
    public void farmRock(AbstractRock rockToFarm) {

        if (rockToFarm.getHealth() == 0) {
            logger.info("This rock has nothing left to offer");
            GameManager.get().getWorld().removeEntity(rockToFarm);

        }

        else {
            SoundManager.playSound(COLLECT_STONE);
            GameManager.getManagerFromInstance(InventoryManager.class).add(new Stone());
            // lowering the possibility of gaining metal

            int x = random.nextInt(2);

            if (x == 1) {
                GameManager.getManagerFromInstance(InventoryManager.class).add(new Metal());
            }
            // lowering the possibility of gaining sand
            if (x == 0) {
                GameManager.getManagerFromInstance(InventoryManager.class).add(new Sand());
            }
            rockToFarm.setHealth(rockToFarm.getHealth() - 10);
        }
    }

    @Override
    public int getCost() {
        return 20;
    }

    @Override
    public void use(HexVector position) {
        for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {
            if (entity instanceof AbstractRock && position.distance(entity.getPosition()) <= 1.5) {
                    this.farmRock((AbstractRock) entity);
            }
        }
        this.decreaseDurability();
        String message = String.format("Durability: %d", this.getDurability());
        logger.warn(message);
    }

    /**
     * Returns the number of wood required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredWood() {
        return 20;
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
        return 4;
    }


}
