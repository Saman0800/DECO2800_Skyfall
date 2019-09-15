package deco2800.skyfall.saving;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.worlds.world.World;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that stores all aspects of the game that require saving
 */
public class Save implements Saveable<Save.SaveMemento>, Serializable {

    // The ID of this save
    private long saveID;

    // TODO delete
    // The worlds in this save
    private List<World> worlds;


    private World currentWorld;


    // TODO delete
    // The ID of the main character in this save
    private MainCharacter mainCharacter;

    /**
     * Constructor for a save state
     *
     * @param worlds The worlds in the save state
     * @param mainCharacter The main character in this save state
     */
    public Save(List<World> worlds, MainCharacter mainCharacter) {
        // FIXME: this will become deprecated at 11:47:16pm AEST, Friday, April 11, 2262
        this.saveID = System.nanoTime();

        this.worlds = worlds;

        this.mainCharacter = mainCharacter;
    }

    public Save(SaveMemento saveMemento) {
        this.load(saveMemento);
        this.worlds = new ArrayList<>();
        this.mainCharacter = null;
    }

    public World getCurrentWorld() {
        return currentWorld;
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

    public void setMainCharacter(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public void addWorld(World world) {
        this.worlds.add(world);
    }

    @Override
    public SaveMemento save() {
        return new SaveMemento(this);
    }

    @Override
    public void load(SaveMemento saveMemento) {
        this.saveID = saveMemento.saveID;
    }

    /**
     * A savestate for the save
     */
    class SaveMemento extends AbstractMemento {
        private long saveID;

        private SaveMemento(Save save) {
            saveID = save.getSaveID();
        }

    }
}
