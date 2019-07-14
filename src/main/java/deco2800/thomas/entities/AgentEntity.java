package deco2800.thomas.entities;

import com.google.gson.annotations.Expose;

import deco2800.thomas.util.HexVector;

public abstract class AgentEntity extends AbstractEntity{
	@Expose
	protected float speed;

	


	public AgentEntity(float col, float row, int renderOrder, float speed) {
		super(col, row, renderOrder);
		
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

	public void setSpeed( float speed) {
		this.speed = speed;
	}


}
