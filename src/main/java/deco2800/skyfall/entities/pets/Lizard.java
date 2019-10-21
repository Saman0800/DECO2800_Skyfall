package deco2800.skyfall.entities.pets;

import deco2800.skyfall.animation.Animatable;
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
    // check whether this pet is summoned
    private boolean isOutSide = false;

    private String lizardString = "lizard";

    // Checking whether this pet is on the way to collect money
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
        this.setTexture(lizardString);
        this.setObjectName(lizardString);
        this.setHealth(10);
        this.setLevel(1);
        this.setSpeed(0.08f);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
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
        List<AbstractEntity> abstractEntityList = GameManager.get().getWorld().getSortedEntities();
        for (AbstractEntity ae : abstractEntityList) {

            boolean isInstanceGoldPiece = ae instanceof GoldPiece;
            boolean inRange = ae.getPosition().distance(this.getPosition()) < 3;

            if (isInstanceGoldPiece && inRange && this.getDomesticated()) {
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

    /**
     * The pet will follow main character
     */
    public void followingCharacter() {
        if (this.getDomesticated() && !isOnTheWay) {
            HexVector destination = new HexVector(mc.getCol() - 1, mc.getRow() - 1);
            moveTowards(destination);
        }
    }

    @Override
    public void configureAnimations() {
        // Do nothing for now
    }

    /**
     * set different direction texture
     */
    @Override
    public void setDirectionTextures() {
        defaultDirectionTextures.put(Direction.EAST, lizardString + "E");
        defaultDirectionTextures.put(Direction.NORTH, lizardString + "N");
        defaultDirectionTextures.put(Direction.WEST, lizardString);
        defaultDirectionTextures.put(Direction.SOUTH, lizardString + "S");
        defaultDirectionTextures.put(Direction.NORTH_EAST, lizardString + "E");
        defaultDirectionTextures.put(Direction.NORTH_WEST, lizardString);
        defaultDirectionTextures.put(Direction.SOUTH_EAST, lizardString + "E");
        defaultDirectionTextures.put(Direction.SOUTH_WEST, lizardString);
    }

    /**
     * Get the name of pet
     *
     * @return name of pet
     */
    @Override
    public String getName() {
        return lizardString;
    }

    /**
     * Returns a description about the pet
     *
     * @return description about the pet
     */
    @Override
    public String getDescription() {
        return "pet lizard";
    }

}
