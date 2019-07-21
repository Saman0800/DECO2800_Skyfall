package deco2800.thomas.entities;

import deco2800.thomas.Tickable;
import deco2800.thomas.util.HexVector;
import deco2800.thomas.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Tree extends StaticEntity implements Tickable {
	private static final Logger LOG = LoggerFactory.getLogger(Tree.class);

	public Tree(float col, float row, int renderOrder, Map<HexVector, String> texture) {
		super(col, row, renderOrder, texture);
		LOG.info("Making a tree at {}, {}", col, row);
		this.setTexture("tree_cubeH1A0");
	}

	public Tree(Tile tile, boolean obstructed) {
        super(tile, 5, "tree", obstructed);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof Tree)) {
			return false;
		}
		Tree otherTree = (Tree) other;
		if (this.getCol() != otherTree.getCol() || this.getRow() != otherTree.getRow()) {
			return false;
		}

		return this.getHeight() == otherTree.getHeight();
	}


	/**
	 * Gets the hashCode of the tree
	 *
	 * @return the hashCode of the tree
	 */
	@Override
	public int hashCode() {
		final float prime = 31;
		float result = 1;
		result = (result + super.getCol()) * prime;
		result = (result + super.getRow()) * prime;
		result = (result + super.getHeight()) * prime;
		return (int) result;
	}



	/**
	 * Animates the trees on every game tick
	 *
	 * @param tick
	 *            Current game tick
	 */
	@Override
	public void onTick(long tick) {
	}
}