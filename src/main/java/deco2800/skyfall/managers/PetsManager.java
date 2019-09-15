package deco2800.skyfall.managers;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.pets.AbstractPet;
import deco2800.skyfall.resources.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PetsManager extends AbstractManager {
    //use map to store Pets Key is the name of pet
    //coder can use pet name to get pet
    private Map<String, AbstractPet> petMap;

    //Store pets in a list
    private LinkedList<AbstractPet> petList;

    //put pets in the character inventory
    private InventoryManager inventoryManager;

    //logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SoundManager.class);

    public PetsManager() {
        this.petMap = new HashMap<>();
        this.petList = new LinkedList<>();
        inventoryManager=GameManager.getManagerFromInstance(InventoryManager.class);
    }

    /**
     * add a pet into pets map
     * @param ap
     */
    public void addPet(AbstractPet ap){
        petMap.put(ap.getName(),ap);
        petList.add(ap);
    }

    /**
     * Get all of pets in the inventory
     * @return all of pets
     */
    public  LinkedList<AbstractPet> allPets(){
        return petList;
    }

    /**
     * The pet which is summoned right now
     * @return the summoned pet
     */
    public AbstractPet currentSummonedPet(){
        for(AbstractPet ap:petMap.values()){
            if(ap.isSummoned()){
                return ap;
            }
        }
        return null;
    }

    //index used to switch to next pet
    private int i=-1;

    /**
     * Summon another pet and put the original pet into inventory
     * @param mc main character
     */
    public void replacePet(MainCharacter mc){
        i++;
        if(petList.size()>0){
            //if no pet was summoned then call the first pet
            if(currentSummonedPet()==null){
                this.inventoryManager.quickAccessRemove(petList.get(0).getName());
                AbstractPet pet=petList.get(0);
                pet.setCol(mc.getCol()-1);
                pet.setRow(mc.getRow()-1);
                pet.setSummoned(true);
                pet.setDomesticated(true);
                GameManager.get().getWorld().addEntity(pet);
            }else{
                //switch to the next pet
                if(i<petList.size()){
                    summonNext(i,mc);
                }else{
                    i=0;
                    summonNext(i,mc);

                }
            }
        }else{
            LOGGER.info("no pet available right now");
        }


    }


    /**
     * Summon other pet
     * @param i index
     */
    private void summonNext(int i,MainCharacter mc){
        if(i==0){
            petList.getLast().setSummoned(false);
            this.inventoryManager.inventoryAdd((Item) petList.getLast());
            GameManager.get().getWorld().removeEntity(petList.getLast());
        }else{
            petList.get(i-1).setSummoned(false);
            this.inventoryManager.inventoryAdd((Item) petList.get(i-1));
            GameManager.get().getWorld().removeEntity(petList.get(i-1));
        }
        //the new pet appear in the world
        AbstractPet pet=petList.get(i);
        pet.setRow(mc.getRow()-1);
        pet.setCol(mc.getCol()-1);
        pet.setSummoned(true);
        pet.setDomesticated(true);
        GameManager.get().getWorld().addEntity(pet);

    }

    /**
     * Return the index of pet in the map
     * @param abstractPet target pet
     * @return index of pet
     */
    public int petPositionInList(AbstractPet abstractPet){
        for(int i=0;i<petList.size();i++){
            if(petList.get(i)==abstractPet){
                return i;
            }
        }
        return 0;
    }

    /**
     * To check whether that pet is summoned
     * @param pet
     * @return
     */
    public boolean isSummoned(AbstractPet pet){
        if(pet.isSummoned()){
            return true;
        }
        return false;
    }



}
