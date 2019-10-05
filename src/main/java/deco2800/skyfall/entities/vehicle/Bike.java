package deco2800.skyfall.entities.vehicle;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;



public class Bike extends AbstractVehicle implements Animatable, Item {
    MainCharacter mc;
    private boolean isOnUse = false;
    private final String textureName = "bike";
    private Direction movingDirection;

    public Bike(float col, float row, MainCharacter mc){
        super(col, row, "bike");
        this.setTexture(textureName);
        this.setObjectName(textureName);
        this.setHeight(1);
        this.setHealth(10);
        this.setSpeed(0.00f);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
    }


    public boolean isOnUse(){
        this.removeBike();
        return this.isOnUse;
    }





    @Override
    public void configureAnimations() {
//        this.addAnimations(
//                AnimationRole.MOVE, Direction.NORTH, new AnimationLinker("whitebearN", AnimationRole.MOVE, Direction.NORTH,
//                        true, true));
//
//        this.addAnimations(
//                AnimationRole.MOVE, Direction.NORTH_EAST, new AnimationLinker("whitebearNE", AnimationRole.MOVE, Direction.NORTH_EAST,
//                        true, true));
//        this.addAnimations(
//                AnimationRole.MOVE, Direction.NORTH_WEST, new AnimationLinker("whitebearNW", AnimationRole.MOVE, Direction.NORTH_WEST,
//                        true, true));
//        this.addAnimations(
//                AnimationRole.MOVE, Direction.SOUTH, new AnimationLinker("whitebearS", AnimationRole.MOVE, Direction.SOUTH,
//                        true, true));
//        this.addAnimations(
//                AnimationRole.MOVE, Direction.SOUTH_EAST, new AnimationLinker("whitebearSE", AnimationRole.MOVE, Direction.SOUTH_EAST,
//                        true, true));
//        this.addAnimations(
//                AnimationRole.MOVE, Direction.SOUTH_WEST, new AnimationLinker("whitebearSW", AnimationRole.MOVE, Direction.SOUTH_WEST,
//                        true, true));
    }

    @Override
    public void setDirectionTextures() {

    }

    @Override
    public String getName() {
        return textureName;
    }

    @Override
    public String getSubtype() {
        return "vehicle";
    }

    @Override
    public Boolean isCarryable() {
        return true;
    }

    @Override
    public HexVector getCoords() {
        return new HexVector(this.getCol(),this.getRow());
    }

    @Override
    public Boolean isExchangeable() {
        return true;
    }

    @Override
    public String getDescription() {
        return "bike";
    }

    @Override
    public void use(HexVector position) {

    }

    @Override
    public Boolean isEquippable() {
        return null;
    }


    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public void dealDamage(MainCharacter mc) {

    }


    @Override
    public boolean canDealDamage() {
        return false;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public int[] getResistanceAttributes() {
        return new int[0];
    }

    public void removeBike(){
        GameManager.get().getWorld().removeEntity(this);
    }
}

