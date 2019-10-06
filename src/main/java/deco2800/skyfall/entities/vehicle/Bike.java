package deco2800.skyfall.entities.vehicle;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.Direction;
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
    public boolean isCarryable() {
        return true;
    }

    @Override
    public HexVector getCoords() {
        return new HexVector(this.getCol(),this.getRow());
    }

    @Override
    public boolean isExchangeable() {
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
    public boolean isEquippable() {
        return true;
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

