package deco2800.skyfall.buildings;

import com.badlogic.gdx.graphics.Texture;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Blueprint;

import java.util.Map;

public class DesertPortal extends AbstractPortal implements Blueprint {

    public String currentBiome = "desert";
    public String name = "desertPortal";
    public boolean blueprintLearned = false;
    Texture texture;

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     */
    public DesertPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setTexture("portal_desert");
        this.setNext("mountain");

    }

}
