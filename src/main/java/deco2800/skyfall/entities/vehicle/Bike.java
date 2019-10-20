package deco2800.skyfall.entities.vehicle;


import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;

public class Bike extends AbstractVehicle {
    
    private final static String textureName = "bike";
    private boolean isOnUse = false;
    MainCharacter mc;


    public Bike(float col, float row, MainCharacter mc) {
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

    public boolean isOnUse() {
        this.removeBike();
        return this.isOnUse;
    }

    @Override
    public String getName() {
        return Bike.textureName;
    }

    @Override
    public String getDescription() {
        return "bike";
    }

    public void removeBike() {
        GameManager.get().getWorld().removeEntity(this);
    }
}
