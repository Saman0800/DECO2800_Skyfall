package deco2800.skyfall.managers;

import deco2800.skyfall.Tickable;

public abstract class TickableManager extends AbstractManager implements Tickable {

	 public abstract void onTick(long i);
}
