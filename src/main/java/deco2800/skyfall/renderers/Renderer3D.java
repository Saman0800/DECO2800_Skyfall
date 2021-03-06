package deco2800.skyfall.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Peon;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.tasks.AbstractTask;
import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.Vector2;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.Chunk;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A ~simple~ complex hex renderer for DECO2800 games
 *
 * @Author Tim Hadwen & Lachlan Healey
 */
public class Renderer3D implements Renderer {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(Renderer3D.class);

    SoundManager sound = new SoundManager();

    private BitmapFont font;

    // mouse cursor
    private static final String TEXTURE_SELECTION = "selection";

    private static final String TEXTURE_PATH = "path";
    private int tilesSkipped = 0;

    private TextureManager textureManager = GameManager.getManagerFromInstance(TextureManager.class);

    /**
     * Renders onto a batch, given a renderables with entities It is expected that
     * AbstractWorld contains some entities and a Map to read tiles from
     *
     * @param batch Batch to render onto
     */
    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(1f);
        }

        Map<Pair<Integer, Integer>, Chunk> chunks = GameManager.get().getWorld().getLoadedChunks();
        int tileCount = chunks.values().stream().mapToInt(chunk -> chunk.getTiles().size()).sum();
        List<Tile> tilesToBeSkipped = new ArrayList<>();

        batch.begin();
        // Render elements section by section
        // tiles will render the static entity attached to each tile after the tile is
        // rendered

        tilesSkipped = 0;
        for (Chunk chunk : chunks.values()) {
            for (Tile t : chunk.getTiles()) {
                // Render each tile
                renderTile(batch, camera, tileCount, tilesToBeSkipped, t);

                // Render each undiscovered area
            }
        }

        renderAbstractEntities(batch, camera);

        renderMouse(batch);

        debugRender(batch, camera);

        batch.end();
    }

    /**
     * Render a single tile.
     *
     * @param batch            the sprite batch.
     * @param camera           the camera.
     * @param tileCount        the total number of tiles.
     * @param tilesToBeSkipped a list of tiles to skip.
     * @param tile             the tile to render.
     */
    private void renderTile(SpriteBatch batch, OrthographicCamera camera, int tileCount, List<Tile> tilesToBeSkipped,
            Tile tile) {

        if (tilesToBeSkipped.contains(tile)) {
            return;
        }
        float[] tileWorldCord = WorldUtil.colRowToWorldCords(tile.getCol(), tile.getRow());

        if (WorldUtil.areCoordinatesOffScreen(tileWorldCord[0], tileWorldCord[1], camera)) {
            tilesSkipped++;
            GameManager.get().setTilesRendered(tileCount - tilesSkipped);
            GameManager.get().setTilesCount(tileCount);
            return;
        }

        Texture tex = tile.getTexture();
        batch.draw(tex, tileWorldCord[0], tileWorldCord[1], tex.getWidth() * WorldUtil.SCALE_X,
                tex.getHeight() * WorldUtil.SCALE_Y);
        if (GameManager.getPaused()) {
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, .5f);
        }
        GameManager.get().setTilesRendered(tileCount - tilesSkipped);
        GameManager.get().setTilesCount(tileCount);

    }

    /**
     * Render the tile under the mouse.
     *
     * @param batch the sprite batch.
     */
    private void renderMouse(SpriteBatch batch) {
        Vector2 mousePosition = GameManager.getManagerFromInstance(InputManager.class).getMousePosition();

        Texture tex = textureManager.getTexture(TEXTURE_SELECTION);

        // get mouse position
        float[] worldCoord = WorldUtil.screenToWorldCoordinates(mousePosition.getX(), mousePosition.getY());

        // snap to the tile under the mouse by converting mouse position to colrow then
        // back to mouse coordinates
        float[] colrow = WorldUtil.worldCoordinatesToColRow(worldCoord[0], worldCoord[1]);

        float[] snapWorldCoord = WorldUtil.colRowToWorldCords(colrow[0], colrow[1] + 1);

        // Needs to getTile with a HexVector for networking to work atm
        Tile tile = GameManager.get().getWorld().getTile(new HexVector(colrow[0], colrow[1]));

        if (tile != null) {
            batch.draw(tex, (int) snapWorldCoord[0], (int) snapWorldCoord[1] - (tex.getHeight() * WorldUtil.SCALE_Y),
                    tex.getWidth() * WorldUtil.SCALE_X, tex.getHeight() * WorldUtil.SCALE_Y);
        }

    }

    /**
     * Render all the entities on in view, including movement tiles, and excluding
     * undiscovered area.
     *
     * @param batch  the sprite batch.
     * @param camera the camera.
     */
    private void renderAbstractEntities(SpriteBatch batch, OrthographicCamera camera) {
        List<AbstractEntity> entities = GameManager.get().getWorld().getSortedEntities();

        int entitiesSkipped = 0;
        logger.debug("NUMBER OF ENTITIES IN ENTITY RENDER LIST: {}", entities.size());

        // We get the tile height and width. NOTE: This assumes that the width and
        // height of each tile is constant
        int w = TextureManager.TILE_WIDTH;
        int h = TextureManager.TILE_HEIGHT;

        for (AbstractEntity entity : entities) {
            float[] entityWorldCoord = WorldUtil.colRowToWorldCords(entity.getCol(), entity.getRow());
            // If it's offscreen
            if (WorldUtil.areCoordinatesOffScreen(entityWorldCoord[0], entityWorldCoord[1], camera)) {
                entitiesSkipped++;
                continue;
            }

            // set color of batch from abstract ent's mod color
            Color modColor = entity.getModulatingColor();
            batch.setColor(modColor.r, modColor.g, modColor.b, modColor.a);

            Texture tex = textureManager.getTexture(entity.getTexture());
            if (entity instanceof StaticEntity) {
                StaticEntity staticEntity = ((StaticEntity) entity);
                Set<HexVector> childrenPosns = staticEntity.getChildrenPositions();

                for (HexVector childpos : childrenPosns) {
                    Texture childTex = staticEntity.getTexture(childpos);
                    float[] childWorldCoord = WorldUtil.colRowToWorldCords(childpos.getCol(), childpos.getRow());

                    // time for some funky math: we want to render the entity at the centre of the
                    // tile.
                    // this way centres of textures bigger than tile textures render exactly on the
                    // top of the tile centre
                    // think of a massive tree with the tree trunk at the centre of the tile
                    // and it's branches and leaves over surrounding tiles

                    int drawX = (int) (childWorldCoord[0] + (w - childTex.getWidth()) / 2.0 * WorldUtil.SCALE_X);
                    int drawY = (int) (childWorldCoord[1] + (h - childTex.getHeight()) / 2.0 * WorldUtil.SCALE_Y);

                    batch.draw(childTex, drawX, drawY, childTex.getWidth() * WorldUtil.SCALE_X,
                            childTex.getHeight() * WorldUtil.SCALE_Y);
                }
            } else {
                if (!(entity instanceof Animatable)) {
                    renderAbstractEntity(batch, entity, entityWorldCoord, tex);
                }
                // the original code is extracted to fix code smells
                // (reduce cyclomatic complexity)
                checkIfMainCharacterEntity(entity, batch);

                runAnimation(batch, entity, entityWorldCoord);
            }

            /* Draw Peon */
            // Place movement tiles
            if (entity instanceof Peon && GameManager.get().getShowPath()) {
                renderPeonMovementTiles(batch, camera, entity);
            }
        }
        GameManager.get().setEntitiesRendered(entities.size() - entitiesSkipped);
        GameManager.get().setEntitiesCount(entities.size());
    }

    /**
     * @param batch           The sprite batch.
     * @param entity          The entity that will be drawn
     * @param entityWorldCord The coordinates to render at
     * @param tex             The texture to render it as
     */

    private void renderAbstractEntity(SpriteBatch batch, AbstractEntity entity, float[] entityWorldCord, Texture tex) {
        TextureRegion tempRegion = new TextureRegion(tex);
        float x = entityWorldCord[0];
        float y = entityWorldCord[1];
        float angle = entity.getAngle();

        float width = tex.getWidth() * entity.getColRenderLength() * WorldUtil.SCALE_X * entity.getScale();
        float height = tex.getHeight() * entity.getRowRenderLength() * WorldUtil.SCALE_Y * entity.getScale();
        batch.draw(tempRegion, x, y, width / 2.f, height / 2.f, width, height, 1.f, 1.f, angle);
    }

    private void renderPeonMovementTiles(SpriteBatch batch, OrthographicCamera camera, AbstractEntity entity) {
        Peon actor = (Peon) entity;
        AbstractTask task = actor.getTask();
        if (task instanceof MovementTask) {
            if (((MovementTask) task).getPath() == null) { // related to issue #8
                return;
            }
            List<Tile> path = ((MovementTask) task).getPath();
            for (Tile tile : path) {
                // Place transparent tiles for the path, but place a non-transparent tile for
                // the destination
                Texture tex = path.get(path.size() - 1) == tile ? textureManager.getTexture(TEXTURE_SELECTION)
                        : textureManager.getTexture(TEXTURE_PATH);
                float[] tileWorldCord = WorldUtil.colRowToWorldCords(tile.getCol(), tile.getRow());
                if (WorldUtil.areCoordinatesOffScreen(tileWorldCord[0], tileWorldCord[1], camera)) {
                    tilesSkipped++;
                    continue;
                }
                batch.draw(tex, tileWorldCord[0], tileWorldCord[1]// + ((tile.getElevation() + 1) *
                // elevationZeroThiccness * WorldUtil.SCALE_Y)
                        , tex.getWidth() * WorldUtil.SCALE_X, tex.getHeight() * WorldUtil.SCALE_Y);

            }
        }
    }

    private void debugRender(SpriteBatch batch, OrthographicCamera camera) {

        if (GameManager.get().getShowCoords()) {
            List<Tile> tileMap = GameManager.get().getWorld().getLoadedChunks().values().stream().flatMap(chunk ->
                    chunk.getTiles().stream()).collect(Collectors.toList());
            for (Tile tile : tileMap) {
                float[] tileWorldCord = WorldUtil.colRowToWorldCords(tile.getCol(), tile.getRow());

                if (!WorldUtil.areCoordinatesOffScreen(tileWorldCord[0], tileWorldCord[1], camera)) {
                    font.draw(batch, tile.toString(),
                            // String.format("%.0f, %.0f, %d",tileWorldCord[0], tileWorldCord[1],
                            // tileMap.indexOf(tile)),
                            tileWorldCord[0] + WorldUtil.TILE_WIDTH / 4.5f, tileWorldCord[1]);
                }

            }
        }

        if (GameManager.get().getShowCoordsEntity()) {
            List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
            for (AbstractEntity entity : entities) {
                float[] tileWorldCord = WorldUtil.colRowToWorldCords(entity.getCol(), entity.getRow());

                if (!WorldUtil.areCoordinatesOffScreen(tileWorldCord[0], tileWorldCord[1], camera)) {
                    font.draw(batch, String.format("%.0f, %.0f", entity.getCol(), entity.getRow()), tileWorldCord[0],
                            tileWorldCord[1]);
                }
            }
        }
    }

    /**
     * Render the default sprite for an entity, used if the state is NULL and the
     * Animatable interface has been implemented. If a directional texture cannot be
     * found then the default entity texture is render(i.e the one given into its
     * constructor)
     *
     * @param batch            The sprite batch.
     * @param entity           The entity that will be drawn
     * @param entityWorldCoord The coordinates to render at
     */
    private void renderDefaultSprite(SpriteBatch batch, AbstractEntity entity, float[] entityWorldCoord) {
        String directionTexture = entity.getDefaultTexture();

        if (!directionTexture.equals("Not Found")) {
            renderAbstractEntity(batch, entity, entityWorldCoord, textureManager.getTexture(directionTexture));

        } else {
            renderAbstractEntity(batch, entity, entityWorldCoord, textureManager.getTexture(entity.getTexture()));
        }
    }

    /**
     * Runs an animation for the entity if it is applicable if there is no animation
     * to be run or it cannot be found a default texture is run
     *
     * @param batch            Sprite batch to draw onto
     * @param entity           Current entity
     * @param entityWorldCoord Where to draw.
     */
    private void runAnimation(SpriteBatch batch, AbstractEntity entity, float[] entityWorldCoord) {
        if (entity.getCurrentState() == AnimationRole.NULL) {
            renderDefaultSprite(batch, entity, entityWorldCoord);
        }

        AnimationLinker aniLink = entity.getToBeRun();
        if (aniLink == null) {
            renderDefaultSprite(batch, entity, entityWorldCoord);
            return;
        }

        Animation<TextureRegion> ani = aniLink.getAnimation();
        float time = aniLink.getStartingTime();

        if (ani == null) {
            logger.info("Animation is null");
            renderDefaultSprite(batch, entity, entityWorldCoord);
            return;
        }

        if (ani.isAnimationFinished(time)) {
            aniLink.resetStartingTime();

            if (!aniLink.isLooping()) {
                entity.setGetToBeRunToNull();
            }
            renderDefaultSprite(batch, entity, entityWorldCoord);
            return;
        }

        TextureRegion currentFrame = ani.getKeyFrame(time, true);
        float width = currentFrame.getRegionWidth() * entity.getColRenderLength() * WorldUtil.SCALE_X
                * entity.getScale();
        float height = currentFrame.getRegionHeight() * entity.getRowRenderLength() * WorldUtil.SCALE_Y
                * entity.getScale();
        int[] offset = aniLink.getOffset();

        if (entity instanceof MainCharacter) {
            if (((MainCharacter) entity).isHurt() || ((MainCharacter) entity).isDead()) {
                entity.setModulatingColor(Color.RED);
            } else if (!((MainCharacter) entity).isRecovering()){
                entity.setModulatingColor(Color.WHITE);
            }
        }

        batch.draw(currentFrame, entityWorldCoord[0] + offset[0], entityWorldCoord[1] + offset[0], width, height);
        aniLink.incrTime(Gdx.graphics.getDeltaTime());
    }

    public void checkIfMainCharacterEntity(AbstractEntity entity, SpriteBatch batch) {
        if(entity instanceof MainCharacter) {
            Color originalCol = batch.getColor();
            if (((MainCharacter) entity).isRecovering()){
                if(((MainCharacter) entity).isTexChanging()){
                    originalCol.set(Color.LIGHT_GRAY);
                } else {
                    originalCol.set(Color.WHITE);
                }
                entity.setModulatingColor(originalCol);
                ((MainCharacter) entity).setTexChanging(!((MainCharacter) entity).isTexChanging());
            }
        }
    }

}
