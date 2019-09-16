package deco2800.skyfall.entities;

import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, DatabaseManager.class, PlayerPeon.class})
public class StoneTest {
    private Stone stone;
    private GameManager gameManager;
    private World world;
    private MainCharacter mainCharacter;
    @Before
    public void setup() {
        mainCharacter = MainCharacter.getInstance(0,0,0.05f, "Main Piece", 10);
        stone=new Stone(-4, -2, mainCharacter);
        HexVector destination=new HexVector(mainCharacter.getCol(),mainCharacter.getRow());
        stone.moveTowards(destination);
        WorldBuilder builder = new WorldBuilder();
        WorldDirector.constructTestWorld(builder);
        world = builder.getWorld();
        gameManager = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        when(gameManager.getWorld()).thenReturn(world);
    }

    /**
     * To test the enemy type
     */
    @Test
    public void getEnemyType() {
        Assert.assertEquals(stone.getEnemyType(),"stone");
    }

    /**
     * To test the height of stone
     */
    @Test
    public void heightTest(){
        Assert.assertEquals(1,stone.getHeight());
    }

    /**
     * To test stone armour
     */
    @Test
    public void testArmour(){
        Assert.assertEquals(1,stone.getArmour());
    }


    /**
     * To test biome
     */
    @Test
    public void getBiome() {
        Assert.assertEquals(stone.getBiome(),"forest");
    }

    /**
     * To test Animations
     */
    @Test
    public void animationTest(){
        stone.setCurrentState(AnimationRole.MOVE);
        stone.setCurrentDirection(Direction.NORTH);

        AnimationLinker animationLinker = stone.getToBeRun();
        Assert.assertEquals(animationLinker.getAnimationName(), "stoneJN");
        Assert.assertEquals(animationLinker.getType(), AnimationRole.MOVE);

        stone.setCurrentState(AnimationRole.MOVE);
        stone.setCurrentDirection(Direction.NORTH_EAST);
        AnimationLinker animationLinker1 = stone.getToBeRun();
        Assert.assertEquals(animationLinker1.getAnimationName(), "stoneJNE");
        Assert.assertEquals(animationLinker1.getType(), AnimationRole.MOVE);

        stone.setCurrentState(AnimationRole.MOVE);
        stone.setCurrentDirection(Direction.NORTH_WEST);
        AnimationLinker animationLinker2= stone.getToBeRun();
        Assert.assertEquals(animationLinker2.getAnimationName(), "stoneJNW");
        Assert.assertEquals(animationLinker2.getType(), AnimationRole.MOVE);


        stone.setCurrentState(AnimationRole.MOVE);
        stone.setCurrentDirection(Direction.SOUTH);
        AnimationLinker animationLinker3 = stone.getToBeRun();
        Assert.assertEquals(animationLinker3.getAnimationName(), "stoneJS");
        Assert.assertEquals(animationLinker3.getType(), AnimationRole.MOVE);


        stone.setCurrentState(AnimationRole.MOVE);
        stone.setCurrentDirection(Direction.SOUTH_WEST);
        AnimationLinker animationLinker4 = stone.getToBeRun();
        Assert.assertEquals(animationLinker4.getAnimationName(), "stoneJSW");
        Assert.assertEquals(animationLinker4.getType(), AnimationRole.MOVE);

        stone.setCurrentState(AnimationRole.MOVE);
        stone.setCurrentDirection(Direction.SOUTH_EAST);
        AnimationLinker animationLinker5 = stone.getToBeRun();
        Assert.assertEquals(animationLinker5.getAnimationName(), "stoneJSE");
        Assert.assertEquals(animationLinker5.getType(), AnimationRole.MOVE);

    }


    /**
     * To test movement direction
     */
    @Test
    public void movementDirection() {
        Assert.assertEquals(stone.movementDirection(this.stone.position.getAngle()),Direction.NORTH_EAST);
    }

    /**
     * toString test
     */
    @Test
    public void toStringTest() {
        Assert.assertEquals(stone.toString(),"stone at (-3, -1) forest biome");
    }

    /**
     * To test moving direction
     */
    @Test
    public void getMovingDirection() {
        stone.attackPlayer(mainCharacter);
        Assert.assertEquals(stone.getMovingDirection(), Direction.NORTH_EAST);
    }

}