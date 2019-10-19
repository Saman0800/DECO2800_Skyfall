package deco2800.skyfall.util;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BFSPathfinder implements Pathfinder {
	
	private final Logger log = LoggerFactory.getLogger(BFSPathfinder.class);

	public List<Tile> pathfind(World world, HexVector origin, HexVector destination) {

		Tile originTile = getTileByHexVector(world, origin);
		Tile destinationTile = getTileByHexVector(world, destination);
		if (originTile == null || destinationTile == null) {
			return new LinkedList<>();
		}
		return pathfindBFS(originTile, destinationTile);
	}

	private List<Tile> pathfindBFS(Tile origin, Tile destination) {
		LinkedList<Tile> queue = new LinkedList<>();
		Set<Tile> closedSet = new HashSet<>();
		Map<Tile, Tile> path = new HashMap<>();
		queue.add(origin);
		
		Tile root;

		while (!queue.isEmpty()) {
			root = queue.remove();
			
			if (root.equals(destination)) {
				return reconstructPath(destination, path);
			}
			
			for (Tile child : root.getNeighbours().values()) {
				if (closedSet.contains(child) || queue.contains(child) || child.isObstructed()) {
					continue;
				}
				path.put(child, root);
				queue.add(child);
			}
			
			closedSet.add(root);
		}
		return Collections.emptyList();
	}
	
	private List<Tile> reconstructPath(Tile destination, Map<Tile, Tile> pathMap) {
		Tile root = destination;
		List<Tile> path = new ArrayList<>();
		path.add(root);
		while(pathMap.get(root) != null) {
			root = pathMap.get(root);
			path.add(0, root);
		}
		return path;
	}

	private Tile getTileByHexVector(World world, HexVector vector) {
		for (Tile tile : world.getTileMap()) {
			if ((int) tile.getCol() == (int) vector.getCol() && (int) tile.getRow() == (int) vector.getRow()) {
				return tile;
			}
		}
		return null;
	}

}
