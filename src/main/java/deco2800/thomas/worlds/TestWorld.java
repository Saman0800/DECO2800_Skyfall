package deco2800.thomas.worlds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.entities.Rock;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.Cube;
import deco2800.thomas.util.HexVector;

@SuppressWarnings("unused")
public class TestWorld extends AbstractWorld {
	/*
	 * radius for tiles 1 - 7 2 - 19 3 - 37 4 - 61 5 - 91 10 - 331 25 - 1951 50 -
	 * 7,651 100 - 30,301 150 - 67,951 200 - 120601
	 * 
	 * N = 1 + 6 * summation[0 -> N]
	 */
	boolean notGenerated = true;

	private static int RADIUS = 100;

	public TestWorld() {
		super();
	}

	//5 tile building
	private StaticEntity  createBuilding1(float col, float row) {
		StaticEntity building;
		
		Map<HexVector, String> textures = new HashMap<HexVector, String>();
		
		textures.put(new HexVector(1, -0.5f), "spacman_ded");
		textures.put(new HexVector(-1, -0.5f), "spacman_ded");
		textures.put(new HexVector(-1, 0.5f), "spacman_ded");
		textures.put(new HexVector(1, 0.5f), "spacman_ded");
		textures.put(new HexVector(0, 0), "spacman_ded");

		return  new StaticEntity(col, row, 1, textures);
		
	}
	
	//building with a fence
	private StaticEntity createBuilding2(float col, float row) {
		StaticEntity building;
		Map<HexVector, String> textures = new HashMap<HexVector, String>();
		
		textures = new HashMap<HexVector, String>();
		textures.put(new HexVector(0, 0), "buildingA");
		
		textures.put(new HexVector(-2, 1), "fenceNE-S");
		textures.put(new HexVector(-2, 0), "fenceN-S");
		textures.put(new HexVector(-2, -1), "fenceN-SE");
		
		textures.put(new HexVector(-1, 1.5f), "fenceNE-SW");
		textures.put(new HexVector(-1, -1.5f), "fenceNW-SE");
		
		//textures.put(new HexVector(2, 0), "fenceN-S");
		textures.put(new HexVector(0, 2), "fenceSE-SW");
		textures.put(new HexVector(0, -2), "fenceNW-NE");
		
		textures.put(new HexVector(1, -1.5f), "fenceNE-SW");
		textures.put(new HexVector(1, 1.5f), "fenceNW-SE");
		
		textures.put(new HexVector(2, 1), "fenceNW-S");
		//textures.put(new HexVector(2, 0), "fenceN-S");
		textures.put(new HexVector(2, -1), "fenceN-SW");

		return new StaticEntity(col, row, 1, textures);
		
	}


	//this get ran on first game tick so the world tiles exist.
	public void createBuildings() {
	


		Random random = new Random();
		int tileCount = GameManager.get().getWorld().getTileMap().size();
		// Generate some rocks to mine later
		for (int i = 0; i <1000;  i++) { 
			Tile t = GameManager.get().getWorld().getTile(random.nextInt(tileCount));
			entities.add(new Rock(t,true));
		}

		entities.add(createBuilding2(-5, 0.5f));

	}

	@Override
	protected void generateWorld() {
		Random random = new Random();
		for (int q = -1000; q < 1000; q++) {
			for (int r = -1000; r < 1000; r++) {
				if (Cube.cubeDistance(Cube.oddqToCube(q, r), Cube.oddqToCube(0, 0)) <= RADIUS) {
					
					int col = q;
					
					float oddCol = (col%2 !=0? 0.5f : 0);
					
					int elevation = random.nextInt(2);
					
					int rand = random.nextInt(8);
					String type = "grass_";
					

					type += elevation;

					tiles.add(new Tile(type, q, r+oddCol, elevation));
				}
			}
		}

		// Create the entities in the game
		addEntity(new PlayerPeon(0f, 0f, 0.05f));

	}

	@Override
	public void onTick(long i) {
		super.onTick(i);

		for (AbstractEntity e : this.getEntities()) {
			e.onTick(0);
		}

		if (notGenerated) {
			createBuildings();
			notGenerated = false;
		}
	}

}

/*
 * print out Neighbours for (Tile tile : tiles) { System.out.println();
 * System.out.println(tile); for (Entry<Integer, Tile> firend :
 * tile.getNeighbours().entrySet()) { switch (firend.getKey()) { case
 * Tile.north: System.out.println("north " +(firend.getValue())); break; case
 * Tile.north_east: System.out.println("north_east " + (firend.getValue()));
 * break; case Tile.north_west: System.out.println("north_west " +
 * (firend.getValue())); break; case Tile.south: System.out.println("south " +
 * (firend.getValue())); break; case Tile.south_east:
 * System.out.println("south_east " +(firend.getValue())); break; case
 * Tile.south_west: System.out.println("south_west " + (firend.getValue()));
 * break; } } }
 * 
 */
