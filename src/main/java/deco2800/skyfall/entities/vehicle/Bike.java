package deco2800.skyfall.entities.vehicle;


import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;

public class Bike extends AbstractVehicle {
    MainCharacter mc;
    private static final transient String BIOME = "forest";
    
    private static final String TEXTURENAME = "bike";
    private boolean isOnUse = false;


    public Bike(float col, float row, MainCharacter mc) {
        super(col, row, "bike");
        this.setTexture(TEXTURENAME);
        this.setObjectName(TEXTURENAME);
        this.setHeight(1);
        this.setHealth(10);
        this.setSpeed(0.00f);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
    }

    public String getBiome() {
        return BIOME;
    }

    @Override
    public String getName() {
        return Bike.TEXTURENAME;
    }

    @Override
    public String getDescription() {
        return "vehicle bike";
    }

    public void removeBike() {
        GameManager.get().getWorld().removeEntity(this);
    }
}
