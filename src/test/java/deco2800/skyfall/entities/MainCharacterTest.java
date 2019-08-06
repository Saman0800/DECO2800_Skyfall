package deco2800.skyfall.entities;

import org.junit.*;

public class MainCharacterTest {

    private MainCharacter testCharacter = new MainCharacter(0f, 0f, 0.05f,
            "Main " +
            "Piece",
            10);

    @Test
    public void simpleTest() {
        Assert.assertEquals(testCharacter.getName(), "Main Piece");
        testCharacter.setName("Side Piece");
        Assert.assertEquals(testCharacter.getName(), "Side Piece");

        Assert.assertEquals(testCharacter.getHealth(), 10);
        testCharacter.changeHealth(-5);
        Assert.assertEquals(testCharacter.getHealth(), 5);
        testCharacter.changeHealth(15);
        Assert.assertEquals(testCharacter.getHealth(), 20);
    }
}
