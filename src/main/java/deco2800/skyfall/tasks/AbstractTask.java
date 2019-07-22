package deco2800.skyfall.tasks;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.entities.AgentEntity;

public abstract class AbstractTask implements Tickable {
	
	protected AgentEntity entity;
	
	
	public AbstractTask(AgentEntity entity) {
		this.entity = entity;
	}
	
	public abstract boolean isComplete();

	public abstract boolean isAlive();

}
