package deco2800.skyfall.networking;

import deco2800.skyfall.entities.AbstractEntity;

public class ConnectMessage {
    private String username;
    private AbstractEntity playerEntity;

    /**
     * Gets the username parameter
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username parameter
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the playerEntity parameter
     */
    public AbstractEntity getPlayerEntity() {
        return this.playerEntity;
    }

    /**
     * Sets the playerEntity parameter
     */
    public void setPlayerEntity(AbstractEntity playerEntity) {
        this.playerEntity = playerEntity;
    }
}
