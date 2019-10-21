package deco2800.skyfall.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
            System.out.println("Invalid Settings File test failed to run, possibly due to a lack of permissions");
            return;
        }

        //should all revert to backup
        SettingsFile settingsFile = new SettingsFile("test1.ini");
        assertEquals(settingsFile.get("floatTest", 0.1), 0.1, 0.01);
        assertEquals(settingsFile.get("intTest", 10), 10);
        assertEquals(settingsFile.get("stringTest", "Hello Test"), "Hello Test");
        settingsFile.close();
    }

    @Test
    public void setSettingsFile() {
        //uses test file from empty setting file
        SettingsFile settingsFile = new SettingsFile("test2.ini");
        assertEquals(settingsFile.get("floatTest", 0.1), 0.1, 0.01);
        assertEquals(settingsFile.get("intTest", 10), 10);
        assertEquals(settingsFile.get("stringTest", "Hello Test"), "Hello Test");
        settingsFile.set("floatTest", 0.2);
        settingsFile.set("intTest", 2);
        settingsFile.set("stringTest", "Test");
        assertEquals(settingsFile.get("floatTest", 0.1), 0.2, 0.01);
        assertEquals(settingsFile.get("intTest", 10), 2);
        assertEquals(settingsFile.get("stringTest", "Hello Test"), "Test");
        settingsFile.close();
    }

    @After
    public void cleanUp() {
        //cleans up files made
        File file = new File("test.ini");
        file.delete();
        file = new File("test1.ini");
        file.delete();
        file = new File("test2.ini");
        file.delete();
    }

}
