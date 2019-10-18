package deco2800.skyfall.util;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;

import java.util.List;

public interface Pathfinder {
	
	List<Tile> pathfind(World world, HexVector origin, HexVector destination);

}
