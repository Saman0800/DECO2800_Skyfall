package deco2800.skyfall.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Collections;

import com.badlogic.gdx.physics.box2d.BodyDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import com.badlogic.gdx.graphics.Texture;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;

import com.google.gson.annotations.Expose;

public class StaticEntity extends AbstractEntity implements NewInstance<StaticEntity> {
    private final transient Logger log = LoggerFactory.getLogger(StaticEntity.class);

    private static final String ENTITY_ID_STRING = "staticEntityID";
    private int renderOrder;
    private boolean obstructed;
    private static TextureManager textureManager = GameManager.getManagerFromInstance(TextureManager.class);

    @Expose
    public Map<HexVector, String> children;

    private Map<HexVector, String> textures;

    public StaticEntity() {
        super();
    }

    public StaticEntity(Tile tile, int renderOrder, String texture, boolean obstructed) {
        super(tile.getCol(), tile.getRow(), renderOrder);
        this.setObjectName(ENTITY_ID_STRING);
        this.setTexture(texture);
        this.renderOrder = renderOrder;
        this.obstructed = obstructed;
        children = new HashMap<>();
        children.put(tile.getCoordinates(), texture);
        if (!WorldUtil.validColRow(tile.getCoordinates())) {
            log.debug(tile.getCoordinates() + "%s Is Invalid:");
            return;
        }

        tile.setParent(this);
        tile.setObstructed(obstructed);
    }

    public StaticEntity(float col, float row, int renderOrder, Map<HexVector, String> texture) {
        super(col, row, renderOrder);
        this.setObjectName(ENTITY_ID_STRING);

        Tile center = GameManager.get().getWorld().getTile(this.getPosition());
        this.renderOrder = renderOrder;
        this.obstructed = true;
        this.textures = texture;

        if (center == null) {
            log.debug("Center is null");
            return;
        }

        if (!WorldUtil.validColRow(center.getCoordinates())) {
            log.debug(center.getCoordinates() + " Is Invalid:");
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
     * A simple getter function to retrieve the render order of the static object.
     * 
     * @return The render order of this instance.
     */
    public int getRenderorder() {
        return this.renderOrder;
    }

    public void setRenderorder(int renderOrder) {
        this.renderOrder = renderOrder;
    }

    /**
     * A simple getter function to retrieve the obstruction value of this object
     *
     * @return The obstruction value.
     */
    public boolean isObstructed() {
        return this.obstructed;
    }

    /**
     * A simple getter function to retrieve the textures used for this object
     * 
     * @return The obstruction value.
     */
    public Map<HexVector, String> getTextures() {
        return Collections.unmodifiableMap(this.textures);
    }

    public void setup() {
        if (children == null) {
            return;
        }

        for (HexVector childPos : children.keySet()) {
            Tile child = GameManager.get().getWorld().getTile(childPos);
            if (child != null) {
                child.setParent(this);
            }
        }

        getBody().setType(BodyDef.BodyType.StaticBody);
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

    private Tile textureToTile(HexVector offset, HexVector center) {
        if (!WorldUtil.validColRow(offset)) {
            log.debug(offset + " Is Invaid:");
            return null;
        }
        HexVector targetTile = center.add(offset);
        return GameManager.get().getWorld().getTile(targetTile);
    }

    public int[] getRenderCentre() {
        float[] rowColValues = WorldUtil.colRowToWorldCords(getCol(), getRow());

        int drawX = (int) (rowColValues[0] + TextureManager.TILE_WIDTH * WorldUtil.SCALE_X / 2);
        int drawY = (int) (rowColValues[1] + TextureManager.TILE_HEIGHT * WorldUtil.SCALE_Y / 2);

        int[] renderPos = new int[2];
        renderPos[0] = drawX;
        renderPos[1] = drawY;

        return renderPos;
    }

    public Set<HexVector> getChildrenPositions() {
        return children.keySet();
    }

    public Texture getTexture(HexVector childPos) {
        String texture = children.get(childPos);

        return textureManager.getTexture(texture);
    }

    public void setChildren(Map<HexVector, String> children) {
        this.children = children;
    }
}
