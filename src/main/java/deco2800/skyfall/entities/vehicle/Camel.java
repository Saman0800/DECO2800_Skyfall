package deco2800.skyfall.entities.vehicle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.DesertBiome;

public class Camel {

    private AbstractBiome biomeType = new DesertBiome();
    private static TextureManager textureManager;
    private Stage stage;

    public Camel(AbstractBiome biomeType){
        this.biomeType = biomeType;
    }
}
