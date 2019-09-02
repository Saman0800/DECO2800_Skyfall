package deco2800.skyfall.animation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class AnimationLinkerTest {

    AnimationLinker al;

    @Before
    public void setUp() {
        al = new AnimationLinker("Mock", AnimationRole.DEFENCE, Direction.EAST, true, false);
    }

    @Test
    public void incrTimeTest() {
        al.incrTime(2);
        assertTrue(al.getStartingTime() == 2f);
        al.resetStartingTime();
        assertTrue(al.getStartingTime() == 0f);
    }

    @Test
    public void completedTest() {
        al.incrTime(2);
        al.setCompleted(true);
        assertTrue(al.isCompleted());
    }

    //Rest of class is just getters and setters

}