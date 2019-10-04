package deco2800.skyfall.managers;

import com.badlogic.gdx.Game;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.*;

import org.junit.*;
import java.util.*;

import static org.junit.Assert.*;

public class InventoryManagerTest {

    private InventoryManager test;

    @Before
    public void initialize() {

        Map<String, List<Item>> inv = new HashMap<>();
        List<Item> stoneList = new ArrayList<>();
        stoneList.add(new Stone());
        inv.put("Stone", stoneList);

        List<String> qai = new ArrayList<>();
        qai.add("Stone");
        test = new InventoryManager(inv, qai);
    }


    @Test
    public void inventoryDefaultConstructorTest(){
        InventoryManager inv = new InventoryManager();

        assertEquals(4, inv.getContents().size());
        assertEquals(2, inv.getAmount("Stone"));
        assertEquals(5, inv.getAmount("Wood"));

    }


    @Test
    public void inventoryCustomConstructorTest(){
        assertEquals(1, test.getContents().size());
        assertEquals(1, test.getAmount("Stone"));
        assertEquals(1, test.getQuickAccess().size());
    }

    @Test
    public void getInventoryContentsTest(){
        Map<String, List<Item>> contents = test.getContents();

        assertNotNull(contents.get("Stone"));
        assertEquals(1, contents.get("Stone").size());

        test.add(new Vine());
        assertNotNull(contents.get("Vine"));
        assertEquals(1, contents.get("Vine").size());
        assertEquals(2, contents.size());


        test.drop("Stone");
        test.drop("Vine");
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

        assertEquals(1, test.getAmounts().size());
        assertEquals((Integer)1, test.getAmounts().get("Stone"));
        assertEquals(1, test.getAmount("Stone"));

        test.drop("Stone");

        assertEquals(0, test.getAmounts().size());
        assertEquals(0,test.getAmount("Stone"));

        Wood wood = new Wood();
        test.add(wood);
        test.add(new Wood());
        test.add(new Vine());
        test.add(new Stone());
        test.add(new Stone());
        test.add(new Sand());
        test.add(new Sand());
        test.add(new Sand());
        test.add(new Metal());
        test.add(new Metal());

        assertEquals(5, test.getAmounts().size());
        assertEquals(2, test.getAmount("Stone"));
        assertEquals(2, test.getAmount("Wood"));
        assertEquals(2, test.getAmount("Metal"));
        assertEquals(1, test.getAmount("Vine"));
        assertEquals(3, test.getAmount("Sand"));
        assertEquals(0, test.getAmount("Apple"));

        assertTrue(test.drop("Stone") instanceof Stone);
        assertEquals(1, test.getAmount("Stone"));
        test.drop("Stone");
        assertEquals(0, test.getAmount("Stone"));
        assertNull(test.drop("Stone"));

        assertEquals(4, test.getAmounts().size());
        assertEquals(0, test.getAmount("Stone"));

        test.dropMultiple("Wood", 2);
        assertEquals(3, test.getAmounts().size());
        assertEquals(0, test.getAmount("Wood"));

        test.dropMultiple("Sand", 2);
        assertEquals(3, test.getAmounts().size());
        assertEquals(1, test.getAmount("Sand"));

        assertNull(test.dropMultiple("Sand", 2));
        assertNull(test.dropMultiple("Apple", 3));
    }

    @Test
    public void toStringTest(){
        String toStringTest = "Inventory Contents " + test.getAmounts().toString();
        assertEquals(test.toString(), toStringTest);
    }


    @Test
    public void QuickAccessTest(){

        assertEquals(1, test.getQuickAccess().size());
        assertEquals((Integer)1, test.getQuickAccess().get("Stone"));

        test.quickAccessAdd("Apple");

        assertEquals(1, test.getQuickAccess().size());
        assertEquals((Integer)1, test.getQuickAccess().get("Stone"));

        test.add(new Wood());
        test.add(new Wood());
        test.add(new Vine());
        test.add(new Stone());
        test.add(new Stone());
        test.add(new Sand());
        test.add(new Apple());
        test.add(new Berry());
        test.add(new Metal());

        test.quickAccessAdd("Wood");
        test.quickAccessAdd("Vine");

        assertEquals(3, test.getQuickAccess().size());
        assertEquals((Integer)2, test.getQuickAccess().get("Wood"));
        assertEquals((Integer)1, test.getQuickAccess().get("Vine"));
        assertEquals((Integer)3, test.getQuickAccess().get("Stone"));

        test.quickAccessAdd("Sand");
        test.quickAccessAdd("Apple");
        test.quickAccessAdd("Berry");

        assertEquals(4, test.getQuickAccess().size());

        test.quickAccessAdd("Metal");
        assertEquals(4, test.getQuickAccess().size());
        assertFalse(test.getQuickAccess().containsKey("Metal"));

    }

    @Test
    public void positionsTest() {
        assertEquals("{Stone=(0, 0)}", test.getPositions().toString());
        test.add(new Apple());
        assertEquals("{Apple=(1, 0), Stone=(0, 0)}", test.getPositions().toString());

        Map<String, List<Item>> inventory = new HashMap<>();

        List<Item> stones = new ArrayList<>();
        stones.add(new Stone());
        List<Item> apples = new ArrayList<>();
        apples.add(new Apple());
        List<Item> aloe = new ArrayList<>();
        aloe.add(new Aloe_Vera());
        List<Item> wood = new ArrayList<>();
        wood.add(new Wood());
        List<Item> sand = new ArrayList<>();
        sand.add(new Sand());

        inventory.put(new Stone().getName(), stones);
        inventory.put(new Apple().getName(), apples);
        inventory.put(new Aloe_Vera().getName(), aloe);
        inventory.put(new Wood().getName(), wood);
        inventory.put(new Sand().getName(), sand);

        InventoryManager m = new InventoryManager(inventory, new ArrayList<>());
        assertEquals("{Apple=(0, 0), Aloe_Vera=(1, 0), Sand=(2, 0), Wood=(3, 0), Stone=(0, 1)}", m.getPositions().toString());
    }
}