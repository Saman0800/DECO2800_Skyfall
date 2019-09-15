package deco2800.skyfall.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.DesertBiome;

public class Camel extends VehicleEntity implements Animatable {
    private AbstractBiome biomeType = new DesertBiome();
    private static TextureManager textureManager;
    private Stage stage;
    private static final transient String BIOME = "desert";
    private MainCharacter mc;
    private boolean available = true;
    private static final transient String VEHICLE = "camel";


    public Camel(float col, float row, MainCharacter mc) {
        super(col,row);
        this.mc = mc;
        this.setTexture("camel_character");
        this.setHeight(1);
        this.setObjectName("camel_character");
        this.setAvailable(available);
    }

    public Camel(float row, float col, String textureName, int damage) {
        super(row, col, textureName, damage);
    }

    public void setBiome(AbstractBiome biomeType){
        this.biomeType = biomeType;
    }

    public String getBiome() {
        return BIOME;
    }

    @Override
    public void onTick(long i) {

    }

    public String getVehicleType() {
        return VEHICLE;
    }

    @Override
    public void configureAnimations() {

    }

    @Override
    public void setDirectionTextures() {

    }
}
