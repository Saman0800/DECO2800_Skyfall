package deco2800.skyfall.entities.worlditems;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.DesertBiome;

public class DetectSand {

    private AbstractBiome biomeType = new DesertBiome();
    private static TextureManager textureManager;
    private Stage stage;

    public DetectSand(AbstractBiome biomeType){
        this.biomeType = biomeType;
    }

    public void putCharacter(){
        if(biomeType.getBiomeName()=="desert"){
            //System.out.println("this is desert");
            
        }
    }
}
