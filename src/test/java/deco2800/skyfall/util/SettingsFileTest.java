package deco2800.skyfall.util;

import java.io.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SettingsFileTest {
    @Before
    public void emptySettingsFile() {
        SettingsFile settingsFile = new SettingsFile("test.ini");
        assertEquals(settingsFile.get("floatTest", 0.1), 0.1, 0.01);
        assertEquals(settingsFile.get("intTest", 10), 10);
        assertEquals(settingsFile.get("stringTest", "Hello Test"), "Hello Test");
        settingsFile.close();
    }

    @Test
    public void fullSettingsFile() {
        //uses test file from empty setting file
        SettingsFile settingsFile = new SettingsFile("test.ini");
        assertEquals(settingsFile.get("floatTest", 0.3), 0.1, 0.01);
        assertEquals(settingsFile.get("intTest", 21), 10);
        assertEquals(settingsFile.get("stringTest", "Different option"), "Hello Test");
        settingsFile.close();
    }

    @Test
    public void invalidSettingFile() {
        try {
            PrintWriter file = new PrintWriter("test1.ini");
            file.print("floatTest hello");
            file.print("intTest NotAnInt");
            file.print("stringTest");
            file.close();
        }
        catch (FileNotFoundException ex) {
            //Test can't be conducted, leave
            return;
        }

        //should all revert to backup
        SettingsFile settingsFile = new SettingsFile("test1.ini");
        assertEquals(settingsFile.get("floatTest", 0.1), 0.1, 0.01);
        assertEquals(settingsFile.get("intTest", 10), 10);
        assertEquals(settingsFile.get("stringTest", "Hello Test"), "Hello Test");
        settingsFile.close();
    }

    @After
    public void cleanUp() {
        //cleans up file made
        File file = new File("test.ini");
        file.delete();
        file = new File("test1.ini");
        file.delete();
    }

}
