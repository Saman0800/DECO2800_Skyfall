//package deco2800.skyfall.entities.worlditems;
//
//import deco2800.skyfall.entities.AbstractEntity;
//import deco2800.skyfall.entities.Collectable;
//import deco2800.skyfall.managers.GameManager;
//import deco2800.skyfall.resources.Item;
//import deco2800.skyfall.util.HexVector;
//import deco2800.skyfall.util.WorldUtil;
//import deco2800.skyfall.worlds.world.World;
//
//import java.util.Random;
//
//public class Gold extends AbstractEntity implements Item {
//    private static final int Move_RANGE = 10;
//
//    // how violently should the cube wiggle
//    private static final float VELOCITY = 0.001f;
//
//    public Gold(float col, float row) {
//        super(col, row, 2);
//        this.setTexture("gold");
//        this.setHeight(1);
//        this.setObjectName("gold");
//    }
//
//    @Override
//    public void onTick(long i) {
//        // a little wiggle to disperse the cubes
//        // this would ideally be replaced with slowly moving up and down as
//        // most games do
////        Random random = new Random();
////        int targetX = random.nextInt(Move_RANGE) - (Move_RANGE / 2);
////        int targetY = random.nextInt(Move_RANGE) - (Move_RANGE / 2);
////        HexVector newPosition = new HexVector(targetX, targetY);
////        position.moveToward(newPosition, VELOCITY);
//    }
//
//
//}
