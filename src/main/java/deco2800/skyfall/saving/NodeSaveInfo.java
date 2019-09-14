package deco2800.skyfall.saving;

import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.world.World;

public class NodeSaveInfo {

    // The ID of the world
    private int worldID;

    // The coordinates of the node
    private double x;
    private double y;

    /**
     * Constructor for a new NodeSaveInfo
     *
     * @param node the node this is for
     * @param world the world this node is in
     */
    public NodeSaveInfo(WorldGenNode node, World world) {
        this.worldID = world.getSaveID();
        this.x = node.getX();
        this.y = node.getY();
    }

    /**
     * Returns the ID of the world this is in
     *
     * @return the ID of the world this is in
     */
    public int getWorldID() {
        return this.worldID;
    }

    /**
     * Returns the X coordinate of this node
     *
     * @return the X coordinate of this node
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the Y coordinate of this node
     *
     * @return the Y coordinate of this node
     */
    public double getY() {
        return this.y;
    }

}
