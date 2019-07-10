package deco2800.thomas.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Logger;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.worlds.Tile;

/**
 * A utility class for the World instances
 * Created by BradleyKing on 15/6/18.
 */
public class WorldUtil {

	private static final float SCALE = 0.3f;
	
	private static final boolean ISO_MODE = false;
	
	public static final float SCALE_X = SCALE;
	public static final float SCALE_Y = SCALE * (ISO_MODE ? 5/8f : 1f);
	
    //base sizes for calculating grid placement.
	public static final float TILE_WIDTH = TextureManager.TILE_WIDTH * SCALE_X;
	public static final float TILE_HEIGHT = TextureManager.TILE_HEIGHT * SCALE_Y;
	
	private WorldUtil() {}

	/**
     * Converts screen coordinates to world coordinates
     * @param x the x coordinate on the screen
     * @param y the y coordinate on the screen
     * @return a float array of the world coordinates
     */
	public static float[] screenToWorldCoordinates(float x, float y) {
		Vector3 result = GameManager.get().getCamera().unproject(new Vector3(x, y, 0));

		return new float[]{result.x, result.y};
	}

	public static float[] worldCoordinatesToColRow(float x, float y) {
		float row;
		float col;
		float actualRow; 
		x -= TILE_WIDTH / 2;
		y -= TILE_HEIGHT / 2;
		col = Math.round(x/(TILE_WIDTH * 0.75f));
		actualRow = y/ TILE_HEIGHT;
		row = Math.round(actualRow);
		if (col % 2 != 0) {
			if (row > actualRow) {
				row -= 0.5;
			} else {
				row += 0.5;
			}
		}
		return new float[]{col, row};
	}

	/**
	 * Same function as above, but returns a primitive type. Much faster for rendering.
	 * @param col coordinate column
	 * @param row coordinate row
	 * @return a float array containing the world coordinates
	 */
	public static float[] colRowToWorldCords(float col, float row) {
		float hexX = col * TILE_WIDTH * 0.75f; //sprite ratio to fix sprite
		float hexY = row * TILE_HEIGHT;

		return new float[]{hexX, hexY};
	}
    
    public static boolean validColRow(HexVector pos) {
    	if (pos.getCol() % 1 !=0 ) {
			 return false;
		}
		float offsetRow = pos.getCol() % 2 != 0 ? 0.5f : 0f ;
		return (pos.getRow() + (offsetRow)) % 1 == 0;
	}

	/**
	 * Determines if the position is safe to walk on or place machines on.
	 * @param col the x coordinate of a position
	 * @param row the y coordinate of a position
	 * @return A boolean stating if the position is safe to walk on.
	 */
	public static boolean isWalkable(float col, float row) {
		if (GameManager.get().getWorld() == null || GameManager.get().getWorld().getTile(col, row) == null)
			return false;

		return !GameManager.get().getWorld().getTile(col, row).isObstructed();
	}
    
  	public static boolean areCoordinatesOffScreen(float hexX, float hexY, OrthographicCamera camera) {
		float bufferWidth = 1.1f;
  		return hexX < (camera.position.x - camera.viewportWidth * camera.zoom / 2 - 2 * TILE_WIDTH * camera.zoom * bufferWidth)
  				|| hexX > (camera.position.x + camera.viewportWidth * camera.zoom / 2 + TILE_WIDTH * camera.zoom * bufferWidth + 50)
  				|| hexY < (camera.position.y - camera.viewportHeight * camera.zoom / 2 - 4 * TILE_HEIGHT * camera.zoom * bufferWidth)
  				|| hexY > (camera.position.y + camera.viewportHeight * camera.zoom / 2 + TILE_HEIGHT * camera.zoom * bufferWidth - 50);
  	}

    /**
     * Attempts to find the closestWalkable tile to a location
     * NO LOGGING BECAUSE OF HORRENDOUS SPAM
     * @param startLocation the location of a tile to start the search
     * @return a location of a walkable tile or null if none where found
     */
    public static HexVector closestWalkable(HexVector startLocation) {
		LinkedList<HexVector> queue = new LinkedList<>();
		// A set of all the tiles that have been added to the queue
        // Used to verify no duplicates are added for infinite loops
		Set<HexVector> closedSet = new HashSet<>();
		queue.add(startLocation);
		while (!queue.isEmpty()) {
		    // Make the first in queue the default start
			HexVector nextLocation = queue.get(0);

			// Attempt to get the closest to the start location out of the queue
			for (HexVector neighbour: queue) {

				// Improve this as it has some funky outcomes
				if(Math.abs(neighbour.distance(startLocation.getCol(), startLocation.getRow())) < Math.abs(nextLocation.distance(startLocation.getCol(), startLocation.getRow()))) {
					nextLocation = neighbour;
				}
			}
			// If the closest is walkable then return it
			if (isWalkable(nextLocation.getCol(), nextLocation.getRow())) {
				return nextLocation;
			}
			// Get neighbours of the closest and add to queue if they haven't already been checked
			getNeighbours(nextLocation, closedSet, queue);
			// Remove the invalid location
			queue.remove(nextLocation);
		}
		return null;
	}

	private static void getNeighbours(HexVector nextLocation, Set<HexVector> closedSet, LinkedList<HexVector> queue) {
		Tile nextLocationTile = GameManager.get().getWorld().getTile(nextLocation.getCol(), nextLocation.getRow());
		if (nextLocationTile != null) {
			for (int i = 0; i < 6; i++) {
				Tile neighbour = nextLocationTile.getNeighbour(i);
				if (!closedSet.contains(neighbour.getCoordinates())) {
					closedSet.add(neighbour.getCoordinates());
					queue.add(neighbour.getCoordinates());
				}
			}
		}
	}
}
