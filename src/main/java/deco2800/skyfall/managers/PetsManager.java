package deco2800.skyfall.managers;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.pets.AbstractPet;
import deco2800.skyfall.resources.Item;

import java.util.*;

public class PetsManager extends AbstractManager {
    private static final int MAX_PETS_Summon = 1;
    private Map<String, AbstractPet> petMap;
    private LinkedList<AbstractPet> petList;
    private InventoryManager inventoryManager;


    public PetsManager() {
        this.petMap = new HashMap<>();
        this.petList = new LinkedList<>();
        inventoryManager=GameManager.getManagerFromInstance(InventoryManager.class);
    }


    public  Collection<AbstractPet> allPets(){
        for(List<Item> itemList:this.inventoryManager.getInventoryContents().values()){
            for(Item item:itemList){
                if(item instanceof AbstractPet){
                    petMap.put(item.getName(), (AbstractPet) item);
                    petList.add((AbstractPet) item);
                }
            }
        }
        return petMap.values();
    }

    public AbstractPet currentSummonedPet(){
        for(AbstractPet ap:petMap.values()){
            if(ap.isSummoned()){
                return ap;
            }
        }
        return null;
    }

    public void replacePet(MainCharacter mc){
        System.out.println(currentSummonedPet());
        if(petList.size()>0){
            if(currentSummonedPet()==null){
                this.inventoryManager.quickAccessRemove(petList.get(0).getName());
                AbstractPet pet=petList.get(0);
                pet.setSummoned(true);
                pet.setDomesticated(true);
                GameManager.get().getWorld().addEntity(pet);
            }else{
                AbstractPet abstractPet=currentSummonedPet();
                int i=petPositionInList(abstractPet);
                if(i+1<petList.size()){
                    summonNext(i);
                }else{
                    i=0;
                    summonNext(i);
                }
            }
        }else{
            System.out.println("no pet available");
        }


    }



    private void summonNext(int i){
        AbstractPet pet=petList.get(i+1);
        petList.get(i).setSummoned(false);
        this.inventoryManager.quickAccessAdd(petList.get(i).getName());
        GameManager.get().getWorld().removeEntity(petList.get(i));
        petList.get(i+1).setSummoned(true);
        GameManager.get().getWorld().addEntity(pet);
    }

    public void feedPet(){

    }

    public int petPositionInList(AbstractPet abstractPet){
        for(int i=0;i<petList.size();i++){
            if(petList.get(i)==abstractPet){
                return i;
            }
        }
        return 0;
    }
    
    public boolean isSummoned(AbstractPet pet){
        if(pet.isSummoned()){
            return true;
        }
        return false;
    }

    public void sellPet(){

    }


}
