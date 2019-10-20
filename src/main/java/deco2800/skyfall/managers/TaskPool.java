package deco2800.skyfall.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.tasks.AbstractTask;
import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.Tile;

public class TaskPool extends AbstractManager {

	private List<AbstractTask> poolOfTasks;
	private World world;
	private Random random;

	public TaskPool() {
		poolOfTasks = new ArrayList<>();
		world = GameManager.get().getWorld();
		random = new Random();
	}
	
	public AbstractTask getTask(AgentEntity entity) {
		if (poolOfTasks.isEmpty()) {
			List<Tile> tiles = world.getLoadedChunks().values().stream().flatMap(chunk ->
					chunk.getTiles().stream()).collect(Collectors.toList());
			if (tiles.isEmpty()) {
				// There are no tiles
				return null;
			}
			Tile destination = tiles.get(random.nextInt(tiles.size()));
			return new MovementTask(entity, destination.getCoordinates());
		}
		return poolOfTasks.remove(0);
	}
}
