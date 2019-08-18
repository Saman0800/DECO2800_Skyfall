package deco2800.skyfall.worlds;

import deco2800.skyfall.util.Cube;
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

                if (node.isBorderNode() && neighbour.isBorderNode()) {
                    if (commonVertices == 0) {
                        fail();
                    }
                } else if (commonVertices == 1) {
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
                Tile tile = new Tile(biome, q, r + oddCol);
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
        Tile tile1 = new Tile(biome, 0, 2);
        Tile tile2 = new Tile(biome, -1, 1.5f);

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

    @Test
    public void getBoundaryIntersectionTest() {
        int worldSize = 100;
        // Check with straight lines (one for each boundary)
        WorldGenNode node1 = new WorldGenNode(10, 10);
        WorldGenNode node2 = new WorldGenNode(50, 10);
        WorldGenNode node3 = new WorldGenNode(50, -90);
        WorldGenNode node4 = new WorldGenNode(-20, -90);
        WorldGenNode node5 = new WorldGenNode(-20, -60);

        // Prevent the NotAdjacentException thrown by WorldGenNode.sharedVertex
        // (that isn't being tested here)
        double[] vertex = {0, 0};
        try {
            node1.addVertex(vertex);
            node2.addVertex(vertex);
            node3.addVertex(vertex);
            node4.addVertex(vertex);
            node5.addVertex(vertex);
        } catch (InvalidCoordinatesException e) {
            fail();
        }

        node1.addBorderNeighbour(node2);
        node2.addBorderNeighbour(node1);
        node2.addBorderNeighbour(node3);
        node3.addBorderNeighbour(node2);
        node3.addBorderNeighbour(node4);
        node4.addBorderNeighbour(node3);
        node4.addBorderNeighbour(node5);
        node5.addBorderNeighbour(node4);

        try {
            double[] intersection1
                    = node1.getBoundaryIntersection(node2, worldSize);
            double[] intersection2
                    = node2.getBoundaryIntersection(node3, worldSize);
            double[] intersection3
                    = node3.getBoundaryIntersection(node4, worldSize);
            double[] intersection4
                    = node4.getBoundaryIntersection(node5, worldSize);
            assertEquals(30, intersection1[0], 0.00001);
            assertEquals(worldSize, intersection1[1], 0.00001);
            assertEquals(worldSize, intersection2[0], 0.00001);
            assertEquals(-40, intersection2[1], 0.00001);
            assertEquals(15, intersection3[0], 0.00001);
            assertEquals(-1 * worldSize, intersection3[1], 0.00001);
            assertEquals(-1 * worldSize, intersection4[0], 0.00001);
            assertEquals(-75, intersection4[1], 0.00001);
        } catch (NotAdjacentException | InvalidCoordinatesException e) {
            fail();
        }

        // Test with random numbers
        WorldGenNode node6 = new WorldGenNode(-68.38475, -37.19384);
        WorldGenNode node7 = new WorldGenNode(-27.64838, 3.62727);
        WorldGenNode node8 = new WorldGenNode(51.42736, 45.32892);
        WorldGenNode node9 = new WorldGenNode(69.48293, 24.00128);
        try {
            node6.addVertex(vertex);
            node7.addVertex(vertex);
            node8.addVertex(vertex);
            node9.addVertex(vertex);
        } catch (InvalidCoordinatesException e) {
            fail();
        }
        node5.addBorderNeighbour(node6);
        node6.addBorderNeighbour(node5);
        node6.addBorderNeighbour(node7);
        node7.addBorderNeighbour(node6);
        node7.addBorderNeighbour(node8);
        node8.addBorderNeighbour(node7);
        node8.addBorderNeighbour(node9);
        node9.addBorderNeighbour(node8);
        try {
            double[] intersection1
                    = node5.getBoundaryIntersection(node6, worldSize);
            double[] intersection2
                    = node6.getBoundaryIntersection(node7, worldSize);
            double[] intersection3
                    = node7.getBoundaryIntersection(node8, worldSize);
            double[] intersection4
                    = node8.getBoundaryIntersection(node9, worldSize);
            assertEquals(-68.42123, intersection1[0], 0.00001);
            assertEquals(-1 * worldSize, intersection1[1], 0.00001);
            assertEquals(-1 * worldSize, intersection2[0], 0.00001);
            assertEquals(35.09224, intersection2[1], 0.00001);
            assertEquals(-27.93800, intersection3[0], 0.00001);
            assertEquals(worldSize, intersection3[1], 0.00001);
            assertEquals(worldSize, intersection4[0], 0.00001);
            assertEquals(68.14301, intersection4[1], 0.00001);
        } catch (NotAdjacentException | InvalidCoordinatesException e) {
            fail();
        }

        // Check an exception is thrown with Nodes that are not border neighbours
        try {
            double[] intersection1
                    = node1.getBoundaryIntersection(node9, worldSize);
            fail();
        } catch (InvalidCoordinatesException e) {
            fail();
        } catch (NotAdjacentException e) {
            // do nothing
        }
    }
}