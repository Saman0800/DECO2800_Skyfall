package deco2800.skyfall.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.DesertBiome;

public class Horse extends VehicleEntity {
    private static TextureManager textureManager;
    private Stage stage;
    private static final transient String BIOME = "forest";
    private MainCharacter mc;
    private boolean available = true;
    private boolean moving=false;
    private static final transient String VEHICLE = "horse";
    private static final transient int HEALTH = 10;

    public Horse(float col, float row, MainCharacter mc) {
        super(col,row);
        this.mc = mc;
        this.setTexture("horse");
        this.setObjectName("horse");
        this.setHeight(1);
        this.setAvailable(available);
        this.setHealth(HEALTH);
    }

    public String getBiome() {
        return BIOME;
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);
        if (mc != null) {
            float colDistance = mc.getCol() - this.getCol();
            float rowDistance = mc.getRow() - this.getRow();

            if ((colDistance * colDistance + rowDistance * rowDistance) < 4){

                //TODO: Let main character get onto vehicle
                setTexture("horse_character");
                setObjectName("horse_character");


            } else {
                this.setCurrentState(AnimationRole.NULL);
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
