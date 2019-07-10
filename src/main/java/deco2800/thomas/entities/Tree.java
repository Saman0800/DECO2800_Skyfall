package deco2800.thomas.entities;

import deco2800.thomas.Tickable;
import deco2800.thomas.worlds.AbstractWorld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tree extends AbstractEntity implements Tickable {
	private final Logger LOG = LoggerFactory.getLogger(Tree.class);

	AbstractWorld world;


	public Tree(AbstractWorld world, float col, float row) {
		super(col, row, 0);
		LOG.info("Making a tree at {}, {}", col, row);
		this.setTexture("tree_cubeH1A0");
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
		if (this.getCol() != otherTree.getCol() || this.getRow() != otherTree.getRow() || this.getHeight() != otherTree.getHeight()) {
			return false;
		}
		return true;
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