package deco2800.skyfall.resources;

/**
 * An interface representing a generic resource item.
 */
public interface Item {

    String getName();
    String getSubtype();
    Boolean isCarryable();
    Boolean hasHealingPower();


}
