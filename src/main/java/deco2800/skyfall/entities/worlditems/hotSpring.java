package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;



public class hotSpring extends AbstractEntity {

    private static final String ENTITY_ID_STRING = "hot_spring";
    private static final int Recover_Healing_power = 3;
    private static final transient String BIOME = "forest";
    private MainCharacter player;


    public hotSpring(float col, float row, MainCharacter player) {
        super(col, row, 2);
        this.setTexture("hot_spring");
        this.setHeight(0);
        this.setObjectName(ENTITY_ID_STRING);
        this.player = player;
    }


    public void onTick(long i) {
        if (player != null) {
            float colDistance = player.getCol() - this.getCol();
            float rowDistance = player.getRow() - this.getRow();
            Recovery(player);
            if ((colDistance * colDistance + rowDistance * rowDistance) < 2) {
                this.setTexture("in_hot_spring");
                this.setObjectName("in_hot_spring");
                player.setRecovering(true);
                player.changeHealth(this.amountOfRecovering());

            }
            else {
                this.setTexture("hot_spring");
                this.setObjectName("hot_spring");
            }
        }
    }

    public void playerRecovery(MainCharacter player){
        float colDistance = player.getCol() - this.getCol();
        float rowDistance = player.getRow() - this.getRow();
        if((colDistance * colDistance + rowDistance * rowDistance) < 4){
            player.changeHealth(this.amountOfRecovering());


        }

    }

    public int amountOfRecovering(){
        return Recover_Healing_power;
    }
    public String getBiome() {
        return BIOME;
    }


    public void Recovery(MainCharacter player){
        if(!player.isRecovering()){
            player.setRecovering(true);
        }
    }



}
