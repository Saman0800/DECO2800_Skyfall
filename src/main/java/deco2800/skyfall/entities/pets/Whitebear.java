package deco2800.skyfall.entities.pets;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;
import java.util.List;

public class Whitebear extends AbstractPet implements Animatable, Item {
    MainCharacter mc;
    private boolean isOutSide = false;
    private boolean isOnTheWay = false;
    private static final String TEXTURENAME = "whitebear";
    private Direction movingDirection;

    public Whitebear(float col, float row, MainCharacter mc) {
        super(col, row);
        this.setTexture(TEXTURENAME);
        this.setObjectName(TEXTURENAME);
        this.setHealth(10);
        this.setLevel(1);
        this.setSpeed(0.04f);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
    }

    public boolean getOutSide() {
        return this.isOutSide;
    }

    public void setOutSide(boolean outSide) {
        this.isOutSide = outSide;
    }

    @Override
    public void onTick(long i) {
        findNearbyGold();
        followingCharacter();
    }

    public void findNearbyGold() {
        List<AbstractEntity> abstractEntityList = GameManager.get().getWorld().getSortedEntities();
        for (AbstractEntity ae : abstractEntityList) {

            boolean isGoldPiece = ae instanceof GoldPiece;
            boolean inRange = ae.getPosition().distance(this.getPosition()) < 3;

            if (isGoldPiece && inRange && this.getDomesticated()) {
                isOnTheWay = true;
                HexVector aeposition = ae.getPosition();
                this.moveTowards(aeposition);
                // when the pet is close enough to the coin, then pick up, otherwise move to the
                // coin
                if (ae.getPosition().distance(this.getPosition()) < 0.1) {
                    mc.addGold((GoldPiece) ae, ((GoldPiece) ae).getValue());
                    GameManager.get().getWorld().removeEntity(ae);
                    isOnTheWay = false;
                }
            }
        }
    }

    public void followingCharacter() {
        setCurrentState(AnimationRole.MOVE);
        if (this.getDomesticated() && !isOnTheWay) {
            // keep at the right hand side of the mc
            HexVector destination = new HexVector(mc.getCol() - 1, mc.getRow() - 1);
            setCurrentDirection(movementDirection(this.position.getAngle()));
            this.position.moveToward(destination, this.getSpeed());
            // when the pet arrive then make it face to the player
            if (destination.getCol() == this.getCol() && destination.getRow() == this.getRow()) {
                if (movingDirection == Direction.NORTH) {
                    movingDirection = Direction.SOUTH;
                    setCurrentDirection(Direction.SOUTH);
                } else if (movingDirection == Direction.NORTH_WEST || movingDirection == Direction.SOUTH_EAST) {
                    movingDirection = Direction.SOUTH_WEST;
                    setCurrentDirection(Direction.SOUTH_WEST);
                }
            } else {
                movingDirection = movementDirection(this.position.getAngle());
            }
        }
    }

    @Override
    public void configureAnimations() {
        this.addAnimations(AnimationRole.MOVE, Direction.NORTH,
                new AnimationLinker("whitebearN", AnimationRole.MOVE, Direction.NORTH, true, true));

        this.addAnimations(AnimationRole.MOVE, Direction.NORTH_EAST,
                new AnimationLinker("whitebearNE", AnimationRole.MOVE, Direction.NORTH_EAST, true, true));
        this.addAnimations(AnimationRole.MOVE, Direction.NORTH_WEST,
                new AnimationLinker("whitebearNW", AnimationRole.MOVE, Direction.NORTH_WEST, true, true));
        this.addAnimations(AnimationRole.MOVE, Direction.SOUTH,
                new AnimationLinker("whitebearS", AnimationRole.MOVE, Direction.SOUTH, true, true));
        this.addAnimations(AnimationRole.MOVE, Direction.SOUTH_EAST,
                new AnimationLinker("whitebearSE", AnimationRole.MOVE, Direction.SOUTH_EAST, true, true));
        this.addAnimations(AnimationRole.MOVE, Direction.SOUTH_WEST,
                new AnimationLinker("whitebearSW", AnimationRole.MOVE, Direction.SOUTH_WEST, true, true));
    }

    @Override
    public void setDirectionTextures() {
        // Do nothing for now.
    }

    @Override
    public String getName() {
        return TEXTURENAME;
    }

    @Override
    public String getDescription() {
        return "pet whitebear";
    }
}
