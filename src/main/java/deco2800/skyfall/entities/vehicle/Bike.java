package deco2800.skyfall.entities.vehicle;


import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;

public class Bike extends AbstractVehicle {
    MainCharacter mc;
    private static final transient String BIOME = "forest";
    
    private static final String TEXTURENAME = "bike";


    public Bike(float col, float row, MainCharacter mc) {
        super(col, row, "bike");
        this.setObjectName(TEXTURENAME);
        this.setHealth(10);
        this.mc = mc;
        this.setSpeed(0.00f);
        this.setHeight(1);
        this.setTexture(TEXTURENAME);
        this.configureAnimations();
        this.setDirectionTextures();
    }


    @Override
    public String getName() {
        return Bike.TEXTURENAME;
    }

    public String getBiome() {
        return BIOME;
    }

    @Override
    public String getDescription() {
        return "vehicle bike";
    }

    public void removeBike() {
        GameManager.get().getWorld().removeEntity(this);
    }
}
