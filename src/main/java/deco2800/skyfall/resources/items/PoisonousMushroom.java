package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.HealthResources;


public class PoisonousMushroom extends HealthResources {

    /**
     * Creates a generic poisonous mushroom.
     */
    public PoisonousMushroom() {
        this.biome = "Forest";
        this.colour ="black white";
        this.name ="PoisonousMushroom";
        this.setHealthValue(-3);
        this.description = "This item hurts the Main Character.";
    }
}