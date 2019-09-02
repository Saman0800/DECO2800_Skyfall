package deco2800.skyfall.util;

import java.util.List;

import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.Tile;

public abstract class Pathfinder {
	
	public  abstract List<Tile> pathfind(World world, HexVector origin, HexVector destination);

}
