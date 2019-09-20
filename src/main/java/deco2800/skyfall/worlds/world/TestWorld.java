package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.entities.worlditems.Rock;
import deco2800.skyfall.entities.worlditems.Tree;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.Cube;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class TestWorld extends World {

    boolean notGenerated = true;

    private static int RADIUS = 25;

    private static final UnaryOperator<WorldParameters> THROWER = wp -> {throw new UnsupportedOperationException();};

    public TestWorld(WorldParameters worldParameters) {
        super(THROWER.apply(worldParameters));
    }

    // 5 tile building
    private StaticEntity createBuilding1(float col, float row) {
        StaticEntity building;

        Map<HexVector, String> textures = new HashMap<>();

        textures.put(new HexVector(1, -0.5f), "spacman_ded");
        textures.put(new HexVector(-1, -0.5f), "spacman_ded");
        textures.put(new HexVector(-1, 0.5f), "spacman_ded");
        textures.put(new HexVector(1, 0.5f), "spacman_ded");
        textures.put(new HexVector(0, 0), "spacman_ded");

        return new StaticEntity(col, row, 1, textures);
    }

    // building with a fence
    private StaticEntity createBuilding2(float col, float row) {
        Map<HexVector, String> textures = new HashMap<>();
        textures.put(new HexVector(0, 0), "buildingA");

        textures.put(new HexVector(-2, 1), "fenceNE-S");
        textures.put(new HexVector(-2, 0), "fenceN-S");
        textures.put(new HexVector(-2, -1), "fenceN-SE");

        textures.put(new HexVector(-1, 1.5f), "fenceNE-SW");
        textures.put(new HexVector(-1, -1.5f), "fenceNW-SE");

        // textures.put(new HexVector(2, 0), "fenceN-S");
        textures.put(new HexVector(0, 2), "fenceSE-SW");
        textures.put(new HexVector(0, -2), "fenceNW-NE");

        textures.put(new HexVector(1, -1.5f), "fenceNE-SW");
        textures.put(new HexVector(1, 1.5f), "fenceNW-SE");

        textures.put(new HexVector(2, 1), "fenceNW-S");
        // textures.put(new HexVector(2, 0), "fenceN-S");
        textures.put(new HexVector(2, -1), "fenceN-SW");
        StaticEntity building = new StaticEntity(col, row, 1, textures);

        return building;
    }

    private void addTree(float col, float row) {
        Map<HexVector, String> textures = new HashMap<>();
        Tile t = GameManager.get().getWorld().getTile(col, row);
        Tree tree = new Tree(t, true);
        addEntity(tree);
    }

    // this get ran on first game tick so the world tiles exist.
    public void createBuildings() {
        Random random = new Random();
        int tileCount = GameManager.get().getWorld().getTileMap().size();
        // Generate some rocks to mine later
        for (int i = 0; i < 200; i++) {
            Tile t = GameManager.get().getWorld().getTile(random.nextInt(tileCount));
            if (t != null) {
                addEntity(new Rock(t, true));
            }
        }
        addEntity(createBuilding2(-5, 0.5f));
    }

    @Override
    protected void generateWorld() {
        AbstractBiome biome = new ForestBiome(random);
        for (int q = -1000; q < 1000; q++) {
            for (int r = -1000; r < 1000; r++) {
                if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <= RADIUS) {

                    int col = q;

                    float oddCol = (col % 2 != 0 ? 0.5f : 0);

                    int elevation = random.nextInt(2);

                    int rand = random.nextInt(8);

                    Tile tile = new Tile(this, q, r + oddCol);
                    addTile(tile);
                    biome.addTile(tile);
                }
            }
        }
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);

        for (AbstractEntity e : this.getEntities()) {
            e.onTick(0);
        }

        if (notGenerated) {
            createBuildings();
            addTree(-1, -3.5f);

            notGenerated = false;
        }
    }

}
