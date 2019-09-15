package deco2800.skyfall.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.DesertBiome;

public class Camel extends VehicleEntity {
    private AbstractBiome biomeType = new DesertBiome();
    private static final transient String BIOME = "desert";
    private MainCharacter mc;
    private boolean available = true;
    private boolean moving=false;
    private static final transient String VEHICLE = "camel";
    private static final transient int HEALTH = 10;

    public Camel(float col, float row, MainCharacter mc) {
        super(col,row);
        this.mc = mc;
        this.setTexture("camel_character");
        this.setObjectName("camel_character");
        this.setHeight(1);
        this.setAvailable(available);
        this.setHealth(HEALTH);
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
        //super.onTick(i);
        if (mc != null) {
            float columnDis = mc.getCol() - this.getCol();
            float rowDis = mc.getRow() - this.getRow();

            if ((columnDis * rowDis + rowDis * rowDis) < 4) {
                if (available){
                    //TODO: Let main character get onto vehicle

                }
            } else {

            }
        } else {
            System.out.println("Main Character is null");
        }

    }

    public String getVehicleType() {
        return VEHICLE;
    }

    public boolean getMove() {
        return this.moving;
    }

}
