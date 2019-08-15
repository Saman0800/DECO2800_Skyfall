package deco2800.skyfall.tasks;

import java.util.List;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.AnimationRole;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.PathFindingService;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import javafx.animation.Animation;

public class MovementTask extends AbstractTask{
	
	private boolean complete;
	
	private boolean computingPath = false;
	private boolean taskAlive = true;
	
	AgentEntity entity;
	HexVector destination;
	
	private List<Tile> path;

	public MovementTask(AgentEntity entity, HexVector destination) {
		super(entity);
		
		this.entity = entity;
		this.destination = destination;
		this.complete = false;//path == null || path.isEmpty();
	}

	@Override
	public void onTick(long tick) {
		if(path != null) {
			//we have a path
			if(path.isEmpty()) {
				complete = true;
                updateAnimation();
			} else {
                updateAnimation();
				entity.moveTowards(path.get(0).getCoordinates());
				//This is a bit of a hack.
				if(entity.getPosition().isCloseEnoughToBeTheSame(path.get(0).getCoordinates())) {
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


	public void updateAnimation() {
        if (this.complete) {
            entity.setMovingAnimation(AnimationRole.NULL);
            return;
        }

        double angle = entity.getPosition().getAngle();
        final double TOL = 0.1;
        if (angle < Math.PI/6 + TOL && angle > Math.PI/6 - TOL) {
            System.out.println("Setting "+ AnimationRole.MOVE_NORTH_EAST.name() +" animation in " + entity.getObjectName());
            entity.setMovingAnimation(AnimationRole.MOVE_NORTH_EAST);

        } else if (angle < Math.PI/2 + TOL && angle > Math.PI/2 - TOL) {
            System.out.println("Setting "+ AnimationRole.MOVE_NORTH.name() + " animation in " + entity.getObjectName());
            entity.setMovingAnimation(AnimationRole.MOVE_NORTH);

        } else if (angle < 5*Math.PI/6 + TOL && angle > 5*Math.PI/6 - TOL) {
            System.out.println("Setting "+ AnimationRole.MOVE_NORTH_WEST.name() +" animation in " + entity.getObjectName());
            entity.setMovingAnimation(AnimationRole.MOVE_NORTH_WEST);


        } else if (angle < 7*Math.PI/6 + TOL && angle > Math.PI/6 - TOL) {
            System.out.println("Setting "+ AnimationRole.MOVE_SOUTH_WEST.name() +" animation in " + entity.getObjectName());
            entity.setMovingAnimation(AnimationRole.MOVE_SOUTH_WEST);

        } else if (angle < 3 * Math.PI/2 + TOL && angle > 3 * Math.PI/2 - TOL) {
            System.out.println("Setting "+ AnimationRole.MOVE_SOUTH.name() + " animation in " + entity.getObjectName());
            entity.setMovingAnimation(AnimationRole.MOVE_SOUTH);
        } else {
            System.out.println("Setting "+ AnimationRole.MOVE_SOUTH_EAST.name() + " animation in " + entity.getObjectName());
            entity.setMovingAnimation(AnimationRole.MOVE_SOUTH_EAST);
        }



    }
}
