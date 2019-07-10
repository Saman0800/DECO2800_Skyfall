package deco2800.thomas.worlds;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.HexVector;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * AbstractWorld is the Game AbstractWorld
 *
 * It provides storage for the WorldEntities and other universal world level items.
 */
public abstract class AbstractWorld {

    protected List<AbstractEntity> entities = new CopyOnWriteArrayList<>();

    protected int width;
    protected int length;

    protected CopyOnWriteArrayList<Tile> tiles;

    protected List<AbstractEntity> entitiesToDelete = new CopyOnWriteArrayList<>();
    protected List<Tile> tilesToDelete = new CopyOnWriteArrayList<>();

    protected AbstractWorld() {
    	tiles = new CopyOnWriteArrayList<Tile>();

    	generateWorld();
    	generateNeighbours();
    	generateTileIndexes();
    	Collections.sort(tiles);
    }
    
    
    protected abstract void generateWorld();
    
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
}
