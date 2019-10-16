package deco2800.skyfall.networking;

import deco2800.skyfall.worlds.Tile;

public class TileUpdateMessage {
    private Tile tile;

    /**
     * Gets the tile parameter
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * Sets the tile parameter
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
