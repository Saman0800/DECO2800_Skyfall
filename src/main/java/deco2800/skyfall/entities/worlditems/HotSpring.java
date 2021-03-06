package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;

public class HotSpring extends AbstractEntity {

    /**
     * Create a hot spring in forest biome
     *
     */
    // Entity ID of hot spring
    private static final String ENTITY_ID_STRING = "hot_spring";
    // Amount of recovering
    private static final int RECOVERHEALINGPOWER = 3;
    // Biome
    private static final transient String BIOME = "forest";
    // MainCharacter player
    private MainCharacter player;

    /**
     * Create a constructor of hot spring
     *
     * @param col    - the column of hot spring
     * @param row    - the row of hot spring
     * @param player - the character
     */
    public HotSpring(float col, float row, MainCharacter player) {
        super(col, row, 2);
        this.setTexture(ENTITY_ID_STRING);
        this.setHeight(0);
        this.setObjectName(ENTITY_ID_STRING);
        this.player = player;
    }

    /**
     * Tick of game
     *
     * @param i - a long number
     *
     */
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
            } else {
                this.setTexture(ENTITY_ID_STRING);
                this.setObjectName(ENTITY_ID_STRING);
            }
        }
    }

    /**
     * The amount of recovering
     */
    public int amountOfRecovering() {
        return RECOVERHEALINGPOWER;
    }

    /**
     * Get biome
     */
    public String getBiome() {
        return BIOME;
    }

    /**
     * Set the boolean recovering to true
     *
     * @param player - MainCharacter
     */
    public void Recovery(MainCharacter player) {
        if (!player.isRecovering()) {
            player.setRecovering(true);
        }
    }

}
