package deco2800.skyfall.entities.pets;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;

import java.util.List;

public class Lizard extends AbstractPet implements Animatable, Item {
    MainCharacter mc;
    //check whether this pet is summoned
    private boolean isOutSide = false;

    //Checking whether this pet is on the way to collect money
    private boolean isOnTheWay = false;

    /**
     * Generate a lizard with a default settings
     *
     * @param col coordinate x
     * @param row coordinate y
     * @param mc  Main character
     */
    public Lizard(float col, float row, MainCharacter mc) {
        super(col, row);
        this.setTexture("lizard");
        this.setObjectName("lizard");
        this.setHeight(1);
        this.setHealth(10);
        this.setLevel(1);
        this.setSpeed(0.08f);
        this.setArmour(1);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
        this.setCurrentState(AnimationRole.NULL);
    }


    /**
     * Get situation is this pet is summoned
     *
     * @return true if this pet is summoned
     */
    public boolean getIsOutSide() {
        return this.isOutSide;
    }

    /**
     * To set is out side situation
     *
     * @param outSide situation of pets
     */
    public void setIsOutSide(boolean outSide) {
        this.isOutSide = outSide;
    }

    /**
     * To update lizard situation in each game second
     *
     * @param i
     */
    @Override
    public void onTick(long i) {
        findNearbyGold();
        followingCharacter();
        this.setCurrentDirection(movementDirection(position.getAngle()));

    }

    /**
     * To collect nearby gold
     */
    public void findNearbyGold() {
        List<AbstractEntity> abstractEntityList = GameManager.get().
                getWorld().getSortedEntities();
        for (AbstractEntity ae : abstractEntityList) {
            if (ae instanceof GoldPiece) {
                if (this.getDomesticated()) {
                    if (ae.getPosition().distance(this.getPosition()) < 3) {
                        isOnTheWay = true;
                        HexVector aeposition = ae.getPosition();
                        this.moveTowards(aeposition);
                        if (ae.getPosition().distance(this.getPosition()) < 0.1) {
                            mc.addGold((GoldPiece) ae, ((GoldPiece) ae).getValue());
                            GameManager.get().getWorld().removeEntity(ae);
                            isOnTheWay = false;
                        }
                    }
                }
            }
        }
    }


    /**
     * get movement direction
     *
     * @param angle the angle between to tile
     * @return direction
     */
    public Direction movementDirection(double angle) {
        angle = Math.toDegrees(angle - Math.PI);
        if (angle < 0) {
            angle += 360;
        }
        if (angle >= 0 && angle <= 60) {
            return Direction.SOUTH_WEST;
        } else if (angle > 60 && angle <= 120) {
            return Direction.SOUTH;
        } else if (angle > 120 && angle <= 180) {
            return Direction.SOUTH_EAST;
        } else if (angle > 180 && angle <= 240) {
            return Direction.NORTH_EAST;
        } else if (angle > 240 && angle <= 300) {
            return Direction.NORTH;
        } else if (angle > 300 && angle < 360) {
            return Direction.NORTH_WEST;
        }
        return null;

    }

    /**
     * The pet will follow main character
     */
    public void followingCharacter() {
        if (this.getDomesticated()) {
            if (!isOnTheWay) {
                HexVector destination = new HexVector(mc.getCol() - 1,
                        mc.getRow() - 1);
                moveTowards(destination);
            }
        }
    }

    @Override
    public void configureAnimations() {
    }

    /**
     * set different direction texture
     */
    @Override
    public void setDirectionTextures() {
        defaultDirectionTextures.put(Direction.EAST, "lizardE");
        defaultDirectionTextures.put(Direction.NORTH, "lizardN");
        defaultDirectionTextures.put(Direction.WEST, "lizard");
        defaultDirectionTextures.put(Direction.SOUTH, "lizardS");
        defaultDirectionTextures.put(Direction.NORTH_EAST, "lizardE");
        defaultDirectionTextures.put(Direction.NORTH_WEST, "lizard");
        defaultDirectionTextures.put(Direction.SOUTH_EAST, "lizardE");
        defaultDirectionTextures.put(Direction.SOUTH_WEST, "lizard");

    }

    /**
     * Get the name of pet
     *
     * @return name of pet
     */
    @Override
    public String getName() {
        return "lizard";
    }

    /**
     * Returns the subtype which the pet belongs to.
     *
     * @return pet type
     */
    @Override
    public String getSubtype() {
        return "pets";
    }

    /**
     * Returns whether or not the item can be carried
     *
     * @return true
     */
    @Override
    public Boolean isCarryable() {
        return true;
    }

    /**
     * Returns the co-ordinates of the tile the item is on
     *
     * @return coordinates
     */
    @Override
    public HexVector getCoords() {
        return new HexVector(this.getCol(), this.getRow());
    }

    /**
     * Returns whether or not the pet can be exchanged
     *
     * @return True if the pet can be exchanged, false otherwise
     */
    @Override
    public Boolean isExchangeable() {
        return true;
    }

    /**
     * Returns a description about the pet
     *
     * @returna description about the pet
     */
    @Override
    public String getDescription() {
        return "pet lizard";
    }

    @Override
    public void use(HexVector position) {

    }
}
