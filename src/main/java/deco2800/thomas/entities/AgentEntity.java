package deco2800.thomas.entities;

import deco2800.thomas.util.HexVector;

public abstract class AgentEntity extends AbstractEntity{
	
	protected float speed;

	public AgentEntity(float col, float row, int height, float speed) {
		super(col, row, height);
		
		this.speed = speed;
	}

	public AgentEntity() {
		super();
	}

	public void moveTowards(HexVector destination) {
		position.moveToward(destination, speed);
	}
	
	public float getSpeed() {
		return speed;
	}
	
}
