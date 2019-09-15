package deco2800.skyfall.entities.pets;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.Harvestable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Spider;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;

import java.util.ArrayList;
import java.util.List;

public class Whitebear extends AbstractPet implements Animatable, Item {
    MainCharacter mc;
    private boolean isOutSide=false;
    private boolean isOnTheWay=false;
    public Whitebear(float col, float row, MainCharacter mc){
        super(col, row);
        this.setTexture("whitebear");
        this.setObjectName("whitebear");
        this.setHeight(1);
        this.setHealth(10);
        this.setLevel(1);
        this.setSpeed(0.04f);
        this.setArmour(1);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
    }


    public boolean getOutSide(){
        return this.isOutSide;
    }
    public void setOutSide(boolean outSide){
        this.isOutSide=outSide;
    }
    @Override
    public void onTick(long i) {
        findNearbyGold();
        followingCharacter();
        this.setCollider();
    }

    public void findNearbyGold(){
        List<AbstractEntity> abstractEntityList=GameManager.get().getWorld().getSortedEntities();
        for(AbstractEntity ae:abstractEntityList){
            if(ae instanceof GoldPiece){
                if(this.getDomesticated()){
                    if(ae.getPosition().distance(this.getPosition())<3){
                        isOnTheWay=true;
                        HexVector aeposition =  ae.getPosition();
                        this.moveTowards(aeposition);
                        if(ae.getPosition().distance(this.getPosition())<0.1){
                            mc.addGold((GoldPiece) ae,((GoldPiece) ae).getValue());
                            GameManager.get().getWorld().removeEntity(ae);
                            isOnTheWay=false;
                        }
                    }
                }
            }
        }
    }


    public void followingCharacter(){
        if(this.getDomesticated()){
            if(!isOnTheWay){
                HexVector destination=new HexVector(mc.getCol()-1,mc.getRow()-1);
                moveTowards(destination);
            }
        }
    }

    @Override
    public void configureAnimations() {
    }

    @Override
    public void setDirectionTextures() {

    }

    @Override
    public String getName() {
        return "whitebear";
    }

    @Override
    public String getSubtype() {
        return "pets";
    }

    @Override
    public Boolean isCarryable() {
        return true;
    }

    @Override
    public HexVector getCoords() {
        return new HexVector(this.getCol(),this.getRow());
    }

    @Override
    public Boolean isExchangeable() {
        return true;
    }

    @Override
    public String getDescription() {
        return "pet whitebear";
    }
}

