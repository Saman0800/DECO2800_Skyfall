package deco2800.skyfall.entities;

import com.badlogic.gdx.graphics.Texture;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.Chunk;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticEntity extends SaveableEntity implements NewInstance<StaticEntity> {
    private final transient Logger log = LoggerFactory.getLogger(StaticEntity.class);

    private static final String ENTITY_ID_STRING = "staticEntityID";
    private static final TextureManager textureManager = GameManager.getManagerFromInstance(TextureManager.class);

    private Map<HexVector, String> children;

    private Map<HexVector, String> textures;

    public StaticEntity() {
        super();
        this.obstructed = true;
    }

    /**
     * Loads a static entity from a memento
     *
     * @param memento the static entitiy to add
     */
    public StaticEntity(SaveableEntityMemento memento) {
        super(memento.getCol(), memento.getRow(), memento.getRenderOrder());
        this.load(memento);
        children = new HashMap<>();
        HexVector hexVector = new HexVector(memento.getCol(), memento.getRow());
        children.put(hexVector, memento.texture);
        if (!WorldUtil.validColRow(hexVector)) {
            log.debug("{} Is Invalid:", hexVector);
        }

    }

    /**
     * Initialises a static entity
     *
     * @param tile        The tile it spawns on
     * @param renderOrder The position is has in the render order
     * @param texture     The texture it is given
     * @param obstructed  Whether the entity is obstructed by something
     */
    public StaticEntity(Tile tile, int renderOrder, String texture, boolean obstructed) {
        super(tile.getCol(), tile.getRow(), renderOrder);
        this.setObjectName(ENTITY_ID_STRING);
        this.setTexture(texture);
        this.obstructed = obstructed;
        children = new HashMap<>();
        children.put(tile.getCoordinates(), texture);
        if (!WorldUtil.validColRow(tile.getCoordinates())) {
            log.debug("{} Is Invalid:", tile.getCoordinates());
            return;
        }

        tile.setParent(this);
        tile.setObstructed(obstructed);
    }

    /**
     * Initialises a static entity
     *
     * @param col         The tile col it spawns on
     * @param row         The tile row it spawns on
     * @param renderOrder The position it has in the render order
     * @param texture     The texture the entity is given
     */
    public StaticEntity(float col, float row, int renderOrder, Map<HexVector, String> texture) {
        super(col, row, renderOrder);
        this.setObjectName(ENTITY_ID_STRING);

        Tile center = GameManager.get().getWorld().getTile(this.getPosition());
        this.obstructed = true;
        this.textures = texture;

        staticEntitySetUp(center, texture);
    }

    /**
     * Initialises a static entity with a custom hit box
     *
     * @param tile        The tile it spawns on
     * @param renderOrder The position it has in the render order
     * @param texture     The texture the entity is given
     * @param obstructed  Whether the entity is obstructed by something
     * @param fixtureDef  The name of the hit box given to the entity
     */
    public StaticEntity(Tile tile, int renderOrder, String texture, boolean obstructed, String fixtureDef) {
        super(tile.getCol(), tile.getRow(), renderOrder, fixtureDef);
        this.setObjectName(ENTITY_ID_STRING);
        this.setTexture(texture);

        this.renderOrder = renderOrder;
        this.obstructed = obstructed;

        children = new HashMap<>();
        children.put(tile.getCoordinates(), texture);
        if (!WorldUtil.validColRow(tile.getCoordinates())) {
            log.debug("{}s Is Invalid:", tile.getCoordinates());
            return;
        }

        tile.setParent(this);
        tile.setObstructed(obstructed);
    }

    /**
     * Initialises a static entity with a custom hit box
     *
     * @param col         The tile col it spawns on
     * @param row         The tile row it spawns on
     * @param renderOrder The position it has in the render order
     * @param texture     The texture the entity is given
     * @param fixtureDef  The name of the hit box given to the entity
     */
    public StaticEntity(float col, float row, int renderOrder, Map<HexVector, String> texture, String fixtureDef) {
        super(col, row, renderOrder, fixtureDef);
        this.setObjectName(ENTITY_ID_STRING);

        Tile center = GameManager.get().getWorld().getTile(this.getPosition());
        this.renderOrder = renderOrder;
        this.obstructed = true;
        this.textures = texture;

        staticEntitySetUp(center, texture);
    }

    private void staticEntitySetUp(Tile center, Map<HexVector, String> texture) {
        if (center == null) {
            log.debug("Center is null");
            return;
        }
        center.setObstructed(true);

        if (!WorldUtil.validColRow(center.getCoordinates())) {
            log.debug("{} is Invalid:", center.getCoordinates());
            return;
        }

        children = new HashMap<>();

        for (Entry<HexVector, String> tex : texture.entrySet()) {
            Tile tile = textureToTile(tex.getKey(), this.getPosition());
            if (tile != null) {
                children.put(tile.getCoordinates(), tex.getValue());
            }
        }

        for (HexVector childPos : children.keySet()) {
            Tile child = GameManager.get().getWorld().getTile(childPos);

            child.setObstructed(true);
        }
    }

    /**
     * A simple getter function to retrieve the textures used for this object
     *
     * @return The obstruction value.
     */
    public Map<HexVector, String> getTextures() {
        return Collections.unmodifiableMap(this.textures);
    }

    /**
     * This is the default implementation of the newInstance function for the static
     * entity given a tile position.
     *
     * @return A clone of the instance with only the tile position having changed.
     */
    public StaticEntity newInstance(Tile tile) {
        return new StaticEntity(tile, this.getRenderOrder(), this.getTexture(), this.isObstructed());
    }

    /**
     * This is the default implementation of the newInstance function for the static
     * entity given a row and column position.
     *
     * @return A clone of the instance with only the centring of the entity having
     *         changed
     */
    public StaticEntity newInstance(float col, float row) {
        return new StaticEntity(col, row, this.getRenderOrder(), this.getTextures());
    }

    @Override
    public void onTick(long i) {
        // Do the AI for the building in here
    }

    /**
     * Gets the tile for a position on the map
     *
     * @param offset The offset from the center of the map
     * @param center The center of the map
     * @return The Tile at the given location
     */
    private Tile textureToTile(HexVector offset, HexVector center) {
        if (!WorldUtil.validColRow(offset)) {
            log.debug("{} Is Invaid:", offset);
            return null;
        }
        HexVector targetTile = center.add(offset);
        return GameManager.get().getWorld().getTile(targetTile);
    }

    /**
     * Gets the position to render
     *
     * @return The x and y value for the position to render
     */
    public int[] getRenderCentre() {
        float[] rowColValues = WorldUtil.colRowToWorldCords(getCol(), getRow());

        int drawX = (int) (rowColValues[0] + TextureManager.TILE_WIDTH * WorldUtil.SCALE_X / 2);
        int drawY = (int) (rowColValues[1] + TextureManager.TILE_HEIGHT * WorldUtil.SCALE_Y / 2);

        int[] renderPos = new int[2];
        renderPos[0] = drawX;
        renderPos[1] = drawY;

        return renderPos;
    }

    /**
     * Get the position of the child static entities
     *
     * @return Key set of all the child positions
     */
    public Set<HexVector> getChildrenPositions() {
        return children.keySet();
    }

    /**
     * Gets the texture for a child position
     *
     * @param childPos The position of the child
     * @return The Texture for that child
     */
    public Texture getTexture(HexVector childPos) {
        String texture = children.get(childPos);

        return textureManager.getTexture(texture);
    }

    /**
     * Sets a map of child entities
     *
     * @param children Map with there position and name
     */
    public void setChildren(Map<HexVector, String> children) {
        this.children = children;
    }

    /**
     * Adds this entity to a chunk
     *
     * @param chunk the chunk to add to
     */
    public void addToChunk(Chunk chunk) {
        chunk.addEntity(this);
    }
}
