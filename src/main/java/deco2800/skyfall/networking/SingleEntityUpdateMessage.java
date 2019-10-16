package deco2800.skyfall.networking;

import deco2800.skyfall.entities.AbstractEntity;

/**
 * Updates (or creates) a single entity in the game.
 */
public class SingleEntityUpdateMessage {
    private AbstractEntity entity;

    /**
     * Gets the entity parameter
     */
    public AbstractEntity getEntity() {
        return this.entity;
    }

    /**
     * Sets the entity parameter
     */
    public void setEntity(AbstractEntity entity) {
        this.entity = entity;
    }
}