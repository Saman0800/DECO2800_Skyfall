package deco2800.skyfall.worlds.delaunay;

import deco2800.skyfall.util.Cube;
import deco2800.skyfall.worlds.ForestBiome;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.delaunay.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class DelaunayTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void triangleCircumcircle() {
        WorldGenNode node1 = new WorldGenNode(0, 0);
        WorldGenNode node2 = new WorldGenNode(1, 0);
        WorldGenNode node3 = new WorldGenNode(0, 1);

        WorldGenNode node4 = new WorldGenNode(2, 5);
        WorldGenNode node5 = new WorldGenNode(3, 3);
        WorldGenNode node6 = new WorldGenNode(-2, 1);

        WorldGenTriangle triangle1 = new WorldGenTriangle(node1, node2, node3);
        WorldGenTriangle triangle2 = new WorldGenTriangle(node4, node5, node6);
        double[] circumcentre1 = {0, 0};
        double[] circumcentre2 = {0, 0};

        // Test that the exception is not thrown when it shouldn't be
        try{
            circumcentre1 = triangle1.circumcentre();
            circumcentre2 = triangle2.circumcentre();
        } catch (CollinearPointsException e) {
            fail();
        }
        assertEquals(2, circumcentre1.length);
        assertEquals(0.5, circumcentre1[0], 0);
        assertEquals(0.5, circumcentre1[1], 0);
        assertEquals(2, circumcentre2.length);
        assertEquals(1.0d / 6.0d, circumcentre2[0], 0.0001);
        assertEquals(17.0d / 6.0d, circumcentre2[1], 0.0001);

        // Test that it correctly throws exception
        WorldGenNode node7 = new WorldGenNode(-1, 0);
        WorldGenNode node8 = new WorldGenNode(5, 5);
        WorldGenTriangle triangle3 = new WorldGenTriangle(node1, node2, node7);
        WorldGenTriangle triangle4 = new WorldGenTriangle(node1, node5, node8);
        double[] circumcentre3 = {0, 0};
        double[] circumcentre4 = {0, 0};

        try {
            circumcentre3 = triangle3.circumcentre();
            fail();
        } catch (CollinearPointsException e) {
            // Do nothing
        }
        try {
            circumcentre4 = triangle4.circumcentre();
            fail();
        } catch (CollinearPointsException e) {
            // Do nothing
        }
    }

    @Test
    public void testAllNeighboursShareAVertex() {

        int nodeCount = 200;
        int worldSize = 500;
        Random random = new Random();

        List<WorldGenNode> nodes = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            // Random number from -500 to 500
            double x = (random.nextFloat() - 0.5) * 2 * worldSize;
            double y = (random.nextFloat() - 0.5) * 2 * worldSize;
            nodes.add(new WorldGenNode(x, y));
        }

        try {
            WorldGenNode.calculateVertices(nodes, worldSize);
            WorldGenNode.assignNeighbours(nodes);
        } catch (WorldGenException e) {
            fail();
        }

        for (WorldGenNode node : nodes) {
            assertNotEquals(0, node.getVertices().size());
            assertNotEquals(0, node.getNeighbours().size());
            for (WorldGenNode neighbour : node.getNeighbours()) {
                int commonVertices = 0;

                for (double[] thisVertex : node.getVertices()) {
                    for (double[] otherVertex : neighbour.getVertices()) {
                        if (thisVertex[0] == otherVertex[0] && thisVertex[1]
                                == otherVertex[1]) {
                            commonVertices += 1;
                        }
                    }
                }

                if (commonVertices == 0) {
                    fail();
                }
            }
        }
    }

    @Test
    public void assignTilesTest() {
        // Keep these parameters small or this test will take a LONG time
        // (method has O(nodeCount*worldSize^2) time complexity)
        int nodeCount = 5;
        int worldSize = 30;
        Random random = new Random();

        List<WorldGenNode> nodes = new ArrayList<>();
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            // Random number from -30 to 30
            double x = (random.nextFloat() - 0.5) * 2 * worldSize;
            double y = (random.nextFloat() - 0.5) * 2 * worldSize;
            nodes.add(new WorldGenNode(x, y));
        }

        ForestBiome biome = new ForestBiome();

        // Fill world with tiles
        for (int q = -1 * worldSize; q < worldSize; q++) {
            for (int r = -1 * worldSize; r < worldSize; r++) {
                float oddCol = (q % 2 != 0 ? 0.5f : 0);

                int elevation = random.nextInt(2);
                // String type = "grass_" + elevation;
                Tile tile = new Tile(q, r + oddCol);
                tiles.add(tile);
                biome.addTile(tile);
            }
        }

        WorldGenNode.assignTiles(nodes, tiles);

        // Check that nodes are sorted
        for (int i = 0; i < nodes.size() - 1; i++) {
            if (nodes.get(i).getY() > nodes.get(i + 1).getY()) {
                fail();
            }
        }

        for (Tile tile : tiles) {
            double minDistanceToNode = -1;
            int index = 0;

            // Find the closest node
            for (int i = 0; i < nodes.size(); i++) {
                double distance = nodes.get(i).distanceToTile(tile);
                if (minDistanceToNode == -1) {
                    minDistanceToNode = distance;
                } else if (minDistanceToNode > distance) {
                    minDistanceToNode = distance;
                    index = i;
                }
            }

            // Check that the tile is in the list of tiles for that node, and no
            // other node
            for (int i = 0; i < nodes.size(); i++) {
                if (nodes.get(i) != nodes.get(index)) {
                    if (nodes.get(i).getTiles().contains(tile)) {
                        fail();
                    }
                } else if (!nodes.get(i).getTiles().contains(tile)) {
                    fail();
                }
            }
        }
    }

    @Test
    public void checkDistanceCalc() {
        WorldGenNode node1 = new WorldGenNode(2, 5);
        WorldGenNode node2 = new WorldGenNode(-3.26, 1.00492);

        ForestBiome biome = new ForestBiome();
        Tile tile1 = new Tile(0, 2);
        Tile tile2 = new Tile(-1, 1.5f);

        // Expected values calculated using calculator
        assertEquals(13, node1.distanceToTile(tile1), 0);
        assertEquals(9, node1.yDistanceToTile(tile1), 0);
        assertEquals(21.25, node1.distanceToTile(tile2), 0);
        assertEquals(12.25, node1.yDistanceToTile(tile2), 0);
        // Use non-zero delta when many decimal places are involved and numbers
        // can't necessarily be exactly written in base 2
        assertEquals(11.61778, node2.distanceToTile(tile1), 0.00001);
        assertEquals(0.99018, node2.yDistanceToTile(tile1), 0.00001);
        assertEquals(5.35270, node2.distanceToTile(tile2), 0.00001);
        assertEquals(0.24510, node2.yDistanceToTile(tile2), 0.00001);
    }
}