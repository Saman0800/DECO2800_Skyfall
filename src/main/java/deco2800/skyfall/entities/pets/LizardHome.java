package deco2800.skyfall.entities.pets;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.Harvestable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.worlds.Tile;

import java.util.ArrayList;
import java.util.List;

public class LizardHome extends AbstractPet implements Animatable, Harvestable {
    MainCharacter mc;
    public LizardHome(float col, float row, MainCharacter mc){
        super(col, row);
        this.setTexture("lizardHome");
        this.setObjectName("lizardHome");
        this.setHeight(1);
        this.setHealth(3);
        this.setLevel(1);
        this.setSpeed(1);
        this.setArmour(1);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
        this.setCurrentDirection(Direction.SOUTH);
        this.setCurrentState(AnimationRole.NULL);
    }

    @Override
    public void onTick(long i) {
        this.setCollider();
    }

    @Override
    public void configureAnimations() {
//        this.addAnimations(
//                AnimationRole.NULL, Direction.SOUTH, new AnimationLinker("lizardHomeAttacked", AnimationRole.NULL, Direction.SOUTH,
//                        true, true));
    }

    @Override
    public void setDirectionTextures() {

    }


    public void cutlizardHomeTree(){
        this.setHealth(this.getHealth()-1);
    }

    @Override
    public List<AbstractEntity> harvest(Tile tile) {
        List<AbstractEntity> abstractEntityList=new ArrayList<>();
        Lizard dragon=new Lizard(0, 4,mc);
        dragon.setDomesticated(false);
        abstractEntityList.add(dragon);
        return abstractEntityList;
    }
}
