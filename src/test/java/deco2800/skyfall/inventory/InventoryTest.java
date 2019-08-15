package deco2800.skyfall.inventory;

import static org.junit.Assert.*;
import java.util.*;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.*;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {

    Inventory test;

    @Before
    public void initialize() {

        Map<String, List<Item>> inv = new HashMap<>();
        List<Item> stoneList = new ArrayList<>();
        stoneList.add(new Stone());
        inv.put("Stone", stoneList);

        test = new Inventory(inv);

    }


    @Test
    public void inventoryDefaultConstructorTest(){
        //????

    }


    @Test
    public void inventoryCustomConstructorTest(){
        //?????
    }

    @Test
    public void getInventoryContentsTest(){
        Map<String, List<Item>> contents = test.getInventoryContents();

        assertNotNull(contents.get("Stone"));
        assertTrue(contents.get("Stone").size() == 1);

        test.inventoryAdd(new Vine());
        assertNotNull(contents.get("Vine"));
        assertTrue(contents.get("Vine").size() == 1);
        assertTrue(contents.size() == 2);


        test.inventoryDrop("Stone");
        test.inventoryDrop("Vine");
        assertTrue(contents.isEmpty());


        try{
            List<Item> addList = new ArrayList<>();
            addList.add(new Wood());
            contents.put("Wood", addList);
            fail();

        }catch(UnsupportedOperationException e){

        }

    }

    @Test
    public void getAmountsTest(){

        assertTrue(test.getInventoryAmounts().size() == 1);
        assertTrue(test.getInventoryAmounts().get("Stone") == 1);
        assertTrue(test.getAmount("Stone") == 1);

        test.inventoryDrop("Stone");

        assertTrue(test.getInventoryAmounts().size() == 0);
        assertTrue(test.getAmount("Stone") == 0);

        test.inventoryAdd(new Wood());
        test.inventoryAdd(new Wood());
        test.inventoryAdd(new Vine());
        test.inventoryAdd(new Stone());

        assertTrue(test.getInventoryAmounts().size() == 3);
        assertTrue(test.getAmount("Stone") == 1);
        assertTrue(test.getAmount("Wood") == 2);
        assertTrue(test.getAmount("Vine") == 1);
        assertTrue(test.getAmount("Sand") == 0);


    }

    @Test
    public void toStringTest(){

    }

    @Test
    public void inventoryAddTest(){

    }


    @Test
    public void inventoryDropTest(){

    }

    @Test
    public void inventoryDropMultipleTest(){

    }

}