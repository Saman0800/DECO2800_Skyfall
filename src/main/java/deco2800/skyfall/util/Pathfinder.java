package deco2800.skyfall.util;

import java.util.List;

import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.Tile;

public abstract class Pathfinder {
	
	public  abstract List<Tile> pathfind(AbstractWorld world, HexVector origin, HexVector destination);

}
