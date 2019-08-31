package deco2800.skyfall.entities;

import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

public class Stone extends EnemyEntity {
    private static final transient int HEALTH = 10;
    private static final transient float ATTACK_RANGE = 0.5f;
    private static final transient int ATTACK_SPEED = 2000;
    private static final transient String BIOME = "forest";
    private boolean moving = false;
    private float originalCol;
    private float orriginalRow;
    private boolean moved = false;
    private static final transient String ENEMY_TYPE = "stone";

    public Stone(float col, float row) {
        super(col, row);
        this.originalCol = col;
        this.orriginalRow = row;
        this.setTexture("enemyStone");
        this.setObjectName("enemyStone");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(1);
        this.setSpeed(1);
        this.setArmour(1);
    }


    public Stone(float row, float col, String texturename, int health, int armour, int damage) {
        super(row, col, texturename, health, armour, damage);
    }


    public String getEnemyType() {
        return ENEMY_TYPE;
    }

    /**
     * To determine whether this enemy can move
     *
     * @return boolean value moving
     */
    public boolean getMoving() {
        return moving;
    }

    public String getBiome() {
        return BIOME;
    }

    int period = 0;

    /**
     * Handles tick based stuff, e.g. movement
     */
//    @Override
//    public void onTick(long i) {
//        if (period<=30){
//            period++;
//        }else{
//            period=0;
//            super.onTick(i);
//            if (task ==null){
//                randomMoving();
//            }else{
//                if (task != null && task.isAlive()) {
//                    task.onTick(i);
//
//                    if (task.isComplete()) {
//                        randomMoving();
//
//                    }
//                }
//
//            }
//        }
//
//
//    }
    private void randomMoving() {

        if (this.getCol() == this.originalCol && this.getRow() == this.orriginalRow) {
            float[] currentPosition = WorldUtil.colRowToWorldCords(this.getCol(), this.getRow());
            float[] randomPosition = {(float) (Math.random() * 100 + currentPosition[0]), (float) (Math.random() * 100 + currentPosition[1])};
            float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow(randomPosition[0], randomPosition[1]);
            this.task = new MovementTask(this, new HexVector(randomPositionWorld[0], randomPositionWorld[1]));
        } else {
            this.task = new MovementTask(this, new HexVector(this.originalCol, this.orriginalRow));
        }


//        Map neighbourTiles = GameManager.get().getWorld().getTile(this.originalCol, this.orriginalRow).getNeighbours();
//        Tile targetTile = (Tile) neighbourTiles.get((int) (Math.random() * neighbourTiles.size()));
//        System.out.println(targetTile.getCol() + "," + targetTile.getRow());
//        if (moved == false) {
//            this.task = new MovementTask(this, new HexVector(targetTile.getCol(), targetTile.getRow()));
//            this.setRow(targetTile.getRow());
//            this.setCol(targetTile.getCol());
//        } else {
//            this.task = new MovementTask(this, new HexVector(originalCol, orriginalRow));
//            this.setRow(this.orriginalRow);
//            this.setCol(this.originalCol);
//        }
    }

    /**
     * @return string representation of this class including its enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(), (int) getCol(), (int) getRow(), getBiome());
    }

}

