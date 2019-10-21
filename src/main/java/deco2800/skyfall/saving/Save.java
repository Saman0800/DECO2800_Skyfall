package deco2800.skyfall.saving;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.worlds.world.World;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that stores all aspects of the game that require saving. An instance
 * of this class represents a single save file
 */
public class Save implements Saveable<Save.SaveMemento>, Serializable {

    // The ID of this save
    private long saveID;

    // The worlds in this save
    private List<World> worlds;

    // The world the player is currently in
    private World currentWorld;

    private long currentWorldId;

    // The ID of the main character in this save
    private MainCharacter mainCharacter;

    private int gameStage;



    /**
     * Constructor for a save without parameters
     */
    public Save() {
        this(new ArrayList<>(), null, null);
    }

    /**
     * Constructor for a save state
     *
     * @param worlds The worlds in the save state
     * @param mainCharacter The main character in this save state
     */
    public Save(List<World> worlds, MainCharacter mainCharacter, World currentWorld) {
        this.saveID = System.nanoTime();

        this.worlds = worlds;

        this.mainCharacter = mainCharacter;
        this.currentWorld = currentWorld;
        this.gameStage = 0;
    }

    public Save(SaveMemento saveMemento) {
        this.load(saveMemento);
        if (this.worlds == null){
            this.worlds = new ArrayList<>();
        }
        this.mainCharacter = null;
        this.gameStage = saveMemento.gameStage;
    }

    public World getCurrentWorld() {
        return currentWorld;
    }

    public long getCurrentWorldId(){
        return currentWorldId;
    }

    /**
     * Returns the ID of this save state
     *
     * @return the ID of this save state
     */
    public long getSaveID() {
        return saveID;
    }

    /**
     * Sets the id of this save
     *
     * @param id the id of this save
     */
    public void setSaveID(long id) {
        this.saveID = id;
    }

    /**
     * Returns a list of ID's of the worlds in this save state
     *
     * @return a list of ID's of the worlds in this save state
     */
    public List<World> getWorlds() {
        return this.worlds;
    }

    /**
     * Returns the ID of the main character in this save state
     *
     * @return the ID of the main character in this save state
     */
    public MainCharacter getMainCharacter() {
        return this.mainCharacter;
    }

    /**
     * Sets the current world
     *
     * @param currentWorld the current world
     */
    public void setCurrentWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    public void setMainCharacter(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public void addWorld(World world) {
        this.worlds.add(world);
    }

    /**
     * Increases the game stage by one
     */
    public void incrementGameStage() {
        this.gameStage++;
    }

    /**
     * Returns the game stage
     *
     * @return the game stage
     */
    public int getGameStage() {
        return this.gameStage;
    }

    @Override
    public SaveMemento save() {
        return new SaveMemento(this);
    }


    @Override
    public void load(SaveMemento saveMemento) {
        if (this.worlds == null){
            this.worlds = new ArrayList<>();
        }
        this.saveID = saveMemento.saveID;
        this.currentWorldId = saveMemento.currentWorld;
        this.gameStage = saveMemento.gameStage;
    }

    /**
     * A savestate for the save
     */
    public static class SaveMemento implements AbstractMemento , Serializable {
        private long saveID;
        private long currentWorld;
        private int gameStage;

        private SaveMemento(Save save) {
            this.saveID = save.getSaveID();
            this.currentWorld = save.currentWorld.getID();
            this.gameStage = save.gameStage;
        }

        public long getWorldID() {
            return this.currentWorld;
        }

    }
}
