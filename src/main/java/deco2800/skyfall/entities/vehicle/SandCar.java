package deco2800.skyfall.entities.vehicle;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;


public class SandCar extends AbstractVehicle implements Animatable, Item {

    private static final transient String BIOME = "desert";
    private final String textureName = "sand_car";
    private boolean underUsing = false;
    MainCharacter mc;

    public SandCar(float col, float row, MainCharacter mc) {
        super(col, row, "sand_car");
        this.setTexture(textureName);
        this.setObjectName(textureName);
        this.setHeight(1);
        this.setHealth(10);
        this.setSpeed(0.00f);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
    }

    public String getBiome() {
        return BIOME;
    }

    public boolean isOnUse() {
        return underUsing;
    }

    @Override
    public void configureAnimations() {

    }

    @Override
    public void setDirectionTextures() {

    }

    @Override
    public String getName() {
        return textureName;
    }

    @Override
    public String getSubtype() {
        return "vehicle";
    }

    @Override
    public boolean isCarryable() {
        return true;
    }

    @Override
    public HexVector getCoords() {
        return new HexVector(this.getCol(),this.getRow());
    }

    @Override
    public boolean isExchangeable() {
        return true;
    }

    @Override
    public String getDescription() {
        return "sand_car";
    }

    @Override
    public void use(HexVector position) {

    }

    @Override
    public boolean isEquippable() {
        return true;
    }


    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public void dealDamage(MainCharacter mc) {

    }

    @Override
    public boolean canDealDamage() {
        return false;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public int[] getResistanceAttributes() {
        return new int[0];
    }

    public void removeSandCar(){
        GameManager.get().getWorld().removeEntity(this);
    }
}
