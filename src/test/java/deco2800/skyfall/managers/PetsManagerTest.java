package deco2800.skyfall.managers;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.pets.AbstractPet;
import deco2800.skyfall.entities.pets.Lizard;
import deco2800.skyfall.entities.pets.Whitebear;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, DatabaseManager.class, MainCharacter.class, InventoryManager.class})
public class PetsManagerTest {
    private World w = null;
    private PetsManager petsManager = null;
    private PhysicsManager physics;
    @Mock
    private GameManager mockGM;
    private MainCharacter mc;
    private InventoryManager inventoryManager;
    private AbstractPet bear;
    private AbstractPet lizard;

    /**
     * Setting up the world
     */
    @Before
    public void Setup() throws NoSuchFieldException, IllegalAccessException {
        MainCharacter.resetInstance();
        mc = MainCharacter.getInstance(0f, 0f, 0.05f, "Main Piece", 10);
        bear = new Whitebear(0, 0, mc);
        lizard = new Lizard(0, 0, mc);
        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder);
        w = worldBuilder.getWorld();
        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);
        inventoryManager = GameManager.get().getManagerFromInstance(InventoryManager.class);
        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(w);
        petsManager = new PetsManager();
        when(mockGM.getManager(PetsManager.class)).thenReturn(petsManager);
        InputManager Im = new InputManager();

        OnScreenMessageManager mockOSMM = mock(OnScreenMessageManager.class);
        when(mockGM.getManager(OnScreenMessageManager.class)).thenReturn(mockOSMM);

        physics = new PhysicsManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);

        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(Im);
    }

    /**
     * Add pets to pet manager
     */
    @Test
    public void addPet() {
        petsManager.addPet(bear);
        Assert.assertEquals(petsManager.allPets().size(), 1);
    }

    /**
     * Getting all pets in the pet manager
     */
    @Test
    public void allPets() {
        petsManager.addPet(bear);
        petsManager.addPet(lizard);
        Assert.assertEquals(petsManager.allPets().size(), 2);
    }

    /**
     * To check is the current pet summoned
     */
    @Test
    public void currentSummonedPet() {
        bear.setSummoned(true);
        Assert.assertEquals(bear.isSummoned(), true);
    }

    /**
     * Switch to next pet
     */
    @Test
    public void replacePet() {
        petsManager.replacePet(mc);
        Assert.assertEquals(petsManager.allPets().size(), 0);

    }


    /**
     * To get the index of pet in  pet list
     */
    @Test
    public void petPositionInList() {
        petsManager.addPet(bear);
        Assert.assertEquals(petsManager.petPositionInList(bear), 0);
    }

    /**
     * To check is the pet summoned
     */
    @Test
    public void isSummoned() {
        bear.setSummoned(true);
        Assert.assertEquals(bear.isSummoned(), true);
    }
}