package deco2800.skyfall.entities;

import com.google.gson.annotations.Expose;

import deco2800.skyfall.util.HexVector;

public abstract class AgentEntity extends AbstractEntity{
	@Expose
	protected float speed;


	protected boolean isMoving;
	protected double angle;

	public AgentEntity(float col, float row, int renderOrder, float speed) {
		super(col, row, renderOrder);
        this.isMoving = false;
        this.angle = 0;
		this.speed = speed;
	}

	public AgentEntity() {
		super();
	}

	/*Gets reset in the Peon class*/
	public void moveTowards(HexVector destination) {
		position.moveToward(destination, speed);
		isMoving = true;
		angle = position.getAngle();
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

    public boolean isMoving() {
        return isMoving;
    }

    public double getAngle() {
        return angle;
    }

}
