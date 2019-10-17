package deco2800.skyfall.tasks;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.PathFindingService;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

import java.util.List;

public class MovementTask extends AbstractTask{
	
	private boolean complete;
	
	private boolean computingPath = false;
	private boolean taskAlive = true;
	
	AgentEntity agentEntity;
	HexVector destination;
	
	private List<Tile> path;

	public MovementTask(AgentEntity entity, HexVector destination) {
		super(entity);
		
		this.agentEntity = entity;
		this.destination = destination;
		this.complete = false;
	}

	@Override
	public void onTick(long tick) {
		if(path != null) {
			//we have a path
			if(path.isEmpty()) {
				complete = true;
			} else {
				agentEntity.moveTowards(path.get(0).getCoordinates());
				//This is a bit of a hack.
				if(agentEntity.getPosition().isCloseEnoughToBeTheSame(path.get(0).getCoordinates())) {
					path.remove(0);
				}
			}			
		} else if (computingPath) {
			// change sprite to show waiting??
			
			
		} else {
			//ask for a path
			computingPath = true;
			GameManager.get().getManager(PathFindingService.class).addPath(this);
		}
	}

	@Override
	public boolean isComplete() {
		return complete;
	}

	public void setPath(List<Tile> path) {
		if (path == null) {
			taskAlive = false;
		}
		this.path = path;
		computingPath = false;
	}
	
	public List<Tile> getPath() {
		return path;
	}

	@Override
	public boolean isAlive() {
		return taskAlive;
	}
}
