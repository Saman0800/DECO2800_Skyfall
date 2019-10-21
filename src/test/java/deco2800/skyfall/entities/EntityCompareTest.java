package deco2800.skyfall.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class EntityCompareTest {
    AbstractEntity a1;
    AbstractEntity a2;
    EntityCompare comparer;

    class AbstractExtend extends AbstractEntity {

        public AbstractExtend() {
            super(0,0,0);
        }

        @Override
        public void onTick(long val) {}
    }

    @Before
    public void setup() {
        a1 = new AbstractExtend();
        a2 = new AbstractExtend();
        comparer = new EntityCompare();
    }

    @Test
    public void testCompare() {
        assertNotEquals(comparer.compare(a1, a2), 0);
        assertEquals(comparer.compare(a1, a1), 0);
    }
}
