package deco2800.skyfall.entities.pets;

import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.Peon;

public abstract class AbstractPet extends Peon implements ICombatEntity{
    private int level;
    private int damage;
    private int health;
    private int armour;
    private boolean domesticated=false;
    private boolean isOutSide=false;
    private boolean isOnTheWay=false;
    private boolean isSummoned=false;

    public AbstractPet(float col, float row){
        this.setRow(row);
        this.setCol(col);
        this.setCollider();
    }
    public AbstractPet(float row, float col, String texturename,int health,int armour,int damage) {
        super(row, col, 0.2f,texturename,health);
        this.setTexture(texturename);
        this.setCollider();

    }


    public boolean isSummoned(){
        return isSummoned;
    }

    public void setSummoned(boolean summoned){
        this.isSummoned=summoned;
    }

    public void setDomesticated(boolean domesticated){
        this.domesticated=domesticated;
    }

    public boolean getDomesticated(){
        return this.domesticated;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level=level;
    }

    @Override
    public void takeDamage(int damage) {
        this.damage=damage;
    }

    @Override
    public void dealDamage(ICombatEntity entity) {

    }

    @Override
    public boolean canDealDamage() {
        return false;
    }

    @Override
    public int getArmour() {
        return this.armour;
    }


    public void setArmour(int armour){
        this.armour=armour;
    }
    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public int[] getResistanceAttributes() {
        return new int[0];
    }

    @Override
    public String[] getStatusIndicators() {
        return new String[0];
    }

    @Override
    public void setHealth(int health) {
        this.health=health;
    }
}
