package deco2800.thomas.managers;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Texture manager acts as a cache between the file system and the renderers.
 * This allows all textures to be read into memory at the start of the game saving
 * file reads from being completed during rendering.
 * <p>
 * With this in mind don't load textures you're not going to use.
 * Textures that are not used should probably (at some point) be removed
 * from the list and then read from disk when needed again using some type
 * of reference counting
 *
 * @Author Tim Hadwen
 */
public class TextureManager extends AbstractManager {
	
	/**
	 * The width of the tile to use then positioning the tile.
	 */
	public static final int TILE_WIDTH = 320;
	
	/**
	 * The height of the tile to use when positioning the tile.
	 */
	public static final int TILE_HEIGHT = 278;

	
	//private final Logger log = LoggerFactory.getLogger(TextureManager.class);


    /**
     * A HashMap of all textures with string keys
     */
    private Map<String, Texture> textureMap = new HashMap<>();

    /**
     * Constructor
     * Currently loads up all the textures but probably shouldn't/doesn't
     * need to.
     */
    public TextureManager() {
        try {
            textureMap.put("background", new Texture("resources/background.jpg"));
            textureMap.put("spacman_ded", new Texture("resources/spacman_ded.png"));
            textureMap.put("spacman_blue", new Texture("resources/spacman_blue.png"));

            textureMap.put("grass_0", new Texture("resources/grass_1.png"));            
            textureMap.put("grass_1", new Texture("resources/grass_2.png"));            
            textureMap.put("grass_2", new Texture("resources/grass_3.png"));
 
            textureMap.put("selection", new Texture("resources/blue_selection.png"));
            textureMap.put("path", new Texture("resources/yellow_selection.png"));
            
            
            textureMap.put("buildingB", new Texture("resources/building3x2.png"));
            
            textureMap.put("buildingA", new Texture("resources/buildingA.png"));
            textureMap.put("tree", new Texture("resources/tree.png"));
            
            textureMap.put("fenceN-S", new Texture("resources/fence N-S.png"));
          
            textureMap.put("fenceNE-SW", new Texture("resources/fence NE-SW.png"));
            textureMap.put("fenceNW-SE", new Texture("resources/fence NW-SE.png"));
            
            
            textureMap.put("fenceNE-SW", new Texture("resources/fence NE-SW.png"));
            textureMap.put("fenceNW-SE", new Texture("resources/fence NW-SE.png"));
            
            textureMap.put("fenceNE-S", new Texture("resources/fence NE-S.png"));
            textureMap.put("fenceNE-SE", new Texture("resources/fence NE-SE.png"));
            textureMap.put("fenceN-SE", new Texture("resources/fence N-SE.png"));
            textureMap.put("fenceN-SW", new Texture("resources/fence N-SW.png"));
            textureMap.put("fenceNW-NE", new Texture("resources/fence NW-NE.png"));
            textureMap.put("fenceSE-SW", new Texture("resources/fence SE-SW.png"));
            textureMap.put("fenceNW-S", new Texture("resources/fence NW-S.png"));

            textureMap.put("rock", new Texture("resources/rocks.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a texture object for a given string id
     *
     * @param id Texture identifier
     * @return Texture for given id
     */
    public Texture getTexture(String id) {
        if (textureMap.containsKey(id)) {
            return textureMap.get(id);
        } else {
        	//log.info("Texture map does not contain P{}, returning default texture.", id);
            return textureMap.get("spacman_ded");
        }

    }

    /**
     * Checks whether or not a texture is available.
     *
     * @param id Texture identifier
     * @return If texture is available or not.
     */
    public boolean hasTexture(String id) {
        return textureMap.containsKey(id);

    }

    /**
     * Saves a texture with a given id
     *
     * @param id       Texture id
     * @param filename Filename within the assets folder
     */
    public void saveTexture(String id, String filename) {
        if (!textureMap.containsKey(id)) {
            textureMap.put(id, new Texture(filename));
        }
    }
}
