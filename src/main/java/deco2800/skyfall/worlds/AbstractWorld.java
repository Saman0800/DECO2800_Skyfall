package deco2800.skyfall.worlds;

import deco2800.skyfall.entities.*;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.delaunay.WorldGenNode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.*;
import java.io.FileWriter;


/**
 * AbstractWorld is the Game AbstractWorld
 *
 * It provides storage for the WorldEntities and other universal world level items.
 */
public abstract class AbstractWorld {

    protected List<AbstractEntity> entities = new CopyOnWriteArrayList<>();

    protected int width;
    protected int length;

    protected int worldSize;
    protected int nodeSpacing;

    //List that contains the world biomes
    protected ArrayList<AbstractBiome> biomes;

    protected CopyOnWriteArrayList<Tile> tiles;
    protected CopyOnWriteArrayList<WorldGenNode> worldGenNodes;

    protected List<AbstractEntity> entitiesToDelete = new CopyOnWriteArrayList<>();
    protected List<Tile> tilesToDelete = new CopyOnWriteArrayList<>();

    protected AbstractWorld(long seed, int worldSize, int nodeSpacing) {
        Random random = new Random(seed);

        this.worldSize = worldSize;
        this.nodeSpacing = nodeSpacing;

    	tiles = new CopyOnWriteArrayList<>();
        worldGenNodes = new CopyOnWriteArrayList<>();

    	tiles = new CopyOnWriteArrayList<Tile>();
//        worldGenNodes = new CopyOnWriteArrayList<>();
        biomes = new ArrayList<>();

    	generateWorld(random);
        generateNeighbours();
    	generateTileIndexes();
    	generateTileTypes(random);

    	//Saving the world for test, and likely saving and loading later
//    	try {
//            saveWorld("ExampleWorldOutput.txt");
//        } catch (IOException e){
//    	    System.out.println("Could not save world");
//        }

    }
    
    protected abstract void generateWorld(Random random);

    /**
     * Loops through all the biomes within the world and adds textures to the tiles which
     * determine their properties
     */
    public void generateTileTypes(Random random) {
        for (AbstractBiome biome : biomes){
            biome.setTileTextures(random);
        }
    }

    public void generateNeighbours() {
    //multiply coords by 2 to remove floats
    	Map<Integer, Map<Integer, Tile>> tileMap = new HashMap<Integer, Map<Integer, Tile>>();
		Map<Integer, Tile> columnMap;
		for(Tile tile : tiles) {
			columnMap = tileMap.getOrDefault((int)tile.getCol()*2, new HashMap<Integer, Tile>());
			columnMap.put((int) (tile.getRow()*2), tile);
			tileMap.put((int) (tile.getCol()*2), columnMap);
		}
		
		for(Tile tile : tiles) {
			int col = (int) (tile.getCol()*2);
			int row = (int) (tile.getRow()*2);
			
			//West
			if(tileMap.containsKey(col - 2)) {
				//North West
				if (tileMap.get(col - 2).containsKey(row + 1)) {
					tile.addNeighbour(Tile.NORTH_WEST,tileMap.get(col - 2).get(row+ 1));
				}
				
				//South West
				if (tileMap.get(col - 2).containsKey(row -  1)) {
					tile.addNeighbour(Tile.SOUTH_WEST,tileMap.get(col -2).get(row - 1));
				}
			}
			
			//Central
			if(tileMap.containsKey(col)) {
				//North
				if (tileMap.get(col).containsKey(row + 2)) {
					tile.addNeighbour(Tile.NORTH,tileMap.get(col).get(row + 2));
				}
				
				//South
				if (tileMap.get(col).containsKey(row - 2)) {
					tile.addNeighbour(Tile.SOUTH,tileMap.get(col).get(row - 2));
				}
			}
			
			//East
			if(tileMap.containsKey(col + 2)) {
				//North East
				if (tileMap.get(col + 2).containsKey(row+1)) {
					tile.addNeighbour(Tile.NORTH_EAST,tileMap.get(col + 2).get(row+1));
				}
				
				//South East
				if (tileMap.get(col + 2).containsKey(row- 1)) {
					tile.addNeighbour(Tile.SOUTH_EAST,tileMap.get(col + 2).get(row- 1));
				}
			}
		}
    }
    
    private void generateTileIndexes() {
    	for(Tile tile : tiles) {
    		tile.calculateIndex();
    	}
    }
    
    /**
     * Returns a list of entities in this world
     * @return All Entities in the world
     */
    public List<AbstractEntity> getEntities() {
        return new CopyOnWriteArrayList<>(this.entities);
    }
    
    /**
     *  Returns a list of entities in this world, ordered by their render level 
     *  @return all entities in the world 
     */
    public List<AbstractEntity> getSortedEntities(){
		List<AbstractEntity> e = new CopyOnWriteArrayList<>(this.entities);
    	Collections.sort(e);
		return e;
    }


    /**
     *  Returns a list of entities in this world, ordered by their render level 
     *  @return all entities in the world 
     */
    public List<AgentEntity> getSortedAgentEntities(){
        List<AgentEntity> e = this.entities
            .stream()
            .filter(p -> p instanceof AgentEntity)
            .map(p -> (AgentEntity) p)
            .collect(Collectors.toList());

    	Collections.sort(e);
		return e;
    }



    /**
     * Adds an entity to the world
     * @param entity the entity to add
     */
    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    /**
     * Removes an entity from the world
     * @param entity the entity to remove
     */
    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
    }

	public void setEntities(List<AbstractEntity> entities) {
		this.entities = entities;
	}

    public List<Tile> getTileMap() {
        return tiles;
    }
    
    
    public Tile getTile(float col, float row) {
    	return getTile(new HexVector(col,row));
    }
    
    public Tile getTile(HexVector position) {
        for (Tile t : tiles) {
        	if (t.getCoordinates().equals(position)) {
        		return t;
			}
		}
		return null;
    }
    
    public Tile getTile(int index) {
        for (Tile t : tiles) {
        	if (t.getTileID() == index) {
        		return t;
			}
		}
		return null;
    }

    public void setTileMap(CopyOnWriteArrayList<Tile> tileMap) {
        this.tiles = tileMap;
    }
    
    //TODO ADDRESS THIS..?
    public void updateTile(Tile tile) {
        for (Tile t : this.tiles) {
            if (t.getTileID() == tile.getTileID()) {
                if (!t.equals(tile)) {
                    this.tiles.remove(t);
                    this.tiles.add(tile);
                }
                return;
            }
        }
        this.tiles.add(tile);
    }

    //TODO ADDRESS THIS..?
    public void updateEntity(AbstractEntity entity) {
        for (AbstractEntity e : this.entities) {
            if (e.getEntityID() == entity.getEntityID()) {
                this.entities.remove(e);
                this.entities.add(entity);
                return;
            }
        }
        this.entities.add(entity);

        // Since MultiEntities need to be attached to the tiles they live on, setup that connection.
        if (entity instanceof StaticEntity) {
            ((StaticEntity) entity).setup();
        }
    }

    public void onTick(long i) {
        for (AbstractEntity e : entitiesToDelete) {
            entities.remove(e);
        }

        for (Tile t : tilesToDelete) {
            tiles.remove(t);
        }

        //Collision detection for entities
        for (AbstractEntity e1 : this.getEntities()) {
            e1.onTick(0);
//            if (e1 instanceof Projectile) {
//                break;
//            }

            Collider c1 = e1.getCollider();
            boolean collided = false;
            for (AbstractEntity e2 : this.getEntities()) {
                Collider c2 = e2.getCollider();
//                if (e2 instanceof Projectile) {
//                    break;
//                }

                if (e1 != e2 && c1.overlaps(c2)) {
                    collided = true;

                    //collision handler
                    this.handleCollision(e1, e2);
                    //System.out.println("Collision!");

                    break;
                }
            }
            //no collision
        }
    }

    public void deleteTile(int tileid) {
        Tile tile = GameManager.get().getWorld().getTile(tileid);
        if (tile != null) {
            tile.dispose();
        }
    }

    public void deleteEntity(int entityID) {
        for (AbstractEntity e : this.getEntities()) {
            if (e.getEntityID() == entityID) {
                e.dispose();
            }
        }
    }

    public void queueEntitiesForDelete(List<AbstractEntity> entities) {
        entitiesToDelete.addAll(entities);
    }

    public void queueTilesForDelete(List<Tile> tiles) {
        tilesToDelete.addAll(tiles);
    }


    /**
     * Adds a biome to a world
     * @param biome The biome getting added
     */
    public void addBiome(AbstractBiome biome){
        this.biomes.add(biome);
    }

    /**
     * Gets the list of biomes in a world
     */
    public ArrayList<AbstractBiome> getBiomes(){
        return this.biomes;
    }

    // e1 is the entity that created the collision
    public void handleCollision(AbstractEntity e1, AbstractEntity e2) {
        //TODO: implement proper game logic for collisions between different types of entities.
        // i.e. if (e1 instanceof Projectile && e2 instanceof Enemy) {
        // removeEntity(e2); removeEntity(e1); }
        if (e1 instanceof Projectile && !(e2 instanceof PlayerPeon)) {
            removeEntity(e2);
        } else if (e2 instanceof Projectile && !(e1 instanceof PlayerPeon)) {
            removeEntity(e1);
        }
    }


    public void saveWorld(String filename) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(worldToString());
        writer.close();
    }

    public String worldToString(){
        StringBuilder string = new StringBuilder();
        for (Tile tile : tiles){
            String out = String.format("%f, %f, %s, %s\n", tile.getCol(), tile.getRow(),
                    tile.getBiome().getBiomeName(), tile.getTextureName());
            string.append(out);
        }
        return string.toString();
    }
}
