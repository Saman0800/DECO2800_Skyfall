package deco2800.skyfall.buildings;

import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Blueprint;

public class MountainPortal extends AbstractPortal implements Blueprint {

    public String currentBiome = "mountain";
    public String name = "mountainPortal";
    Texture texture;
    private final transient Logger logger = LoggerFactory.getLogger(BuildingEntity.class);

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     */
    public MountainPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setTexture("portal_mountain");
        this.setNext("volcanic_mountain");

    }

    @Override
    public void onTick(long i) {
        // do nothing so far
    }

    public void unlocknext(MainCharacter character) {
        super.unlocknext(character, nextBiome);
    }

}
