package deco2800.skyfall.entities.vehicle;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;

public class Bike extends AbstractVehicle {
    MainCharacter mc;
    private boolean isOnUse = false;
    private final String textureName = "bike";

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
    public String getName() {
        return textureName;
    }

    @Override
    public String getDescription() {
        return "bike";
    }

    public void removeBike(){
        GameManager.get().getWorld().removeEntity(this);
    }
}

