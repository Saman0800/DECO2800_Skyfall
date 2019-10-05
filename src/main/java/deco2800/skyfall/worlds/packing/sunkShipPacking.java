package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.entities.AbstractEntity;

public class sunkShipPacking extends AbstractEntity {

    public sunkShipPacking(float col, float row) {
        super(col, row, 2);
        this.setTexture("sunk_ship");
    }

    @Override
    public void onTick(long i) {

    }


}
