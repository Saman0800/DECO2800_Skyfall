package deco2800.skyfall.inventory;

import static org.junit.Assert.*;
import java.util.*;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.*;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {

    private Inventory test;

    @Before
    public void initialize() {

        Map<String, List<Item>> inv = new HashMap<>();
        List<Item> stoneList = new ArrayList<>();
        stoneList.add(new Stone());
        inv.put("Stone", stoneList);

        List<String> qai = new ArrayList<>();
        qai.add("Stone");

        test = new Inventory(inv, qai);

    }


    @Test
    public void inventoryDefaultConstructorTest(){
        Inventory inv = new Inventory();

        assertEquals(2, inv.getInventoryContents().size());
        assertEquals(2, inv.getAmount("Stone"));
        assertEquals(2, inv.getAmount("Wood"));

    }


    @Test
    public void inventoryCustomConstructorTest(){
        assertEquals(1, test.getInventoryContents().size());
        assertEquals(1, test.getAmount("Stone"));
        assertEquals(1, test.getQuickAccess().size());
    }

    @Test
    public void getInventoryContentsTest(){
        Map<String, List<Item>> contents = test.getInventoryContents();

        assertNotNull(contents.get("Stone"));
        assertEquals(1, contents.get("Stone").size());

        test.inventoryAdd(new Vine());
        assertNotNull(contents.get("Vine"));
        assertEquals(1, contents.get("Vine").size());
        assertEquals(2, contents.size());


        test.inventoryDrop("Stone");
        test.inventoryDrop("Vine");
        assertTrue(contents.isEmpty());


        try{
            List<Item> addList = new ArrayList<>();
            addList.add(new Wood());
            contents.put("Wood", addList);
            fail();

        }catch(UnsupportedOperationException e){
            //Unable to modify returned inventory map as expected
        }

    }

    @Test
    public void getAmountsAddDropTest(){

        assertEquals(1, test.getInventoryAmounts().size());
        assertEquals((Integer)1, test.getInventoryAmounts().get("Stone"));
        assertEquals(1, test.getAmount("Stone"));

        test.inventoryDrop("Stone");

        assertEquals(0, test.getInventoryAmounts().size());
        assertEquals(0,test.getAmount("Stone"));

        test.inventoryAdd(new Wood());
        test.inventoryAdd(new Wood());
        test.inventoryAdd(new Vine());
        test.inventoryAdd(new Stone());
        test.inventoryAdd(new Stone());
        test.inventoryAdd(new Sand());
        test.inventoryAdd(new Sand());
        test.inventoryAdd(new Sand());

        assertEquals(4, test.getInventoryAmounts().size());
        assertEquals(2, test.getAmount("Stone"));
        assertEquals(2, test.getAmount("Wood"));
        assertEquals(1, test.getAmount("Vine"));
        assertEquals(3, test.getAmount("Sand"));
        assertEquals(0, test.getAmount("Apple"));

        assertTrue(test.inventoryDrop("Stone") instanceof Stone);
        assertEquals(1, test.getAmount("Stone"));
        test.inventoryDrop("Stone");
        assertEquals(0, test.getAmount("Stone"));
        assertNull(test.inventoryDrop("Stone"));

        assertEquals(3, test.getInventoryAmounts().size());
        assertEquals(0, test.getAmount("Stone"));

        test.inventoryDropMultiple("Wood", 2);
        assertEquals(2, test.getInventoryAmounts().size());
        assertEquals(0, test.getAmount("Wood"));

        test.inventoryDropMultiple("Sand", 2);
        assertEquals(2, test.getInventoryAmounts().size());
        assertEquals(1, test.getAmount("Sand"));

        assertNull(test.inventoryDropMultiple("Sand", 2));
        assertNull(test.inventoryDropMultiple("Apple", 3));
    }

    @Test
    public void toStringTest(){
        String toStringTest = "Inventory Contents " + test.getInventoryAmounts().toString();
        assertEquals(test.toString(), toStringTest);
    }


    @Test
    public void QuickAccessTest(){

        assertEquals(1, test.getQuickAccess().size());
        assertEquals((Integer)1, test.getQuickAccess().get("Stone"));

        test.quickAccessAdd("Apple");

        assertEquals(1, test.getQuickAccess().size());
        assertEquals((Integer)1, test.getQuickAccess().get("Stone"));

        test.inventoryAdd(new Wood());
        test.inventoryAdd(new Wood());
        test.inventoryAdd(new Vine());
        test.inventoryAdd(new Stone());
        test.inventoryAdd(new Stone());
        test.inventoryAdd(new Sand());
        test.inventoryAdd(new Apple());
        test.inventoryAdd(new Berry());
        test.inventoryAdd(new Metal());

        test.quickAccessAdd("Wood");
        test.quickAccessAdd("Vine");

        assertEquals(3, test.getQuickAccess().size());
        assertEquals((Integer)2, test.getQuickAccess().get("Wood"));
        assertEquals((Integer)1, test.getQuickAccess().get("Vine"));
        assertEquals((Integer)3, test.getQuickAccess().get("Stone"));

        test.quickAccessAdd("Sand");
        test.quickAccessAdd("Apple");
        test.quickAccessAdd("Berry");

        assertEquals(6, test.getQuickAccess().size());

        test.quickAccessAdd("Metal");
        assertEquals(6, test.getQuickAccess().size());
        assertFalse(test.getQuickAccess().containsKey("Metal"));

    }
}