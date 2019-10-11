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
import deco2800.skyfall.resources.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/***
 * A Pick Axe item. Pick Axe is a manufacturd resource. It can harvest a rock.
 */
public class PickAxe extends ManufacturedResources implements Item, Blueprint {

    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(PickAxe.class);

    //Used for farming sound
    private static final String WALK_NORMAL = "walk_D";

    /***
     * Create a Pick Axe with the name Pick Axe.
     *
     * @param owner    the owner of the inventory.
     * @param position the position of the Pick Axe.
     */
    public PickAxe(MainCharacter owner, HexVector position) {
        super(owner, position);
        init();
    }

    private void init() {
        this.name = "Pick Axe";
        allRequirements = new HashMap<>();
        allRequirements.put("Wood", 20);
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
            System.out.println("This rock has nothing left to offer");
            GameManager.get().getWorld().removeEntity(rockToFarm);

        }

        else {
            SoundManager.playSound(WALK_NORMAL);
            GameManager.getManagerFromInstance(InventoryManager.class).add(new Stone());
            // lowering the possibility of gaining metal
            double x = (int) (Math.random() * (2));

            if (x == 1) {
                GameManager.getManagerFromInstance(InventoryManager.class).add(new Metal());
            }

            // lowering the possibility of gaining sand
            double y = Math.random();

            if (y >= 0.8) {
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
            if (entity instanceof AbstractRock) {
                if (position.distance(entity.getPosition()) <= 1.5) {
                    this.farmRock((AbstractRock) entity);
                }
            }
        }
        this.decreaseDurability();
        logger.warn("Durability: " + this.getDurability());
    }
}
